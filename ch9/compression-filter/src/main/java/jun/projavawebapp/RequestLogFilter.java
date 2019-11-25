package jun.projavawebapp;

import org.apache.commons.lang3.time.StopWatch;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

public class RequestLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Instant time = Instant.now();
        StopWatch timer = new StopWatch();
        timer.start();

        try {
            chain.doFilter(request, response);
        } finally {
            timer.stop();
            HttpServletRequest in = (HttpServletRequest) request;
            HttpServletResponse out = (HttpServletResponse) response;
            String length = out.getHeader("Content-Length");
            if (length == null || length.length() == 0) {
                length = "-";
            }
            System.out.println("REQUEST: " + in.getRemoteAddr() + " - - [" + time + "]" +
                    " \"" + in.getMethod() + " " + in.getRequestURI() + " " +
                    in.getProtocol() + "\" " + out.getStatus() + " " + length + " " + timer);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
