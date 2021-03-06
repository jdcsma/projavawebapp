package jun.projavawebapp.site;

import jun.projavawebapp.site.entities.UserPrincipal;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String id = UUID.randomUUID().toString();
        ThreadContext.put("id", id);
        Principal principal = UserPrincipal.getPrincipal(
                ((HttpServletRequest) request).getSession(false)
        );
        if (principal != null)
            ThreadContext.put("username", principal.getName());

        try {
            ((HttpServletResponse) response).setHeader("X-Wrox-Request-Id", id);
            chain.doFilter(request, response);
        } finally {
            ThreadContext.clearAll();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
