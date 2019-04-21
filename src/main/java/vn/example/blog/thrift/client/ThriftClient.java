package vn.example.blog.thrift.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import org.apache.thrift.transport.TTransportException;
import vn.example.blog.thrift.Blog;
import vn.example.blog.thrift.BlogService;

import java.util.List;

public class ThriftClient {
    public static final String HOST = "localhost";
    public static final int PORT = 9090;

    public static BlogClient createClient() throws TTransportException {
        return new BlogClient();
    }

    public static class BlogClient implements AutoCloseable, BlogService.Iface {

        private BlogService.Client delegate;

        private TTransport transport;

        private BlogClient() throws TTransportException {
            transport = new TSocket(HOST, PORT);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            this.delegate = new BlogService.Client(protocol);
        }

        @Override
        public void close() {
            if (transport != null) {
                transport.close();
            }
        }

        @Override
        public List<Blog> getBlogs() throws TException {
            return delegate.getBlogs();
        }

        @Override
        public Blog getBlog(long id) throws TException {
            return delegate.getBlog(id);
        }

        @Override
        public Blog createBlog(Blog blog) throws TException {
            return delegate.createBlog(blog);
        }

        @Override
        public Blog updateBlog(Blog blog) throws TException {
            return delegate.updateBlog(blog);
        }

        @Override
        public void deleteBlog(long id) throws TException {
            delegate.deleteBlog(id);
        }
    }

}
