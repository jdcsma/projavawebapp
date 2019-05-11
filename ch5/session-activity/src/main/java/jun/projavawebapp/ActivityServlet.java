package jun.projavawebapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

@WebServlet(
        name = "activityServlet",
        urlPatterns = "/do/*",
        loadOnStartup = 1)
public class ActivityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        this.recordSessionActivity(req);

        this.viewSessionActivity(req, resp);
    }

    private void recordSessionActivity(HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (session.getAttribute("activity") == null) {
            session.setAttribute("activity", new Vector<PageVisit>());
        }

        @SuppressWarnings("unchecked")
        Vector<PageVisit> visits =
                (Vector<PageVisit>) session.getAttribute("activity");

        if (!visits.isEmpty()) {
            PageVisit last = visits.lastElement();
            last.setLeftTimestamp(System.currentTimeMillis());
        }

        PageVisit now = new PageVisit();
        now.setEnteredTimestamp(System.currentTimeMillis());

        String queryString = request.getQueryString();
        if (queryString == null) {
            now.setRequest(request.getRequestURL().toString());
        } else {
            now.setRequest(request.getRequestURL() + "?" + request.getQueryString());
        }

        try {
            now.setIpAddress(InetAddress.getByName(request.getRemoteAddr()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        visits.add(now);
    }

    private void viewSessionActivity(HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/view/viewSessionActivity.jsp")
                .forward(request, response);
    }
}
