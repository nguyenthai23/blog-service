package vn.example.jetty.blog.service;

import org.springframework.stereotype.Service;
import vn.example.jetty.blog.entity.Blog;
import vn.example.jetty.blog.repository.BlogRepository;
import vn.example.jetty.blog.utils.ServiceUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    private BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
        ServiceUtils.setBlogService(this);
    }

    public List<Blog> getBlogs() {
        return this.blogRepository.findAll().stream().filter(blog -> blog.getStatus()).collect(Collectors.toList());
    }

    public Blog getBlog(long id) {
        Blog blog = this.blogRepository.findById(id).orElse(null);
        if (blog == null || !blog.getStatus()) {
            return null;
        }
        return blog;
    }

    public Blog createBlog(Blog blog) {
        Blog newBlog = new Blog();
        newBlog.setTitle(blog.getTitle());
        newBlog.setContent(blog.getContent());
        newBlog.setStatus(true);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        newBlog.setCreatedDate(now);
        newBlog.setUpdatedDate(now);
        return this.blogRepository.save(newBlog);
    }

    public Blog updateBlog(Blog blog) {
        Blog oldBlog = this.blogRepository.findById(blog.getId()).orElse(null);
        if (oldBlog == null) {
            return oldBlog;
        }

        oldBlog.setTitle(blog.getTitle());
        oldBlog.setContent(blog.getContent());
        oldBlog.setStatus(blog.getStatus());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        oldBlog.setUpdatedDate(now);
        return this.blogRepository.save(oldBlog);
    }

    public void deleteBlog(long id) {
        Blog blog = this.blogRepository.findById(id).orElse(null);
        if (blog == null || !blog.getStatus()) {
            return;
        }

        blog.setStatus(false);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        blog.setUpdatedDate(now);
        this.blogRepository.save(blog);
    }
}
