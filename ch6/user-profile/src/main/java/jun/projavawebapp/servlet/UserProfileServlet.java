package jun.projavawebapp.servlet;

import jun.projavawebapp.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "UserProfileServlet", urlPatterns = "/profile")
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = new User();
        user.setUserID(19384L);
        user.setUsername("Coder314");
        user.setFirstName("John");
        user.setLastName("Smith");

        Map<String, Boolean> permissions = user.getPermissions();
        permissions.put("user", true);
        permissions.put("moderator", true);
        permissions.put("admin", false);

        req.setAttribute("user", user);
//        req.getSession().setAttribute("user", user);
//        this.getServletContext().setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/jsp/view/profile.jsp")
                .forward(req, resp);
    }
}
