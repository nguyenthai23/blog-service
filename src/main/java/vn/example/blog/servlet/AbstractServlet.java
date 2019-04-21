package vn.example.blog.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServlet.class);

    protected abstract String handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception;
    protected abstract String handlePost(HttpServletRequest request, HttpServletResponse response) throws Exception;
    protected abstract String handlePut(HttpServletRequest request, HttpServletResponse response) throws Exception;
    protected abstract String handleDelete(HttpServletRequest request, HttpServletResponse response) throws Exception;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String result = handleGet(request, response);
            setResponse(response, result, HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            LOGGER.error("Get Exception", ex);
            setResponseError(response, ex.getLocalizedMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String result = handlePost(request, response);
            setResponse(response, result, HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            LOGGER.error("Post Exception", ex);
            setResponseError(response, ex.getLocalizedMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String result = handlePost(request, response);
            setResponse(response, result, HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            LOGGER.error("Put Exception", ex);
            setResponseError(response, ex.getLocalizedMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String result = handlePost(request, response);
            setResponse(response, result, HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            LOGGER.error("Delete Exception", ex);
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
        setResponse(response, message, HttpServletResponse.SC_BAD_GATEWAY);
    }

    protected long getParamLong(HttpServletRequest req, String param, long defaultValue) {
        try {
            return Long.parseLong(req.getParameter(param));
        } catch (Exception ex) {
            return defaultValue;
        }
    }
}