package jun.projavawebapp;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

@WebServlet(
        name = "ticketServlet",
        urlPatterns = "/v3/ticket",
        loadOnStartup = 1)
public class TicketServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (SessionInspector.illegal(
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

        if (SessionInspector.illegal(
                req.getSession(), resp, "login")) {
            return;
        }

        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            resp.sendRedirect("ticketForm");
            return;
        }

        if (items == null) {
            resp.sendRedirect("ticketForm");
            return;
        }

        String subject = null;
        String body = null;
        Attachment attachment = null;

        // Process the uploaded items
        Iterator<FileItem> iter = items.iterator();

        while (iter.hasNext()) {

            FileItem item = iter.next();

            if (item.isFormField()) {
                switch (item.getFieldName()) {
                    case "subject":
                        subject = item.getString();
                        break;
                    case "body":
                        body = item.getString();
                        break;
                }
            } else {
                String fieldName = item.getFieldName();
                String fileName = item.getName();
                String contentType = item.getContentType();
                boolean isInMemory = item.isInMemory();
                long sizeInBytes = item.getSize();

                InputStream is = item.getInputStream();
                ByteArrayOutputStream os = new ByteArrayOutputStream();

                int read;
                final byte[] buffer = new byte[1024];

                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }

                attachment = new Attachment(
                        fileName, os.toByteArray());

                is.close();
            }
        }

        Ticket ticket = new TicketBuilder()
                .setConsumer((String) req.getSession().getAttribute("username"))
                .setSubject(subject)
                .setBody(body)
                .addAttachment(attachment).build();

        TicketRepository.addTicket(ticket);

        resp.sendRedirect("ticket?ticketId=" + ticket.getId());
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
