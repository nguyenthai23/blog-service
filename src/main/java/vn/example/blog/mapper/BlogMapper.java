package vn.example.blog.mapper;

import vn.example.blog.dto.BlogDto;
import vn.example.blog.thrift.Blog;

public class BlogMapper {
    public BlogDto domain2Dto(Blog domain) {
        BlogDto result = new BlogDto();
        result.setId(domain.getId());
        result.setContent(domain.getContent());
        result.setTitle(domain.getTitle());
        result.setStatus(domain.isStatus());
        result.setUpdatedDate(domain.getUpdatedDate());
        return result;
    }

    public Blog dto2Domain(BlogDto blogDto) {
        Blog blog = new Blog();
        blog.setId(blogDto.getId());
        blog.setContent(blogDto.getContent());
        blog.setStatus(blogDto.getStatus());
        blog.setTitle(blogDto.getTitle());
        return blog;
    }
}