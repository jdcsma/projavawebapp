package jun.projavawebapp;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "attachmentServlet",
        urlPatterns = "/v3/attachment")
public class AttachmentServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!SessionInspector.hasAuthorized(
                req.getSession(), resp, "login")) {
            return;
        }

        String ticketId = req.getParameter("ticketId");
        String attachmentName = req.getParameter("attachment");

        if (StringUtils.isEmpty(ticketId) ||
                StringUtils.isEmpty(attachmentName)) {
            resp.sendRedirect("ticketList");
            return;
        }

        Ticket ticket = TicketRepository.getTicket(ticketId);

        if (ticket == null) {
            resp.sendRedirect("ticketList");
            return;
        }

        Attachment attachment = ticket.getAttachment(attachmentName);

        if (attachment != null) {
            AttachmentSupport.download(attachment, resp);
        } else {
            resp.sendRedirect("ticket?ticketId=" + ticketId);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!SessionInspector.hasAuthorized(
                req.getSession(), resp, "login")) {
            return;
        }

        String ticketId;
        List<Attachment> attachments = new ArrayList<>();

        try {
            ticketId = AttachmentSupport.receive(req, attachments);
        } catch (FileUploadException e) {
            resp.sendRedirect("ticketList");
            return;
        }

        Ticket ticket = TicketRepository.getTicket(ticketId);

        if (ticket == null) {
            resp.sendRedirect("ticketList");
            return;
        }

        for (Attachment attachment : attachments) {
            ticket.addAttachment(attachment);
        }

        resp.sendRedirect("ticket?ticketId=" + ticketId);
    }
}
