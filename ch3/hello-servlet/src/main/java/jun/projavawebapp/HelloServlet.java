package jun.projavawebapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.getWriter().println("Hello, Web World!");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Servlet: " +
                this.getServletName() + " has started.");
    }

    @Override
    public void destroy() {
        System.out.println("Servlet: " +
                this.getServletName() + " has stopped.");
    }
}
