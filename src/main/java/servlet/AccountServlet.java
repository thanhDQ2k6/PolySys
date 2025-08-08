package servlet;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;
import util.XJPA;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({
        "/account/signup",
        "/account/changepwd",
        "/account/editprofile"
})
public class AccountServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy đường dẫn URI của yêu cầu
        String path = request.getRequestURI();

        if (path.contains("/account/signup")) {
            // Chuyển tiếp đến trang đăng ký
            request.getRequestDispatcher("/view/account/register.jsp").forward(request, response);
        } else if (path.contains("/account/changepwd")) {
            // Chuyển tiếp đến trang đổi mật khẩu
            request.getRequestDispatcher("/view/account/changepwd.jsp").forward(request, response);
        } else if (path.contains("/account/editprofile")) {
            // Chuyển tiếp đến trang chỉnh sửa hồ sơ
            request.getRequestDispatcher("/view/account/editProfile.jsp").forward(request, response);
        } else {
            // Xử lý trường hợp không xác định
            request.getRequestDispatcher("/view/page.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (XJPA jpa = new XJPA()) {
            EntityManager em = jpa.getEntityManager();
            String path = request.getRequestURI();
            UserDAO userDAO = new UserDAOImpl(em);

            // Sử dụng if-else if để đảm bảo chỉ một khối lệnh được thực thi
            if (path.contains("/account/signup")) {
                // Lấy dữ liệu từ form
                String fullname = request.getParameter("fullname");
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                // Tạo đối tượng User
                User user = new User(fullname, username, email, password);

                // Gọi phương thức tạo user
                boolean isAdded = userDAO.create(user);

                if (isAdded) {
                    // Thêm thành công: Chuyển hướng đến trang thành công và thoát
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                } else {
                    // Thêm thất bại: Đặt lỗi và forward về trang đăng ký
                    request.setAttribute("error", "Failed to save changes. Please try again.");
                    request.getRequestDispatcher("/view/account/register.jsp").forward(request, response);
                    return;
                }

            } else if (path.contains("/account/editprofile")) {
                // Lấy đối tượng user cũ từ session
                User u = (User) request.getSession().getAttribute("user");

                // Tạo đối tượng User mới từ dữ liệu form
                String fullname = request.getParameter("fullname");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                u.setFullName(fullname);
                u.setEmail(email);
                u.setPassword(password);

                // Gọi phương thức cập nhật
                User updUser = userDAO.update(u);
                // Cập nhật lại session
                request.getSession().setAttribute("user", updUser);

                if (updUser != null) {
                    // Sửa thành công: Chuyển hướng và thoát
                    response.sendRedirect(request.getContextPath() + "/video/list");
                    return;
                } else {
                    // Sửa thất bại: Đặt lỗi và forward về trang chỉnh sửa
                    request.setAttribute("error", "Failed to save changes. Please try again.");
                    request.getRequestDispatcher("/view/account/editProfile.jsp").forward(request, response);
                    return;
                }

            } else if (path.contains("/account/changepwd")) {
                // Lấy đối tượng user cũ từ session
                User u = (User) request.getSession().getAttribute("user");

                String pwd = request.getParameter("pwd");
                String new1 = request.getParameter("new1");
                String new2 = request.getParameter("new2");

                if (pwd.equals(u.getPassword())) {
                    if (new1.equals(new2)) {
                        u.setPassword(new1);

                        User updUser = userDAO.update(u);
                        request.getSession().setAttribute("user", updUser);

                        if (updUser != null) {
                            // Sửa thành công: Chuyển hướng và thoát
                            response.sendRedirect(request.getContextPath() + "/video/list");
                            return;
                        } else {
                            // Sửa thất bại: Đặt lỗi và forward
                            request.setAttribute("error", "Failed to save changes. Please try again.");
                            request.getRequestDispatcher("/view/account/changepwd.jsp").forward(request, response);
                            return;
                        }
                    } else {
                        request.setAttribute("error", "New Password and Confirm do not match. Please try again.");
                        request.getRequestDispatcher("/view/account/changepwd.jsp").forward(request, response);
                        return;
                    }
                } else {
                    request.setAttribute("error", "Password do not match. Please try again.");
                    request.getRequestDispatcher("/view/account/changepwd.jsp").forward(request, response);
                    return;
                }
            }

            // Nếu không có path nào khớp, trả về lỗi 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            // Xử lý lỗi toàn cục
            e.printStackTrace();
            // Forward về trang lỗi chung nếu có exception
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/view/error.jsp").forward(request, response);
        }
    }
}
