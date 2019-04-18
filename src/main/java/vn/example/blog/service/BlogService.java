package vn.example.blog.service;

import java.util.List;
import java.util.Optional;

import vn.example.blog.dto.BlogDto;
import vn.example.blog.thrift.Blog;

public interface BlogService {
    List<BlogDto> getAll();

    Optional<BlogDto> findById(long blogId);
}