package jun.projavawebapp.site.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{sessionId}",
        encoders = ChatMessageCodec.class,
        decoders = ChatMessageCodec.class,
        configurator = ChatEndpoint.EndpointConfigurator.class)
@WebListener
public class ChatEndpoint implements HttpSessionListener {

    private static final Logger log = LogManager.getLogger();

    private static final String HTTP_SESSION_PROPERTY = "jun.projavawebapp.ws.HTTP_SESSION";
    private static final String WS_SESSION_PROPERTY = "jun.projavawebapp.http.WS_SESSION";
    private static long sessionIdSequence = 1L;
    private static final Object sessionIdSequenceLock = new Object();
    private static final Map<Long, ChatSession> chatSessions = new ConcurrentHashMap<>();
    private static final Map<Session, ChatSession> sessions = new ConcurrentHashMap<>();
    private static final Map<Session, HttpSession> httpSessions = new ConcurrentHashMap<>();
    public static final List<ChatSession> pendingSessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") long sessionId) {
        log.traceEntry("{}", sessionId);
        HttpSession httpSession = (HttpSession) session.getUserProperties()
                .get(ChatEndpoint.HTTP_SESSION_PROPERTY);
        try {
            if (httpSession == null || httpSession.getAttribute("username") == null) {
                log.warn("Attempt to access chat server while logged out.");
                session.close(new CloseReason(
                        CloseReason.CloseCodes.VIOLATED_POLICY,
                        "You are not logged in!"
                ));
                return;
            }

            String username = (String) httpSession.getAttribute("username");
            session.getUserProperties().put("username", username);
            ChatMessage message = new ChatMessage();
            message.setTimestamp(OffsetDateTime.now());
            message.setUser(username);
            ChatSession chatSession;
            if (sessionId < 1) {
                log.debug("User starting chat {} is {}.", sessionId, username);
                message.setType(ChatMessage.Type.STARTED);
                message.setContent(username + " started the chat session.");
                chatSession = new ChatSession();
                synchronized (ChatEndpoint.sessionIdSequenceLock) {
                    chatSession.setSessionId(ChatEndpoint.sessionIdSequence++);
                }
                chatSession.setCustomer(session);
                chatSession.setCustomerUsername(username);
                chatSession.setCreationMessage(message);
                ChatEndpoint.pendingSessions.add(chatSession);
                ChatEndpoint.chatSessions.put(chatSession.getSessionId(),
                        chatSession);
            } else {
                log.debug("User joining chat {} is {}.", sessionId, username);
                message.setType(ChatMessage.Type.JOINED);
                message.setContent(username + " joined the chat session.");
                chatSession = ChatEndpoint.chatSessions.get(sessionId);
                chatSession.setRepresentative(session);
                chatSession.setRepresentativeUsername(username);
                ChatEndpoint.pendingSessions.remove(chatSession);
                session.getBasicRemote()
                        .sendObject(chatSession.getCreationMessage());
                session.getBasicRemote().sendObject(message);
            }

            ChatEndpoint.sessions.put(session, chatSession);
            ChatEndpoint.httpSessions.put(session, httpSession);
            this.getSessionsFor(httpSession).add(session);
            chatSession.log(message);
            chatSession.getCustomer().getBasicRemote().sendObject(message);
            log.debug("onMessage completed successfully for chat {}.", sessionId);
        } catch (IOException | EncodeException e) {
            this.onError(session, e);
        } finally {
            log.traceExit();
        }
    }

    @OnMessage
    public void onMessage(Session session, ChatMessage message) {
        log.traceEntry();
        ChatSession c = ChatEndpoint.sessions.get(session);
        Session other = this.getOtherSession(c, session);
        if (c != null && other != null) {
            c.log(message);
            try {
                session.getBasicRemote().sendObject(message);
                other.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                this.onError(session, e);
            }
        } else
            log.warn("Chat message received with only one chat member.");
        log.traceExit();
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        if (reason.getCloseCode() == CloseReason.CloseCodes.NORMAL_CLOSURE) {
            ChatMessage message = new ChatMessage();
            message.setUser((String) session.getUserProperties().get("username"));
            message.setType(ChatMessage.Type.LEFT);
            message.setTimestamp(OffsetDateTime.now());
            message.setContent(message.getUser() + " left the chat.");
            try {
                Session other = this.close(session, message);
                if (other != null)
                    other.close();
            } catch (IOException e) {
                log.warn("Problem closing companion chat session.", e);
            }
        } else
            log.warn("Abnormal closure {} for reason [{}].", reason.getCloseCode(),
                    reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.warn("Error received in WebSocket session.", e);
        ChatMessage message = new ChatMessage();
        message.setUser((String) session.getUserProperties().get("username"));
        message.setType(ChatMessage.Type.ERROR);
        message.setTimestamp(OffsetDateTime.now());
        message.setContent(message.getUser() + " left the chat due to an error.");
        try {
            Session other = this.close(session, message);
            if (other != null)
                other.close(new CloseReason(
                        CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()
                ));
        } catch (IOException ignore) {
        } finally {
            try {
                session.close(new CloseReason(
                        CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()
                ));
            } catch (IOException ignore) {
            }
            log.traceExit();
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession httpSession = event.getSession();
        log.traceEntry(httpSession.getId());
        if (httpSession.getAttribute(WS_SESSION_PROPERTY) != null) {
            ChatMessage message = new ChatMessage();
            message.setUser((String) httpSession.getAttribute("username"));
            message.setType(ChatMessage.Type.LEFT);
            message.setTimestamp(OffsetDateTime.now());
            message.setContent(message.getUser() + " logged out.");
            for (Session session : new ArrayList<>(this.getSessionsFor(httpSession))) {
                log.info("Closing chat session {} belonging to HTTP session {}.",
                        session.getId(), httpSession.getId());
                try {
                    session.getBasicRemote().sendObject(message);
                    Session other = this.close(session, message);
                    if (other != null)
                        other.close();
                } catch (IOException | EncodeException e) {
                    log.warn("Problem closing companion chat session.");
                } finally {
                    try {
                        session.close();
                    } catch (IOException ignore) {
                    }
                }
            }
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) { /* do nothing */ }

    @SuppressWarnings("unchecked")
    private synchronized ArrayList<Session> getSessionsFor(HttpSession session) {
        log.traceEntry();
        try {
            if (session.getAttribute(WS_SESSION_PROPERTY) == null)
                session.setAttribute(WS_SESSION_PROPERTY, new ArrayList<>());

            return (ArrayList<Session>) session.getAttribute(WS_SESSION_PROPERTY);
        } catch (IllegalStateException e) {
            return new ArrayList<>();
        } finally {
            log.traceExit();
        }
    }

    private Session close(Session s, ChatMessage message) {
        log.traceEntry("{}", s);
        ChatSession c = ChatEndpoint.sessions.get(s);
        Session other = this.getOtherSession(c, s);
        ChatEndpoint.sessions.remove(s);
        HttpSession h = ChatEndpoint.httpSessions.get(s);
        if (h != null)
            this.getSessionsFor(h).remove(s);
        if (c != null) {
            c.log(message);
            ChatEndpoint.pendingSessions.remove(c);
            ChatEndpoint.chatSessions.remove(c.getSessionId());
            try {
                c.writeChatLog(new File("chat." + c.getSessionId() + ".log"));
            } catch (Exception e) {
                log.error("Could not write chat log due to error.", e);
            }
        }
        if (other != null) {
            ChatEndpoint.sessions.remove(other);
            h = ChatEndpoint.httpSessions.get(other);
            if (h != null)
                this.getSessionsFor(h).remove(s);
            try {
                other.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                log.warn("Problem closing companion chat session.", e);
            }
        }
        return log.traceExit(other);
    }

    private Session getOtherSession(ChatSession c, Session s) {
        log.traceEntry();
        return log.traceExit(c == null ? null :
                (s == c.getCustomer() ? c.getRepresentative() : c.getCustomer()));
    }

    public static class EndpointConfigurator
            extends ServerEndpointConfig.Configurator {
        @Override
        public void modifyHandshake(ServerEndpointConfig config,
                                    HandshakeRequest request,
                                    HandshakeResponse response) {
            log.traceEntry();
            super.modifyHandshake(config, request, response);

            config.getUserProperties().put(
                    ChatEndpoint.HTTP_SESSION_PROPERTY, request.getHttpSession()
            );
            log.traceExit();
        }
    }
}
