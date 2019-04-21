package vn.example.blog.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.thrift.TException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.example.blog.dto.BlogDto;
import vn.example.blog.mapper.BlogMapper;
import vn.example.blog.pool.ClientProvider;
import vn.example.blog.thrift.Blog;
import vn.example.blog.thrift.client.ThriftClient.BlogClient;

public class BlogServiceImpl implements BlogService {
    private static final Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);
    private final ClientProvider<BlogClient> blogClientProvider;
    private final BlogMapper blogMapper;

    public BlogServiceImpl(ClientProvider<BlogClient> provider, BlogMapper blogMapper) {
        this.blogClientProvider = provider;
        this.blogMapper = blogMapper;
    }

    @Override
    public List<BlogDto> getAll() {
        BlogClient blogClient = blogClientProvider.obtain();
        try {
            return blogClient.getBlogs().stream().map(tBlog -> blogMapper.domain2Dto(tBlog)).collect(Collectors.toList());
        } catch (TException ex) {
            log.error("Fail to get all blogs", ex);
            return Collections.emptyList();
        } finally {
            blogClientProvider.release(blogClient);
        }
    }

    @Override
    public Optional<BlogDto> findById(long blogId) {
        BlogClient blogClient = this.blogClientProvider.obtain();
        try {
            Blog blog = blogClient.getBlog(blogId);
            if (blog == null) {
                return Optional.empty();
            }
            return Optional.of(blogMapper.domain2Dto(blog));
        } catch (TException e) {
            log.error("Fail to get blog", e);
            return Optional.empty();
        } finally {
            this.blogClientProvider.release(blogClient);
        }
    }

    @Override
    public BlogDto createBlog(BlogDto blogDto) {
        BlogClient blogClient = this.blogClientProvider.obtain();
        try {
            Blog blog = blogClient.createBlog(blogMapper.dto2Domain(blogDto));
            return blogMapper.domain2Dto(blog);
        } catch (TException e) {
            log.error(e.getLocalizedMessage(), e);
        } finally {
            this.blogClientProvider.release(blogClient);
        }
        return null;
    }

    @Override
    public BlogDto updateBlog(BlogDto blogDto) {
        BlogClient blogClient = this.blogClientProvider.obtain();
        try {
            Blog blog = blogClient.updateBlog(blogMapper.dto2Domain(blogDto));
            return blogMapper.domain2Dto(blog);
        } catch (TException e) {
            log.error(e.getLocalizedMessage(), e);
        } finally {
            this.blogClientProvider.release(blogClient);
        }
        return null;
    }

    @Override
    public void deleteBlog(long id) {
        BlogClient blogClient = this.blogClientProvider.obtain();
        try {
            blogClient.deleteBlog(id);
        } catch (TException e) {
            log.error(e.getLocalizedMessage(), e);
        } finally {
            this.blogClientProvider.release(blogClient);
        }
    }
}