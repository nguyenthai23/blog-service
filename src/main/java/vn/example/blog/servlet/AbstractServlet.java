package vn.example.blog.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.example.blog.thrift.client.ThriftClient;

public abstract class AbstractServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServlet.class);

    protected abstract String handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String result = handleGet(request, response);
            setResponse(response, result, 200);
        } catch (Exception ex) {
            LOGGER.error("Get Exception", ex);
            setResponseError(response, ex.getLocalizedMessage());
        }
    }

    private void setResponse(HttpServletResponse response, String stringResponse, int responseStatus)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(responseStatus);
        response.getWriter().println(stringResponse);
    }

    private void setResponseError(HttpServletResponse response, String message) throws IOException {
        setResponse(response, message, 502);
    }
}