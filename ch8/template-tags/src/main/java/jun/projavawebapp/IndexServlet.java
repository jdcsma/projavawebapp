package jun.projavawebapp;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "indexServlet", urlPatterns = "/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String s = "简单才是美啊";
        String r = StringUtils.abbreviate(s, 5);
        System.out.println("len:" + s.length());
        System.out.println(r);

        String view = "hello";

        if (req.getParameter("dates") != null) {
            req.setAttribute("date", new Date());
            req.setAttribute("calender", Calendar.getInstance());
            req.setAttribute("instant", Instant.now());
            view = "date";
        } else if (req.getParameter("text") != null) {
            req.setAttribute("shortText", "This is short text.");
            req.setAttribute("longText",
                    "This is really long text that should get cut off at 32 chars.");
            view = "text";
        }

        req.getRequestDispatcher("/WEB-INF/jsp/view/" + view + ".jsp")
                .forward(req, resp);
    }
}
