package vn.example.jetty.blog.utils;

import vn.example.jetty.blog.service.BlogService;

public class ServiceUtils {
    private static BlogService blogService;

    public static void init(BlogService blogService) {
        ServiceUtils.blogService = blogService;
    }

    public static BlogService getBlogService() {
        return blogService;
    }

    public static void setBlogService(BlogService blogService) {
        ServiceUtils.blogService = blogService;
    }
}
