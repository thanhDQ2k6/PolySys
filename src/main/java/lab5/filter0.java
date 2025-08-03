package lab5;

import entity.User;
import utils.XJPA;

import javax.persistence.EntityManager;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/counter")
public class filter0 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try (XJPA tx = new XJPA()) {
            EntityManager em = tx.getEntityManager();
            LogDAO ldao = new LogDAO(em);
            HttpServletRequest  req = (HttpServletRequest) request;

            // 1. Thiết lập mã hóa ký tự UTF-8
            req.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            // 2. Ghi nhận thông tin truy cập nếu người dùng đã đăng nhập
            HttpSession session = req.getSession(false); // false để không tạo session mới
            if (session != null && session.getAttribute("user") != null) {
                // Phương thức getId() lấy id người dun
                User user = (User) session.getAttribute("user");
                String username = user.getId();

                // Lấy URI của request hiện tại
                String uri = req.getRequestURI();

                // Tạo đối tượng Log và lưu vào CSDL
                Log log = new Log(uri, username);
                ldao.create(log);
            }

            // Chuyển request đến filter hoặc servlet/JSP tiếp theo trong chuỗi
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {}
}
