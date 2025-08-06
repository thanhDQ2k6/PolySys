package lab7;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/lab7/bai1")
public class Bai1Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Thiết lập Content-Type của response
        // Điều này báo cho client biết rằng nội dung trả về là JSON và sử dụng mã hóa UTF-8
        response.setContentType("application/json;charset=UTF-8");

        // 2. Thiết lập mã hóa ký tự cho response
        response.setCharacterEncoding("UTF-8");

        // 3. Tạo chuỗi JSON
        String json = "{"
                + "\"manv\":\"TeoNV\","
                + "\"hoTen\":\"Nguyễn Văn Tèo\","
                + "\"gioiTinh\":true,"
                + "\"luong\":950.5"
                + "}";

        // 4. Lấy PrintWriter và ghi chuỗi JSON vào response
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
