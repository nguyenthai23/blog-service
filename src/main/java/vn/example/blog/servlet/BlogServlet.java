package vn.example.blog.servlet;

import vn.example.blog.domain.SimpleResponse;
import vn.example.blog.dto.BlogDto;
import vn.example.blog.mapper.BlogMapper;
import vn.example.blog.pool.ClientProviderImpl;
import vn.example.blog.service.BlogServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Optional;

public class BlogServlet extends BaseJsonServlet {

    private final vn.example.blog.service.BlogService blogService = new BlogServiceImpl(new ClientProviderImpl(), new BlogMapper());

    public BlogServlet() {
    }

    @Override
    protected Object executeGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = super.getParamLong(request, "id", -1);
        if (id != -1) {
            Optional<BlogDto> optionalBlogDto = this.blogService.findById(id);
            if (optionalBlogDto.isPresent()) {
                return optionalBlogDto.get();
            }
            return Collections.emptyList();
        }
        return this.blogService.getAll();
    }

    @Override
    protected Object executePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BlogDto blog = super.getRequestBody(request, BlogDto.class);
        return this.blogService.createBlog(blog);
    }

    @Override
    protected Object executePut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BlogDto blog = super.getRequestBody(request, BlogDto.class);
        return this.blogService.updateBlog(blog);
    }

    @Override
    protected Object executeDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = super.getParamLong(request, "id", -1);
        if (id != -1) {
            this.blogService.deleteBlog(id);
        }
        return new SimpleResponse(0, "success");
    }
}
