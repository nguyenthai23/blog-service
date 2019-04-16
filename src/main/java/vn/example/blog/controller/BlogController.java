package vn.example.blog.controller;

import org.eclipse.jetty.servlet.ServletHandler;
import vn.example.blog.servlet.BlogServlet;

public class BlogController {

    public BlogController(ServletHandler servletHandler) {
        initMapping(servletHandler);
    }

    private void initMapping(ServletHandler servletHandler) {
        this.getMappingBlog(servletHandler);
    }

    private void getMappingBlog(ServletHandler servletHandler) {
        servletHandler.addServletWithMapping(BlogServlet.class, "/blog");
    }
}
