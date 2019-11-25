package jun.projavawebapp;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.time.Instant;
import java.time.Month;
import java.time.MonthDay;
import java.util.Collections;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

@WebServlet(name = "listServlet", urlPatterns = "/list")
public class ListServlet extends HttpServlet {

    private static final SortedSet<Contact> contacts = new TreeSet<>();

    static {
        contacts.add(new Contact("Jane", "Sanders", "555-1593", "394 E 22nd Ave",
                MonthDay.of(Month.JANUARY, 5),
                Instant.parse("2013-02-01T21:22:23Z")
        ));
        contacts.add(new Contact("John", "Smith", "555-0712", "315 Maple St",
                null, Instant.parse("2012-10-15T15:31:17Z")
        ));
        contacts.add(new Contact("Scott", "Johnson", "555-9834", "424 Oak Dr",
                MonthDay.of(Month.NOVEMBER, 17),
                Instant.parse("2013-04-05T01:45:01Z")
        ));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        setRequestLocale(req, req.getParameter("language"));

        if (req.getParameter("empty") != null) {
            req.setAttribute("contacts", Collections.emptySet());
        } else {
            req.setAttribute("contacts", contacts);
        }

        req.getRequestDispatcher("WEB-INF/jsp/view/list.jsp")
                .forward(req, resp);
    }

    private static void setRequestLocale(
            HttpServletRequest request, String language) {

        if (StringUtils.isEmpty(language)) {
            return;
        }

        switch (language.toLowerCase()) {
            case "us":
                Config.set(request, Config.FMT_LOCALE, Locale.US);
                break;
            case "china":
                Config.set(request, Config.FMT_LOCALE, Locale.CHINA);
                break;
        }
    }
}
