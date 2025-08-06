package controller;


import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig; // Cần import EndpointConfig

import com.fasterxml.jackson.core.JsonProcessingException; // Cần import JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Message; // Đảm bảo đúng package của Message

import java.io.IOException; // Cần import IOException

public class MessageDecoder implements Decoder.Text<Message> {

    // Khai báo ObjectMapper là static final để tái sử dụng và đảm bảo an toàn luồng
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(EndpointConfig config) {
        // Phương thức này được gọi khi decoder được khởi tạo.
        // Trong trường hợp đơn giản này, không cần khởi tạo gì đặc biệt.
    }

    @Override
    public void destroy() {
        // Phương thức này được gọi khi decoder bị hủy.
        // Trong trường hợp này, không có tài nguyên nào cần giải phóng đặc biệt.
    }

    @Override
    public Message decode(String json) throws DecodeException {
        try {
            // Chuyển đổi chuỗi JSON thành đối tượng Message
            return mapper.readValue(json, Message.class);
        } catch (JsonProcessingException e) { // Bắt JsonProcessingException cụ thể hơn
            // Ném DecodeException với thông báo lỗi chi tiết hơn
            throw new DecodeException(json, "Lỗi khi giải mã chuỗi JSON thành đối tượng Message: " + e.getMessage(), e);
        } catch (IOException e) { // Bắt IOException cho các lỗi đọc/ghi khác
            throw new DecodeException(json, "Lỗi I/O khi giải mã JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean willDecode(String json) {
        // Cố gắng kiểm tra xem chuỗi có phải là JSON hợp lệ hay không.
        // Đây là một cách kiểm tra tốt hơn so với chỉ dùng contains().
        // Tuy nhiên, nó có thể hơi tốn kém nếu được gọi quá thường xuyên với chuỗi lớn.
        // Đối với các ứng dụng thực tế, nếu bạn tin tưởng client gửi JSON hợp lệ,
        // bạn có thể chỉ cần kiểm tra json != null && !json.trim().isEmpty();
        try {
            // Cố gắng parse chuỗi thành một JsonNode để kiểm tra tính hợp lệ của JSON
            mapper.readTree(json);
            return true; // Nếu parse thành công, có khả năng là JSON hợp lệ
        } catch (JsonProcessingException e) {
            // Nếu có lỗi khi parse JSON, thì không phải là JSON hợp lệ
            return false;
        } catch (IOException e) {
            // Xử lý các lỗi I/O khác nếu có
            return false;
        }
    }
}
