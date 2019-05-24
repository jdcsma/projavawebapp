package jun.projavawebapp;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public final class SessionInspector {

    public static boolean authorize(
            HttpSession session, HttpServletResponse response,
            String authorizedRedirect) throws IOException {

        if (session.getAttribute("username") == null) {
            return false;
        }

        handleRedirect(response, authorizedRedirect);

        return true;
    }

    public static boolean illegal(
            HttpSession session, HttpServletResponse response,
            String illegalRedirect) throws IOException {

        if (session.getAttribute("username") != null) {
            return false;
        }

        handleRedirect(response, illegalRedirect);

        return true;
    }

    private static void handleRedirect(
            HttpServletResponse response, String redirect)
            throws IOException {

        if (StringUtils.isNoneEmpty(redirect)) {
            response.sendRedirect(redirect);
        }
    }
}
