package cn.zyp.rpc;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DispatherServlet 进行暴露服务
 */
public class DispatherServlet extends HttpServlet {
    private HttpRequestHandler httpRequestHandler;

    @Override
    public void init() throws ServletException {
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        //寻找与servlet名称相同的HttpRequestHandler
        this.httpRequestHandler = wac.getBean(getServletName(), HttpRequestHandler.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       httpRequestHandler.handleRequest(req,resp);
    }
}
