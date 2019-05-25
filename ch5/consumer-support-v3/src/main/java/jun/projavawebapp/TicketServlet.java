package jun.projavawebapp;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "ticketServlet",
        urlPatterns = "/v3/ticket",
        loadOnStartup = 1)
public class TicketServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!SessionInspector.hasAuthorized(
                req.getSession(), resp, "login")) {
            return;
        }

        String ticketId = req.getParameter("ticketId");

        if (StringUtils.isNoneEmpty(ticketId)) {
            handleTicketView(ticketId, req, resp);
            return;
        }

        req.getRequestDispatcher("/WEB-INF/jsp/view/ticketForm.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!SessionInspector.hasAuthorized(
                req.getSession(), resp, "login")) {
            return;
        }

        try {
            Ticket ticket = TicketFactory.newTicket(req);
            TicketRepository.addTicket(ticket);
            resp.sendRedirect("ticket?ticketId=" + ticket.getId());
        } catch (FileUploadException e) {
            resp.sendRedirect("ticketForm");
            return;
        }
    }

    private void handleTicketView(
            String ticketId, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Ticket ticket = TicketRepository.getTicket(ticketId);

        if (ticket == null) {
            resp.sendRedirect("ticketList");
            return;
        }

        req.setAttribute("ticket", ticket);
        req.getRequestDispatcher("/WEB-INF/jsp/view/ticketView.jsp")
                .forward(req, resp);
    }
}
