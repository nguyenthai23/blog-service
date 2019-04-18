package vn.example.blog;

import java.util.ArrayList;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import vn.example.blog.controller.BlogController;

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
        ArrayList
        throw new Exception();

        new BlogController(servletHandler);
    }



}
