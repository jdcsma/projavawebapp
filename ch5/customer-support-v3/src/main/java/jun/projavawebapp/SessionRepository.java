package jun.projavawebapp;

import javax.servlet.http.HttpSession;
import java.util.*;

public class SessionRepository {

    private static final Object guard;
    private static final Map<String, HttpSession> sessions;

    static {
        guard = new Object();
        sessions = new HashMap<>();
    }

    public static void addSession(HttpSession session) {
        if (session != null) {
            synchronized (guard) {
                addSessionUnsafe(session);
            }
        }
    }

    public static void changeSessionId(
            HttpSession session, String oldSessionId) {
        if (session != null) {
            synchronized (guard) {
                removeSessionUnsafe(oldSessionId);
                addSessionUnsafe(session);
            }
        }
    }

    public static void removeSession(String sessionId) {
        synchronized (guard) {
            removeSessionUnsafe(sessionId);
        }
    }

    public static Collection<HttpSession> getSessions() {
        synchronized (guard) {
            return Collections.unmodifiableCollection(sessions.values());
        }
    }

    private static void addSessionUnsafe(HttpSession session) {
        sessions.put(session.getId(), session);
    }

    private static void removeSessionUnsafe(String sessionId) {
        sessions.remove(sessionId);
    }
}
