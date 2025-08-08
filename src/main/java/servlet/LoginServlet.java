package servlet;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;
import filter.AuthFilter;
import util.XJPA;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/account/login.jsp").forward(request, response);
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

                        String securedURI = (String) request.getSession().getAttribute(AuthFilter.SecuredURI);
                        if (securedURI != null) {
                            response.sendRedirect(securedURI);
                            request.getSession().removeAttribute(AuthFilter.SecuredURI);
                        } else {
                            response.sendRedirect(request.getContextPath() + "/video/list");
                        }
                    } else {
                        request.setAttribute("error", "Wrong Password");
                        request.getRequestDispatcher("/view/account/login.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("error", "Wrong Username");
                    request.getRequestDispatcher("/view/account/login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Please insert username and password");
                request.getRequestDispatcher("/view/account/login.jsp").forward(request, response);
            }
        }
    }
}
