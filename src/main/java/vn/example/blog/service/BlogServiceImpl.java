package vn.example.blog.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.thrift.TException;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import vn.example.blog.dto.BlogDto;
import vn.example.blog.mapper.BlogMapper;
import vn.example.blog.pool.ClientProvider;
import vn.example.blog.thrift.Blog;
import vn.example.blog.thrift.client.ThriftClient.BlogClient;

@Slf4j
public class BlogServiceImpl implements BlogService {

    private final ClientProvider<BlogClient> blogClientProvider;
    private final BlogMapper blogMapper;

    public BlogServiceImpl(ClientProvider<BlogClient> provider, BlogMapper blogMapper) {
        this.blogClientProvider = provider;
        this.blogMapper = blogMapper;
    }

    @Override
    public List<BlogDto> getAll() {
        BlogClient b = blogClientProvider.obtain();
        try {
            return b.getBlogs().stream().map(tBlog -> blogMapper.domain2Dto(tBlog)).collect(Collectors.toList());
        } catch (TException ex) {
            log.error("Fail to get all blogs", ex);
            return Collections.emptyList();
        } finally {
            blogClientProvider.release(b);
        }
    }

    @Override
    public Optional<BlogDto> findById(int blogId) {
        return null;
    }

}