package jun.projavawebapp;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements
        HttpSessionListener, HttpSessionIdListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session " + se.getSession().getId() + " created");
        SessionRepository.addSession(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Session " + se.getSession().getId() + " destroyed");
        SessionRepository.removeSession(se.getSession().getId());
    }

    @Override
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        System.out.println("Session ID " + oldSessionId +
                " change to " + event.getSession().getId());
        SessionRepository.changeSessionId(event.getSession(), oldSessionId);
    }
}
