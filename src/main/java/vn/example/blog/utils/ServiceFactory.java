package vn.example.blog.utils;

import vn.example.blog.service.BlogService;

public class ServiceFactory {
    private static BlogService blogService;

    public static BlogService getBlogService() {
        if (blogService == null) {
            blogService = new BlogService();
        }
        return blogService;
    }
}
