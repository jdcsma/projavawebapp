package jun.projavawebapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "nonAsyncServlet", urlPatterns = "/regular")
public class NonAsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("Entering NonAsyncServlet.doGet().");
        req.getRequestDispatcher("WEB-INF/jsp/view/nonAsync.jsp").
                forward(req, resp);
        System.out.println("Leaving NonAsyncServlet.doGet().");
    }
}
