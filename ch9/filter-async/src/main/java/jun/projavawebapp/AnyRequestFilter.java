package jun.projavawebapp;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

public class AnyRequestFilter implements Filter {

    private String name;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("Entering " + name + ".doFilter().");

        chain.doFilter(
                new HttpServletRequestWrapper((HttpServletRequest) request),
                new HttpServletResponseWrapper((HttpServletResponse) response));

        if (request.isAsyncSupported() && request.isAsyncStarted()) {
            AsyncContext context = request.getAsyncContext();
            System.out.println("Leaving " + name + ".doFilter(), async " +
                    "context holds wrapped request/response = " +
                    !context.hasOriginalRequestAndResponse());
        } else {
            System.out.println("Leaving " + name + ".doFilter().");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        name = filterConfig.getFilterName();
    }

    @Override
    public void destroy() {

    }
}
