package vn.example.blog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.thrift.TException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.example.blog.dto.BlogDto;
import vn.example.blog.mapper.BlogMapper;
import vn.example.blog.pool.ClientProviderImpl;
import vn.example.blog.thrift.Blog;
import vn.example.blog.thrift.client.ThriftClient.BlogClient;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BlogClient blogClient;

    ClientProviderImpl clientProvider;

    private BlogService blogService;

    private BlogMapper blogMapper;

    public BlogServiceTest() {
        this.blogClient = Mockito.mock(BlogClient.class);
        this.clientProvider = Mockito.mock(ClientProviderImpl.class);
        blogMapper = new BlogMapper();
        blogService = new BlogServiceImpl(clientProvider, blogMapper);
        Mockito.when(clientProvider.obtain()).thenReturn(blogClient);
        doNothing().when(clientProvider).release(blogClient);
    }

    @Test
    public void testCreateBlog() throws TException {
        BlogDto blog = new BlogDto();
        blog.setTitle("title new");
        blog.setContent("content new");
        Mockito.when(blogClient.createBlog(Mockito.any(Blog.class))).then(new Answer<Blog>() {
            @Override
            public Blog answer(InvocationOnMock invocation) throws Throwable {
                Blog blog = (Blog) invocation.getArgument(0);
                blog.setId(Long.valueOf(new Random().nextInt(100) + 1));
                return blog;
            }
        });

        BlogDto newBlog = this.blogService.createBlog(blog);
        loggerInfo(blog.getTitle(), newBlog.getTitle(), "testCreateBlog title");
        Assertions.assertEquals(blog.getTitle(), newBlog.getTitle());

        loggerInfo(blog.getContent(), newBlog.getContent(), "testCreateBlog content");
        Assertions.assertEquals(blog.getContent(), newBlog.getContent());
    }

    @Test
    public void testUpdateBlog() throws InterruptedException, TException {
        BlogDto blog = new BlogDto();
        blog.setId(Long.valueOf(10));
        blog.setTitle("title update");
        blog.setContent("content update");
        String oldDate = String.valueOf(new Date().getTime());
        blog.setUpdatedDate(oldDate);
        Thread.sleep(1);
        Mockito.when(blogClient.updateBlog(Mockito.any(Blog.class))).then(new Answer<Blog>() {
            @Override
            public Blog answer(InvocationOnMock invocation) throws Throwable {
                Blog argument = (Blog) invocation.getArgument(0);
                Thread.sleep(1);
                argument.setUpdatedDate(String.valueOf(new Date().getTime()));
                return argument;
            }
        });

        BlogDto updateBlog = this.blogService.updateBlog(blog);
        loggerInfo(blog.getTitle(), updateBlog.getTitle(), "testUpdateBlog title");
        Assertions.assertEquals(blog.getTitle(), updateBlog.getTitle());

        loggerInfo(blog.getContent(), updateBlog.getContent(), "testUpdateBlog content");
        Assertions.assertEquals(blog.getContent(), updateBlog.getContent());

        loggerInfo(oldDate, updateBlog.getUpdatedDate(), "testUpdateBlog updated_date");
        Assertions.assertNotEquals(oldDate, updateBlog.getUpdatedDate());
    }

    @Test
    public void testGetBlog() throws JsonProcessingException, TException {
        Blog blog = new Blog();
        blog.setId(Long.valueOf(10));
        blog.setStatus(true);
        blog.setTitle("title test");
        blog.setContent("content test");
        blog.setUpdatedDate(String.valueOf(new Date().getTime()));
        Mockito.when(blogClient.getBlogs()).thenReturn(Arrays.asList(blog));

        List<BlogDto> blogs = this.blogService.getAll();
        ObjectMapper mapper = new ObjectMapper();
        loggerInfo(mapper.writeValueAsString(Arrays.asList(blogMapper.domain2Dto(blog)).toArray()), mapper.writeValueAsString(blogs.toArray()), "testGetBlog");
        Assertions.assertEquals(mapper.writeValueAsString(Arrays.asList(blogMapper.domain2Dto(blog)).toArray()), mapper.writeValueAsString(blogs.toArray()));
    }

    protected void loggerInfo(Object expected, Object actual, String msg) {
        if (msg != null && !msg.isEmpty()) {
            logger.info(msg + ": ");
        }
        logger.info("Expected: " + expected + ", Actual: " + actual);
    }
}
