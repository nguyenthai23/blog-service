package vn.example.blog;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import vn.example.blog.controller.BlogController;

public class EmbeddedJetty {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        initController(servletHandler);

        server.start();
        server.join();
    }

    private static void initController(ServletHandler servletHandler) {
        new BlogController(servletHandler);
    }
}
