package jun.projavawebapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet(name = "actionServlet", urlPatterns = "/files")
public class ActionServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            logger.info("Received request with action {}.", action);
            String contents = null;
            switch (action) {
                case "readFoo":
                    contents = this.readFile(getServletContext().
                            getRealPath("WEB-INF/static/foo.bar"), true);
                    break;
                case "readLicense":
                    contents = this.readFile(getServletContext().
                            getRealPath("WEB-INF/static/LICENSE"), false);
                    break;
                default:
                    contents = "Bad action " + action + " specified.";
                    logger.warn("Action {} not supported.", action);
            }
            if (contents != null)
                response.getWriter().write(contents);
        } else {
            logger.error("No action specified.");
            response.getWriter().write("No action specified.");
        }
    }

    protected String readFile(String fileName, boolean deleteWhenDone) {

        logger.traceEntry("{} {}", fileName, deleteWhenDone);

        try {
            byte[] data = Files.readAllBytes(new File(fileName).toPath());
            logger.info("Successfully read file {}.", fileName);
            return logger.traceExit(new String(data));
        } catch (IOException e) {
            logger.error(MarkerManager.getMarker("LOG_CONSOLE"),
                    "Failed to read file {}.", fileName, e);
            return null;
        }
    }
}
