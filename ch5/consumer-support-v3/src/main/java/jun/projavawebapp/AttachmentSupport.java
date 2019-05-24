package jun.projavawebapp;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class AttachmentSupport {

    public static Attachment receiveAttachment(
            Part filePart) throws IOException {

        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }

        InputStream is = filePart.getInputStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        int read;
        final byte[] buffer = new byte[1024];

        while ((read = is.read(buffer)) != -1) {
            os.write(buffer, 0, read);
        }

        return new Attachment(
                filePart.getSubmittedFileName(),
                os.toByteArray());
    }

    public static void downloadAttachment(
            Attachment attachment, HttpServletResponse resp)
            throws IOException {

        resp.setHeader("Content-Disposition",
                "attachment; filename=" + attachment.getName());
        resp.setContentType("application/octet-stream");

        ServletOutputStream stream = resp.getOutputStream();
        stream.write(attachment.getContent());
    }
}
