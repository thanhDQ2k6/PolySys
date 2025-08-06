package controller;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig; // Cần import EndpointConfig

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Message;

public class MessageEncoder implements Encoder.Text<Message> {

    // Khai báo ObjectMapper là static final để tái sử dụng và đảm bảo an toàn luồng
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(EndpointConfig config) {
        // Phương thức này được gọi khi encoder được khởi tạo.
        // Trong trường hợp đơn giản này, không cần khởi tạo gì đặc biệt.
        // Nếu bạn muốn cấu hình ObjectMapper dựa trên EndpointConfig, bạn có thể làm ở đây.
    }

    @Override
    public void destroy() {
        // Phương thức này được gọi khi encoder bị hủy.
        // Trong trường hợp này, không có tài nguyên nào cần giải phóng đặc biệt.
    }

    @Override
    public String encode(Message message) throws EncodeException {
        try {
            // Chuyển đổi đối tượng Message thành chuỗi JSON
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            // Ném EncodeException với thông báo lỗi chi tiết hơn
            throw new EncodeException(message, "Lỗi khi mã hóa đối tượng Message thành JSON: " + e.getMessage(), e);
        }
    }
}
