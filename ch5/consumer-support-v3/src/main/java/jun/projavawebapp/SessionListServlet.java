package jun.projavawebapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "sessionListServlet",
        urlPatterns = "/v3/sessionList")
public class SessionListServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!SessionInspector.hasAuthorized(
                req.getSession(), resp, "login")) {
            return;
        }

        req.setAttribute("sessions", SessionRepository.getSessions());
        req.getRequestDispatcher("/WEB-INF/jsp/view/sessionList.jsp")
                .forward(req, resp);
    }
}
