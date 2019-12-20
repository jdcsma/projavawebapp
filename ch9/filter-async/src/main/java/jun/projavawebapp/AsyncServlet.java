package jun.projavawebapp;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "asyncServlet", urlPatterns = "/async",
        asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    private static volatile int ID = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        final int id;
        synchronized (AsyncServlet.class) {
            id = ID++;
        }

        long timeout = req.getParameter("timeout") == null ?
                10_000L : Long.parseLong(req.getParameter("timeout"));

        System.out.println("Entering AsyncServlet.doGet(). Request ID = " + id +
                ", isAsyncStarted = " + req.isAsyncStarted());

        /**
         * 当前 req 为 AnyRequestFilter 调用 chain.doFilter 方法时传入的 HttpServletRequestWrapper 对象实例
         * 当前 resp 为 AnyRequestFilter 调用 chain.doFilter 方法时传入的 HttpServletResponseWrapper 对象实例
         */
        final AsyncContext context = req.getParameter("unwrap") != null
                // AsyncContext 将使用原始的请求和响应对象：HttpServletRequest 和 HttpServletResponse
                ? req.startAsync()
                // AsyncContext 将使用封装后的请求和响应对象: HttpServletRequestWrapper 和 HttpServletResponseWrapper
                : req.startAsync(req, resp);
        context.setTimeout(timeout);

        System.out.println("Starting asynchronous thread. Request ID = " + id);

        Runnable runnable = new AsyncThread(id, context)::doWork;
        context.start(runnable);

        System.out.println("Leaving AsyncServlet.doGet(). Request ID = " + id +
                ", isAsyncStarted = " + req.isAsyncStarted());
    }

    private static class AsyncThread {

        private final int id;
        private final AsyncContext context;

        public AsyncThread(int id, AsyncContext context) {
            this.id = id;
            this.context = context;
        }

        public void doWork() {
            System.out.println("Asynchronous thread started. Request ID = " + id);

            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            HttpServletRequest request =
                    (HttpServletRequest) context.getRequest();

            System.out.println("Done sleeping. Request ID = " + id +
                    ", URL = " + request.getRequestURL());

            context.dispatch("/WEB-INF/jsp/view/async.jsp");

            System.out.println("Asynchronous thread completed. Request ID = " + id);
        }
    }
}
