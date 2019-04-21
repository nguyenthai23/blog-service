package vn.example.blog;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import vn.example.blog.servlet.BlogServlet;

public class EmbeddedJetty {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        initHandler(servletHandler);

        server.start();
        server.join();
    }

    private static void initHandler(ServletHandler servletHandler) {
        // Side effect
        servletHandler.addServletWithMapping(BlogServlet.class, "/blog");
    }
}
