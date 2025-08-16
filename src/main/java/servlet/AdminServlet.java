package servlet;

import dao.UserDAO;
import dao.UserDAOImpl;
import dao.VideoDAO;
import dao.VideoDAOImpl;
import entity.User;
import entity.Video;
import util.XJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({
        "/admin/video",
        "/admin/user",
        "/admin/reports"
})
public class AdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();
        
        if (uri.contains("/admin/user")) {
            // Load user list for user management page
            try (XJPA tx = new XJPA()) {
                EntityManager em = tx.getEntityManager();
                UserDAO userDAO = new UserDAOImpl(em);
                
                List<User> userList = userDAO.findAll();
                request.setAttribute("userList", userList);
                request.setAttribute("totalUsers", userList.size());
                
                request.getRequestDispatcher("/view/admin/userManagement.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error loading user data: " + e.getMessage());
                request.getRequestDispatcher("/view/admin/userManagement.jsp").forward(request, response);
            }
        } else if (uri.contains("/admin/video")) {
            // Load video list for video management page
            try (XJPA tx = new XJPA()) {
                EntityManager em = tx.getEntityManager();
                VideoDAO videoDAO = new VideoDAOImpl(em);
                
                List<Video> videoList = videoDAO.findAll();
                request.setAttribute("videoList", videoList);
                request.setAttribute("totalVideos", videoList.size());
                
                request.getRequestDispatcher("/view/admin/videosManagement.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error loading video data: " + e.getMessage());
                request.getRequestDispatcher("/view/admin/videosManagement.jsp").forward(request, response);
            }
        } else if (uri.contains("/admin/reports")) {
            // Load reports page
            request.getRequestDispatcher("/view/admin/reports.jsp").forward(request, response);
        } else {
            // For other admin pages, redirect to basic page
            request.getRequestDispatcher("/view/page.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();
        
        if (uri.contains("/admin/user")) {
            handleUserManagement(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleUserManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try (XJPA tx = new XJPA()) {
            EntityManager em = tx.getEntityManager();
            UserDAO userDAO = new UserDAOImpl(em);
            
            switch (action) {
                case "create":
                    createUser(request, userDAO);
                    request.setAttribute("success", "User created successfully!");
                    break;
                case "update":
                    updateUser(request, userDAO);
                    request.setAttribute("success", "User updated successfully!");
                    break;
                case "delete":
                    deleteUser(request, userDAO);
                    request.setAttribute("success", "User deleted successfully!");
                    break;
                default:
                    request.setAttribute("error", "Invalid action");
            }
            
            // Reload user list
            List<User> userList = userDAO.findAll();
            request.setAttribute("userList", userList);
            request.setAttribute("totalUsers", userList.size());
            
        } catch (EntityExistsException e) {
            request.setAttribute("error", "User already exists with this username");
        } catch (EntityNotFoundException e) {
            request.setAttribute("error", "User not found");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/view/admin/userManagement.jsp").forward(request, response);
    }

    private void createUser(HttpServletRequest request, UserDAO userDAO) throws EntityExistsException {
        User user = new User();
        user.setId(request.getParameter("username")); // user.id is the username
        user.setFullName(request.getParameter("fullName"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setAdmin(Boolean.parseBoolean(request.getParameter("admin")));
        
        if (!userDAO.create(user)) {
            throw new RuntimeException("Failed to create user");
        }
    }

    private void updateUser(HttpServletRequest request, UserDAO userDAO) throws EntityNotFoundException {
        String userId = request.getParameter("userId");
        User user = userDAO.findById(userId).orElseThrow(() -> 
            new EntityNotFoundException("User not found: " + userId));
        
        user.setFullName(request.getParameter("fullName"));
        user.setEmail(request.getParameter("email"));
        
        String password = request.getParameter("password");
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        }
        
        user.setAdmin(Boolean.parseBoolean(request.getParameter("admin")));
        
        userDAO.update(user);
    }

    private void deleteUser(HttpServletRequest request, UserDAO userDAO) throws EntityNotFoundException {
        String userId = request.getParameter("userId");
        userDAO.delete(userId);
    }
}
