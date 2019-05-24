package jun.projavawebapp;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "attachmentServlet",
    urlPatterns = "/v3/attachment")
public class AttachmentServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (SessionInspector.illegal(
                req.getSession(), resp, "login")) {
            return;
        }

        String ticketId = req.getParameter("ticketId");
        String attachment = req.getParameter("attachment");

        if (StringUtils.isEmpty(ticketId) ||
                StringUtils.isEmpty(attachment)) {
            resp.sendRedirect("ticketList");
            return;
        }

        Ticket ticket = TicketRepository.getTicket(ticketId);

        if (ticket == null) {
            resp.sendRedirect("ticketList");
            return;
        }

        if (ticket.hasAttachment(attachment)) {
            AttachmentSupport.downloadAttachment(
                    ticket.getAttachment(attachment), resp);
        }

        resp.sendRedirect("ticket?ticketId=" + ticketId);
    }

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (SessionInspector.illegal(
                req.getSession(), resp, "login")) {
            return;
        }

        String ticketId = req.getParameter("ticketId");
        Ticket ticket = TicketRepository.getTicket(ticketId);

        if (ticket == null) {
            resp.sendRedirect("ticketList");
            return;
        }

        Attachment attachment = AttachmentSupport
                .receiveAttachment(req.getPart("file1"));

        if (attachment != null) {
            ticket.addAttachment(attachment);
        }

        resp.sendRedirect("ticket?ticketId=" + ticketId);
    }
}
