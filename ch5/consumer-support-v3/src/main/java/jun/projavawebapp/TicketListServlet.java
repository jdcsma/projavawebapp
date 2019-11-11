package jun.projavawebapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "ticketListServlet",
        urlPatterns = "/v3/ticketList",
        loadOnStartup = 1)
public class TicketListServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!SessionInspector.hasAuthorized(
                req.getSession(), resp, "login")) {
            return;
        }

        req.setAttribute("tickets", TicketRepository.getTickets());
        req.getRequestDispatcher("/WEB-INF/jsp/view/ticketList.jsp")
                .forward(req, resp);
    }
}
