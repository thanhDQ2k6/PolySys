package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/servlet/bai2")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class Bai2Servlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String json = null;

        try {
            // Lấy Part (phần dữ liệu) của file upload từ request
            Part filePart = request.getPart("file"); // "file" là tên thuộc tính 'name' trong input type="file"

            // Lấy thông tin file
            String fileName = filePart.getSubmittedFileName();
            long fileSize = filePart.getSize();
            String fileType = filePart.getContentType();

            // Tạo đường dẫn lưu file trên server
            String uploadPath = "C:" + File.separator + "uploads"; // Đường dẫn đến thư mục "uploads" trên ổ C
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Tạo tên file duy nhất để tránh trùng lặp
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            filePart.write(uploadPath + File.separator + uniqueFileName);

            // Tạo chuỗi JSON với thông tin của file
            json = String.format("{\"name\":\"%s\",\"type\":\"%s\",\"size\":%d}",
                    fileName, fileType, fileSize);
        } catch (Exception e) {
            e.printStackTrace();
            // Trường hợp có lỗi, gửi JSON thông báo lỗi
            json = "{\"error\":\"" + e.getMessage() + "\"}";
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        out.print(json);
        out.flush();
    }
}
