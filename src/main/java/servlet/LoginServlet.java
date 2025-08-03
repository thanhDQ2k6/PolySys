package servlet;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;
import filter.AuthFilter;
import utils.XJPA;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/view/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (XJPA tx = new XJPA()) {
            EntityManager em = tx.getEntityManager();

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            UserDAO udao = new UserDAOImpl(em);

            if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
                Optional<User> u = udao.findById(username);

                if (u.isPresent()) {
                    if (u.get().getPassword().equals(password)) {
                        request.getSession().setAttribute("user", u.get());
                        request.setAttribute("message", "Logged in successfully.");

                        String securedURI = (String) request.getSession().getAttribute(AuthFilter.SecuredURI);
                        if (securedURI != null) {
                            response.sendRedirect(securedURI);
                            return;
                        }
                    } else {
                        request.getSession().setAttribute("error", "Wrong Password");
                    }
                } else {
                    request.getSession().setAttribute("error", "Wrong Username");
                }
            } else request.getSession().setAttribute("error", "Please insert username and password");

            request.getRequestDispatcher("/view/login.jsp").forward(request, response);
        }
    }
}
