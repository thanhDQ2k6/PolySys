package utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Collectors;

public class RestIO {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Đọc chuỗi JSON từ client một cách hiệu quả hơn
     */
    public static String readJson(HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("UTF-8");
        // Sử dụng BufferedReader để đọc toàn bộ stream một cách hiệu quả
        try (BufferedReader reader = req.getReader()) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    /**
     * Gửi chuỗi JSON về client, sử dụng try-with-resources
     */
    public static void writeJson(HttpServletResponse resp, String json) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = resp.getWriter()) {
            writer.print(json);
        }
    }

    /**
     * Đọc JSON và chuyển đổi sang Java Object (ngắn gọn hơn)
     */
    public static <T> T readObject(HttpServletRequest req, Class<T> clazz) throws IOException {
        String json = readJson(req);
        return mapper.readValue(json, clazz);
    }

    /**
     * Chuyển đổi Java Object sang JSON và gửi về client (ngắn gọn hơn)
     */
    public static void writeObject(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        mapper.writeValue(resp.getWriter(), data);
    }

    /**
     * Gửi đối tượng rỗng về client (ngắn gọn hơn)
     */
    public static void writeEmptyObject(HttpServletResponse resp) throws IOException {
        writeObject(resp, Map.of());
    }
}
