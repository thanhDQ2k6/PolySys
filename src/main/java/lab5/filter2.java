package lab5;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/counter")
public class filter2 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println(request.getAttribute("hello"));

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
