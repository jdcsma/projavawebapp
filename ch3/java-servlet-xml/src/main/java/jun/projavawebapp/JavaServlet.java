package jun.projavawebapp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JavaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter writer = resp.getWriter();

        writer.append("<!DOCTYPE html")
                .append("<html>\r\n")
                .append("<head>\r\n")
                .append("<title>JavaServlet</title>\r\n")
                .append("</head>\r\n")
                .append("<body>\r\n")
                .append("<h2>This is a welcome information from JavaServlet.</h2>\r\n")
                .append("</body>\r\n")
                .append("</html>\r\n");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Servlet " + this.getServletName() + " has started.");

        ServletContext context = this.getServletContext();
        System.out.println("ServletContextParam:" +
                context.getInitParameter("ServletContextParamName"));

        ServletConfig config = this.getServletConfig();
        System.out.println("ServletConfigParam:" +
                config.getInitParameter("ServletConfigParamName"));
    }

    @Override
    public void destroy() {
        System.out.println("Servlet " + this.getServletName() + " has started.");
    }
}
