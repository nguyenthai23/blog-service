package vn.example.blog.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;

import lombok.val;
import vn.example.blog.domain.SimpleResponse;
import vn.example.blog.dto.BlogDto;
import vn.example.blog.thrift.Blog;
import vn.example.blog.thrift.BlogService;
import vn.example.blog.thrift.client.ThriftClient;
import vn.example.blog.thrift.client.ThriftClient.BlogClient;

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

    private final vn.example.blog.service.BlogService blogService;
    public BlogServlet(vn.example.blog.service.BlogService blogService){
        this.blogService = blogService;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            //return blog;
            BlogDto dto = blogService.findById(Long.valueOf(id)).orElse(null);
            blogService.HackGoogle();
            if(dto == null){
             // 404   
             throw new BLogNotFound("Blog not found", 404);
            }
            else{

            }
        }

        try(BlogClient blogClient = ThriftClient.createClient()){
            val blogs = blogClient.getBlogs();
        }

        TTransport transport = ThriftClient.getTRANSPORT();
        try {
            BlogService.Client blogService = ThriftClient.getBlogService(transport);
            
     
            List<Blog> blogs = blogService.getBlogs();
            this.setResponse(response, blogs);
        } catch (TException e) {
            e.printStackTrace();
            this.setResponseError(response);
        } finally {
            transport.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TTransport transport = ThriftClient.getTRANSPORT();
        try {
            BlogService.Client blogService = ThriftClient.getBlogService(transport);
            Blog blog = mapper.readValue(IOUtils.toString(request.getReader()), Blog.class);
            blog = blogService.createBlog(blog);
            this.setResponse(response, blog);
        } catch (TException e) {
            e.printStackTrace();
            this.setResponseError(response);
        } finally {
            transport.close();
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TTransport transport = ThriftClient.getTRANSPORT();
        try {
            BlogService.Client blogService = ThriftClient.getBlogService(transport);
            Blog blog = mapper.readValue(IOUtils.toString(request.getReader()), Blog.class);
            blog = blogService.updateBlog(blog);
            this.setResponse(response, blog);
        } catch (TException e) {
            e.printStackTrace();
            this.setResponseError(response);
        } finally {
            transport.close();
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TTransport transport = ThriftClient.getTRANSPORT();
        try {
            BlogService.Client blogService = ThriftClient.getBlogService(transport);
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                blogService.deleteBlog(Long.parseLong(id));
                SimpleResponse simpleResponse = new SimpleResponse();
                simpleResponse.setReturnCode(1);
                simpleResponse.setReturnMsg("success");
                this.setResponse(response, simpleResponse);
                return;
            }
        } catch (TException e) {
            e.printStackTrace();
            this.setResponseError(response);
        } finally {
            transport.close();
        }
    }

    private void setResponse(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(this.mapper.writeValueAsString(obj));
    }

    private void setResponseError(HttpServletResponse response) throws IOException {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setReturnCode(0);
        simpleResponse.setReturnMsg("Error exception unexpected");
        setResponse(response, simpleResponse);
    }
}
