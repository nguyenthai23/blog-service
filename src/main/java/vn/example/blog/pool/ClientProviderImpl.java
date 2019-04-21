package vn.example.blog.pool;

import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.example.blog.thrift.client.ThriftClient;
import vn.example.blog.thrift.client.ThriftClient.BlogClient;

public class ClientProviderImpl implements ClientProvider<BlogClient> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientProviderImpl.class);

    @Override
    public BlogClient obtain() {
        try {
            return ThriftClient.createClient();
        } catch (TTransportException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public void release(BlogClient blogClient) {
        blogClient.close();
    }
}
