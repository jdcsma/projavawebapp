package jun.projavawebapp;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class TicketFactory {

    public static Ticket newTicket(HttpServletRequest request)
            throws FileUploadException, IOException {

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request
        List<FileItem> items = upload.parseRequest(request);

        if (items == null) {
            return null;
        }

        TicketBuilder builder = new TicketBuilder();

        builder.setConsumer((String) request.getSession()
                .getAttribute("username"));

        // Process the uploaded items
        Iterator<FileItem> iterator = items.iterator();

        while (iterator.hasNext()) {

            FileItem fileItem = iterator.next();

            if (fileItem.isFormField()) {
                switch (fileItem.getFieldName()) {
                    case "subject":
                        builder.setSubject(fileItem.getString());
                        break;
                    case "body":
                        builder.setBody(fileItem.getString());
                        break;
                }
            } else {
                builder.addAttachment(AttachmentSupport.receive(fileItem));
            }
        }

        try {
            return builder.build();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
