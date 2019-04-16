package vn.example.blog.service;

import vn.example.blog.entity.Blog;
import vn.example.blog.repository.BlogRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class BlogService {
    private BlogRepository blogRepository = new BlogRepository();

    public List<Blog> getBlogs() {
        return this.blogRepository.findAll().stream().filter(blog -> blog.getStatus()).collect(Collectors.toList());
    }

    public Blog getBlog(long id) {
        Blog blog = this.blogRepository.findById(id);
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
        Blog oldBlog = this.blogRepository.findById(blog.getId());
        if (oldBlog == null) {
            return oldBlog;
        }

        oldBlog.setTitle(blog.getTitle());
        oldBlog.setContent(blog.getContent());
        oldBlog.setStatus(blog.getStatus());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        oldBlog.setUpdatedDate(now);
        return this.blogRepository.update(oldBlog);
    }

    public void deleteBlog(long id) {
        Blog blog = this.blogRepository.findById(id);
        if (blog == null || !blog.getStatus()) {
            return;
        }

        blog.setStatus(false);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        blog.setUpdatedDate(now);
        this.blogRepository.update(blog);
    }
}
