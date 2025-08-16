package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.FavoriteDAO;
import dao.FavoriteDAOImpl;
import dao.VideoDAO;
import dao.VideoDAOImpl;
import entity.Favorite;
import entity.Video;
import entity.User;
import util.XJPA;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Optional;

@WebServlet("/video/*")
public class VideoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        // Nếu là API lấy danh sách video (trả về JSON)
        if ("/api/list".equals(path)) {
            // 1. Tạo EntityManager để làm việc với JPA (bắt buộc phải đóng sau khi dùng)
            try (XJPA jpa = new XJPA()) {
                EntityManager em = jpa.getEntityManager();
                // 2. Tạo DAO với EntityManager
                VideoDAO videoDAO = new VideoDAOImpl(em);
                // 3. Lấy danh sách video từ database
                List<Video> videos = videoDAO.findAll();
                // 4. Chuyển danh sách video thành JSON bằng Jackson
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(videos);
                // 5. Trả về JSON cho client
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            }
            return; // Kết thúc luôn, không forward JSP nữa
        }
        // Nếu là API lấy chi tiết video (trả về JSON)
        if ("/api/detail".equals(path)) {
            String id = request.getParameter("id");
            if (id == null || id.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu id video");
                return;
            }
            try (XJPA jpa = new XJPA()) {
                EntityManager em = jpa.getEntityManager();
                VideoDAO videoDAO = new VideoDAOImpl(em);
                Optional<Video> video = videoDAO.findById(id);
                if (!video.isPresent()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy video");
                    return;
                }
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(video.get()); // Unwrap Optional before serializing
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            }
            return;
        }
        if ("/api/liked".equals(path)) {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("[]");
                return;
            }
            try (XJPA jpa = new XJPA()) {
                EntityManager em = jpa.getEntityManager();
                FavoriteDAO favoriteDAO = new FavoriteDAOImpl(em);
                List<Favorite> favorites = favoriteDAO.findByUserId(user.getId());
                List<String> likedIds = new ArrayList<>();
                for (Favorite fav : favorites) {
                    likedIds.add(fav.getVideo().getId());
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(likedIds));
            }
            return;
        }
        if (path == null || path.equals("/") || path.equals("/list")) {
            // Danh sách video (API JSON hoặc forward đến trang home)
            // Tạm thời forward về home.jsp
            request.getRequestDispatcher("/view/video/home.jsp").forward(request, response);
        } else if (path.equals("/detail")) {
            // --- View count logic ---
            String videoId = request.getParameter("id");
            if (videoId != null && !videoId.isEmpty()) {
                // Lấy danh sách đã xem từ session
                @SuppressWarnings("unchecked")
                Set<String> viewedVideos = (Set<String>) request.getSession().getAttribute("viewedVideos");
                if (viewedVideos == null) {
                    viewedVideos = new HashSet<>();
                }
                if (!viewedVideos.contains(videoId)) {
                    // Chưa xem trong session này, tăng view
                    try (XJPA jpa = new XJPA()) {
                        EntityManager em = jpa.getEntityManager();
                        VideoDAO videoDAO = new VideoDAOImpl(em);
                        videoDAO.increaseView(videoId); // Tăng view
                    }
                    viewedVideos.add(videoId);
                    request.getSession().setAttribute("viewedVideos", viewedVideos);
                }
            }
            // Forward về detail.jsp như cũ
            request.getRequestDispatcher("/view/video/detail.jsp").forward(request, response);
        } else if (path.equals("/favorite")) {
            // Danh sách video đã like (favorite)
            request.getRequestDispatcher("/view/video/favorite.jsp").forward(request, response);
        } else if (path.equals("/share")) {
            // Trang share video
            request.getRequestDispatcher("/view/video/share.jsp").forward(request, response);
        } else {
            // 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if ("/api/like".equals(path)) {
            // Lấy user từ session (giả sử đã đăng nhập)
            entity.User user = (entity.User) request.getSession().getAttribute("user");
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"Chưa đăng nhập\"}");
                return;
            }
            String videoId = request.getParameter("videoId");
            if (videoId == null || videoId.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Thiếu videoId\"}");
                return;
            }
            try (XJPA jpa = new XJPA()) {
                EntityManager em = jpa.getEntityManager();
                FavoriteDAO favoriteDAO = new FavoriteDAOImpl(em);
                VideoDAO videoDAO = new VideoDAOImpl(em);
                Optional<Favorite> favoriteOpt = favoriteDAO.findByUserAndVideo(user.getId(), videoId);
                boolean liked = false;
                if (favoriteOpt.isPresent()) {
                    // Đã like, thì unlike
                    favoriteDAO.delete(user.getId(), videoId);
                    liked = false;
                } else {
                    // Chưa like, thì like
                    entity.Favorite favorite = new entity.Favorite();
                    favorite.setUser(user);
                    favorite.setVideo(videoDAO.findById(videoId).orElse(null));
                    favorite.setLikeDate(java.time.LocalDate.now());
                    favoriteDAO.create(favorite);
                    liked = true;
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"liked\":" + liked + "}");
            }
        }
        if ("/api/share".equals(path)) {
            // Lấy thông tin từ request
            String recipientEmail = request.getParameter("recipientEmail");
            String videoTitle = request.getParameter("videoTitle");
            String youtubeLink = request.getParameter("youtubeLink");
            // Kiểm tra đầu vào
            if (recipientEmail == null || recipientEmail.isEmpty() ||
                videoTitle == null || videoTitle.isEmpty() ||
                youtubeLink == null || youtubeLink.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Thiếu thông tin chia sẻ\"}");
                return;
            }
            try {
                util.EmailUtil.constructAndSendShareEmail(recipientEmail, videoTitle, youtubeLink);
                response.setContentType("application/json");
                response.getWriter().write("{\"success\":true}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Gửi email thất bại: " + e.getMessage() + "\"}");
            }
            return;
        }
    }
}
