package top.sharex.oes.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author danielyang
 * @Date 2017/11/9 16:55
 */
@Order(1)
@WebFilter(filterName = "LoginFilter", urlPatterns = "/backend/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        ((HttpServletResponse) servletResponse).sendRedirect("/login.jsp");
        System.out.println("TestFilter1");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
