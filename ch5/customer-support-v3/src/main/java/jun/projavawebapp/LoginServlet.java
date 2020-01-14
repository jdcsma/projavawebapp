package jun.projavawebapp;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@WebServlet(
        name = "loginServlet",
        urlPatterns = "/v3/login",
        loadOnStartup = 1)
public class LoginServlet extends HttpServlet {

    private static final Map<String, String> userDatabase;

    static {
        userDatabase = new HashMap<>();
        userDatabase.put("Nicholas", "1");
        userDatabase.put("Sarah", "2");
        userDatabase.put("Mike", "3");
        userDatabase.put("John", "4");
    }

    @Override
    protected void doGet(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (StringUtils.isEmpty(action)) {
            action = "login";
        }

        switch (action) {
            case "logout":
                if (SessionInspector.hasAuthorized(req.getSession())) {
                    req.getSession().invalidate();
                }
                resp.sendRedirect("login");
                break;
            default:

                if (SessionInspector.hasAuthorized(req.getSession())) {
                    resp.sendRedirect("ticketList");
                } else {
                    req.setAttribute("loginFailed", false);
                    req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
                            .forward(req, resp);
                }

                break;
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (SessionInspector.hasAuthorized(req.getSession())) {
            resp.sendRedirect("ticketList");
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (!authenticate(username, password)) {
            req.setAttribute("loginFailed", true);
            req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
                    .forward(req, resp);
            return;
        }

        req.getSession().setAttribute("username", username);
        resp.sendRedirect("ticketList");
    }

    private static boolean authenticate(
            String username, String password) {

        if (StringUtils.isEmpty(username) ||
                StringUtils.isEmpty(password)) {
            return false;
        }

        String certificate = userDatabase.get(username);

        if (StringUtils.isEmpty(certificate)) {
            return false;
        }

        return StringUtils.equals(certificate, password);
    }
}
