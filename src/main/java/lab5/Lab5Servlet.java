package lab5;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;
import utils.XJPA;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet({
        "/lab5/login",
        "/counter"
})
public class Lab5Servlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (XJPA tx = new XJPA()) {
            // Bai 1
            EntityManager em = tx.getEntityManager();
            UserDAO udao = new UserDAOImpl(em);
            String uri = request.getRequestURI();

            if (uri.contains("/lab5/login")) {
                String uid = request.getParameter("uid");
                String pwd = request.getParameter("pwd");

                if (uid != null && pwd != null && !uid.isEmpty() && !pwd.isEmpty()) {
                    Optional<User> u = udao.findById(uid);

                    if (u.isPresent()) {
                        if (u.get().getPassword().equals(pwd)) {
                            request.getSession().setAttribute("user", u.get());
                            request.getSession().removeAttribute("error");
                        } else {
                            request.getSession().setAttribute("error", "Wrong Password");
                        }
                    } else {
                        request.getSession().setAttribute("error", "Wrong Username");
                    }
                } else request.getSession().setAttribute("error", "Please insert username and password");

                request.getRequestDispatcher("/lab5/login.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("/lab5/login")) {
            request.getRequestDispatcher("/lab5/login.jsp").forward(request, response);
        } else if (uri.contains("/counter")) {
            request.getRequestDispatcher("/lab5/counter.jsp").forward(request, response);
        }
    }
}
