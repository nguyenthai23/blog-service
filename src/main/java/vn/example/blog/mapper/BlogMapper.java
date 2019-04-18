package vn.example.blog.mapper;

import java.util.Arrays;

import vn.example.blog.dto.BlogDto;
import vn.example.blog.thrift.Blog;

public class BlogMapper {
    public BlogDto domain2Dto(Blog domain){
        BlogDto result =  new BlogDto();
        result.setId(domain.getId());
        // Database/Blog doesnt know this
        result.setReferencees( Arrays.asList("liemlhd") );
        return result;
    }
}