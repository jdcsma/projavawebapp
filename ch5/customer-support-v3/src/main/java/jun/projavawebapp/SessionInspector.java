package jun.projavawebapp;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public final class SessionInspector {

    public static boolean hasAuthorized(HttpSession session) {
        return session.getAttribute("username") != null;
    }

    public static boolean hasAuthorized(
            HttpSession session, HttpServletResponse response,
            String redirect) throws IOException {

        if (hasAuthorized(session)) {
            return true;
        }

        if (StringUtils.isNoneEmpty(redirect)) {
            response.sendRedirect(redirect);
        }

        return false;
    }
}
