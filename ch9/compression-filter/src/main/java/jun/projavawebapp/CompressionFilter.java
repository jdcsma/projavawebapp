package jun.projavawebapp;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

public class CompressionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (((HttpServletRequest) request).
                getHeader("Accept-Encoding").contains("gzip")) {

            System.out.println("Encoding requested.");

            ((HttpServletResponse) response).setHeader("Content-Encoding", "gzip");
            ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);

            try {
                chain.doFilter(request, wrapper);
            } finally {
                try {
                    wrapper.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Encoding not requested.");

            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    private static class GZIPServletOutputStream
            extends ServletOutputStream {

        private final ServletOutputStream servletOutputStream;
        private final GZIPOutputStream gzipOutputStream;

        public GZIPServletOutputStream(ServletOutputStream servletOutputStream)
                throws IOException {
            this.servletOutputStream = servletOutputStream;
            this.gzipOutputStream = new GZIPOutputStream(servletOutputStream);
        }

        @Override
        public boolean isReady() {
            return this.servletOutputStream.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            this.servletOutputStream.setWriteListener(writeListener);
        }

        @Override
        public void write(int b) throws IOException {
            this.gzipOutputStream.write(b);
        }

        @Override
        public void close() throws IOException {
            this.gzipOutputStream.close();
        }

        @Override
        public void flush() throws IOException {
            this.gzipOutputStream.flush();
        }
    }

    private static class ResponseWrapper
            extends HttpServletResponseWrapper {

        private GZIPServletOutputStream outputStream;
        private PrintWriter writer;

        public ResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            if (this.writer != null) {
                throw new IllegalStateException("getWriter() already called.");
            }
            if (this.outputStream == null) {
                this.outputStream = new GZIPServletOutputStream(super.getOutputStream());
            }
            return this.outputStream;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            if (this.writer == null && this.outputStream != null) {
                throw new IllegalStateException("getWriter() already called.");
            }
            if (this.writer == null) {
                this.outputStream = new GZIPServletOutputStream(super.getOutputStream());
                this.writer = new PrintWriter(new OutputStreamWriter(
                        this.outputStream, this.getCharacterEncoding()));
            }
            return this.writer;
        }

        @Override
        public void flushBuffer() throws IOException {
            if (this.writer != null) {
                this.writer.flush();
            } else if (this.outputStream != null) {
                this.outputStream.flush();
            }
            super.flushBuffer();
        }

        @Override
        public void setContentLength(int len) {
        }

        @Override
        public void setContentLengthLong(long len) {
        }

        @Override
        public void setHeader(String name, String value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.setHeader(name, value);
            }
        }

        @Override
        public void addHeader(String name, String value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.addHeader(name, value);
            }
        }

        @Override
        public void setIntHeader(String name, int value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.setIntHeader(name, value);
            }
        }

        @Override
        public void addIntHeader(String name, int value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.addIntHeader(name, value);
            }
        }

        public void close() throws IOException {
            if (this.writer != null) {
                this.writer.close();
            } else if (this.outputStream != null) {
                this.outputStream.close();
            }
        }
    }
}
