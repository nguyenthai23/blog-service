package vn.example.blog.thrift.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import lombok.Delegate;
import vn.example.blog.thrift.BlogService;

public class ThriftClient {
    public static final String HOST = "localhost";
    public static final int PORT = 9090;

    public static BlogClient createClient(){
        return new BlogClient();
    }

    public static class BlogClient implements AutoCloseable, BlogService.Iface {

        @Delegate
        private BlogService.Client delegate;
        
        private TTransport transport ;
        private BlogClient(){
            TTransport transport = new TSocket(HOST, PORT);
            TProtocol protocol = new TBinaryProtocol(transport);
            this.delegate = new BlogService.Client(protocol);
        }

        @Override
		public void close()  {
            if(transport!= null)
                transport.close();
		}

    }

}
