package filter;

import entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({
        "/admin/*",
        "/account/changepwd",
        "/account/editprofile",
        "/video/favorite",
        "/video/api/like*",
        "/video/api/liked*",
        "/video/api/share*"
})
public class AuthFilter implements Filter {

    public static final String SecuredURI = "securedURI";
    public static final String usession = "user";

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute(usession);
        String uri = req.getRequestURI();

        if (user == null || (uri.startsWith(req.getContextPath() + "/admin/") && !user.getAdmin())) {
            session.setAttribute(AuthFilter.SecuredURI, uri);
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
