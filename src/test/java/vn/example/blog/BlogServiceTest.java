package vn.example.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogService blogService;

    @Test
    public void testCreateBlog() {
        Blog blog = new Blog();
        blog.setTitle("title new");
        blog.setContent("content new");
        Mockito.when(blogRepository.save(Mockito.any(Blog.class))).then(new Answer<Blog>() {
            @Override
            public Blog answer(InvocationOnMock invocation) throws Throwable {
                Blog blog = (Blog) invocation.getArgument(0);
                if (blog.getId() == null) {
                    blog.setId(Long.valueOf(new Random().nextInt(100) + 1));
                }
                return blog;
            }
        });

        Blog newBlog = this.blogService.createBlog(blog);
        loggerInfo(blog.getTitle(), newBlog.getTitle(), "testCreateBlog title");
        Assertions.assertEquals(blog.getTitle(), newBlog.getTitle());

        loggerInfo(blog.getContent(), newBlog.getContent(), "testCreateBlog content");
        Assertions.assertEquals(blog.getContent(), newBlog.getContent());
    }

    @Test
    public void testUpdateBlog() throws InterruptedException {
        Blog blog = new Blog();
        blog.setId(Long.valueOf(10));
        blog.setTitle("title update");
        blog.setContent("content update");
        Timestamp oldDate = Timestamp.valueOf(LocalDateTime.now());
        blog.setCreatedDate(oldDate);
        blog.setUpdatedDate(oldDate);
        Thread.sleep(1);
        Mockito.when(blogRepository.findById(Long.valueOf(10))).thenReturn(blog);
        Mockito.when(blogRepository.update(Mockito.any(Blog.class))).then(new Answer<Blog>() {
            @Override
            public Blog answer(InvocationOnMock invocation) throws Throwable {
                return (Blog) invocation.getArgument(0);
            }
        });

        Blog updateBlog = this.blogService.updateBlog(blog);
        loggerInfo(blog.getTitle(), updateBlog.getTitle(), "testUpdateBlog title");
        Assertions.assertEquals(blog.getTitle(), updateBlog.getTitle());

        loggerInfo(blog.getContent(), updateBlog.getContent(), "testUpdateBlog content");
        Assertions.assertEquals(blog.getContent(), updateBlog.getContent());

        loggerInfo(oldDate, updateBlog.getUpdatedDate(), "testUpdateBlog updated_date");
        Assertions.assertNotEquals(oldDate, updateBlog.getUpdatedDate());
    }

    @Test
    public void testGetBlog() throws JsonProcessingException {
        Blog blog = new Blog();
        blog.setId(Long.valueOf(10));
        blog.setStatus(true);
        blog.setTitle("title test");
        blog.setContent("content test");
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        blog.setCreatedDate(now);
        blog.setUpdatedDate(now);
        Mockito.when(blogRepository.findAll()).thenReturn(Arrays.asList(blog));

        List<Blog> blogs = this.blogService.getBlogs();
        ObjectMapper mapper = new ObjectMapper();
        loggerInfo(mapper.writeValueAsString(Arrays.asList(blog).toArray()), mapper.writeValueAsString(blogs.toArray()), "testGetBlog");
        Assertions.assertArrayEquals(Arrays.asList(blog).toArray(), blogs.toArray());
    }

    @Test
    public void testDeleteBlog() {
        Blog blog = new Blog();
        blog.setId(Long.valueOf(10));
        blog.setStatus(true);
        blog.setTitle("title delete");
        blog.setContent("content delete");
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        blog.setCreatedDate(now);
        blog.setUpdatedDate(now);
        Mockito.when(blogRepository.findById(Long.valueOf(10))).thenReturn(blog);

        this.blogService.deleteBlog(Long.valueOf(10));
        Blog blogDelete = this.blogService.getBlog(Long.valueOf(10));
        loggerInfo(null, blogDelete, "testDeleteBlog");
        Assertions.assertNull(blogDelete);
    }

    protected void loggerInfo(Object expected, Object actual, String msg) {
        if (msg != null && !msg.isEmpty()) {
            logger.info(msg + ": ");
        }
        logger.info("Expected: " + expected + ", Actual: " + actual);
    }
}
