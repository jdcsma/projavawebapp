package jun.projavawebapp;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public final class AttachmentSupport {

    public static String receive(HttpServletRequest request, List<Attachment> attachments)
            throws FileUploadException, IOException {

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request
        List<FileItem> items = upload.parseRequest(request);

        if (items == null || items.size() == 0) {
            return null;
        }

        String ticketId = null;

        // Process the uploaded items

        Iterator<FileItem> iterator = items.iterator();

        while (iterator.hasNext()) {

            FileItem fileItem = iterator.next();

            if (fileItem.isFormField()) {
                switch (fileItem.getFieldName()) {
                    case "ticketId":
                        ticketId = fileItem.getString();
                        break;
                }

            } else {
                Attachment attachment =
                        AttachmentSupport.receive(fileItem);

                if (attachment != null) {
                    attachments.add(attachment);
                }
            }
        }

        return ticketId;
    }

    public static Attachment receive(
            FileItem fileItem) throws IOException {

        if (!isAcceptedFile(fileItem)) {
            return null;
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try (InputStream is = fileItem.getInputStream()) {

            final byte[] buffer = new byte[1024];

            while (true) {

                int read = is.read(buffer);
                if (read == -1) {
                    break;
                }

                os.write(buffer, 0, read);
            }
        }

        return new Attachment(fileItem.getName(), os.toByteArray());
    }

    public static void download(
            Attachment attachment, HttpServletResponse response)
            throws IOException {

        response.setHeader("Content-Disposition",
                "attachment; filename=" + attachment.getName());
        response.setContentType("application/octet-stream");

        ServletOutputStream stream = response.getOutputStream();
        stream.write(attachment.getContent());
    }

    public static boolean isAcceptedFile(FileItem fileItem) {

        if (fileItem == null ||
                fileItem.isFormField() ||
                fileItem.getSize() == 0) {
            return false;
        }

        return true;
    }
}
