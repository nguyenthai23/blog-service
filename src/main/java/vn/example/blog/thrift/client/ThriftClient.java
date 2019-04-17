package vn.example.blog.thrift.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import vn.example.blog.thrift.BlogService;

public class ThriftClient {
    public static final String HOST = "localhost";
    public static final int PORT = 9090;
    private static final TTransport TRANSPORT = new TSocket(HOST, PORT);

    public static BlogService.Client getBlogService(TTransport TRANSPORT) {
        try {
            TRANSPORT.open();
            TProtocol protocol = new TBinaryProtocol(TRANSPORT);
            return new BlogService.Client(protocol);
        } catch (TException x) {
            x.printStackTrace();
        }
        return null;
    }

    public static TTransport getTRANSPORT() {
        return TRANSPORT;
    }
}
