package just.fun;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException {
        // do nothing
    }

    public void destroy() {
        // do nothing
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest
                && response instanceof HttpServletResponse) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            HttpSession session = servletRequest.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                servletResponse.sendRedirect("login.jsp");
            } else {
                chain.doFilter(request, response);
            }
        } else if (!response.isCommitted()) {
            chain.doFilter(request, response);
        }
    }
}
