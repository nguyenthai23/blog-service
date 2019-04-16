package vn.example.blog.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import vn.example.blog.domain.SimpleResponse;
import vn.example.blog.entity.Blog;
import vn.example.blog.service.BlogService;
import vn.example.blog.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BlogServlet extends HttpServlet {

    public final String UTF_8 = "UTF-8";
    public final String APPLICATION_JSON = "application/json";

    private final ObjectMapper mapper = new ObjectMapper();

    private BlogService blogService = ServiceFactory.getBlogService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            Blog blog = this.blogService.getBlog(Long.parseLong(id));
            this.setResponse(response, blog);
            return;
        }
        List<Blog> blogs = this.blogService.getBlogs();
        this.setResponse(response, blogs);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Blog blog = mapper.readValue(IOUtils.toString(request.getReader()), Blog.class);
        blog = this.blogService.createBlog(blog);
        this.setResponse(response, blog);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Blog blog = mapper.readValue(IOUtils.toString(request.getReader()), Blog.class);
        blog = this.blogService.updateBlog(blog);
        this.setResponse(response, blog);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            this.blogService.deleteBlog(Long.parseLong(id));
            SimpleResponse simpleResponse = new SimpleResponse();
            simpleResponse.setReturnCode(1);
            simpleResponse.setReturnMsg("success");
            this.setResponse(response, simpleResponse);
            return;
        }
    }

    private void setResponse(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(this.mapper.writeValueAsString(obj));
    }
}
