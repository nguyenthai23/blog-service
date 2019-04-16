package vn.example.jetty.blog.controller;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import vn.example.jetty.blog.service.BlogService;
import vn.example.jetty.blog.servlet.BlogServlet;

@Component
public class JettyController {
    private Server server;
    private ServletHandler servletHandler;

    public JettyController() {
        Thread thread = new Thread(() -> {
            this.server = new Server(8080);
            this.servletHandler = new ServletHandler();
            this.server.setHandler(this.servletHandler);

            this.initMapping();

            try {
                this.server.start();
                this.server.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void initMapping() {
        this.getMappingBlog();
    }

    private void getMappingBlog() {
        this.servletHandler.addServletWithMapping(BlogServlet.class, "/blog");
    }
}
