package lab5;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/counter")
public class filter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setAttribute("hello", "I'm filter 1");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
