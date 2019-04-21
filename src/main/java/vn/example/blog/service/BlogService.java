package vn.example.blog.service;

import java.util.List;
import java.util.Optional;

import vn.example.blog.dto.BlogDto;

public interface BlogService {
    List<BlogDto> getAll();

    Optional<BlogDto> findById(long blogId);

    BlogDto createBlog(BlogDto blog);

    BlogDto updateBlog(BlogDto blog);

    void deleteBlog(long id);
}