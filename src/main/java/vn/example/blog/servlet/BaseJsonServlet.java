package vn.example.blog.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseJsonServlet extends AbstractServlet {

    public final String APPLICATION_JSON = "application/json";
    private final ObjectMapper mapper = new ObjectMapper();

    protected abstract Object executeGet(HttpServletRequest request, HttpServletResponse response) throws Exception;

    protected abstract Object executePost(HttpServletRequest request, HttpServletResponse response) throws Exception;

    protected abstract Object executePut(HttpServletRequest request, HttpServletResponse response) throws Exception;

    protected abstract Object executeDelete(HttpServletRequest request, HttpServletResponse response) throws Exception;

    @Override
    protected String handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON);
        return this.mapper.writeValueAsString(executeGet(request, response));
    }

    @Override
    protected String handlePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON);
        return this.mapper.writeValueAsString(executePost(request, response));
    }

    @Override
    protected String handlePut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON);
        return this.mapper.writeValueAsString(executePut(request, response));
    }

    @Override
    protected String handleDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON);
        return this.mapper.writeValueAsString(executeDelete(request, response));
    }

    protected <T> T getRequestBody(HttpServletRequest request, Class<T> valueType) throws IOException {
        return mapper.readValue(IOUtils.toString(request.getReader()), valueType);
    }
}