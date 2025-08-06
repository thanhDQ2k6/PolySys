package websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/chat") // URL mà WebSocket client sẽ kết nối tới
public class ChatServerEndpoint {

    // Logger để ghi log các sự kiện và lỗi
    private static final Logger LOGGER = Logger.getLogger(ChatServerEndpoint.class.getName());

    // Duy trì danh sách session của các client đang kết nối.
    // ConcurrentHashMap được sử dụng để đảm bảo an toàn luồng (thread-safe)
    // khi nhiều client kết nối/ngắt kết nối cùng lúc.
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    /**
     * Gửi một thông điệp đến tất cả các client đang kết nối.
     *
     * @param message Chuỗi thông điệp cần gửi.
     */
    private void broadcast(String message) {
        LOGGER.log(Level.INFO, "Broadcasting message: {0}", message);
        sessions.forEach((id, session) -> {
            try {
                // Kiểm tra xem session có đang mở không trước khi gửi
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Failed to send message to session {0}: {1}", new Object[]{id, e.getMessage()});
                // Nếu không gửi được, có thể đóng session đó
                try {
                    session.close();
                } catch (IOException closeException) {
                    LOGGER.log(Level.SEVERE, "Error closing session after send failure: {0}", closeException.getMessage());
                }
            }
        });
    }

    /**
     * Phương thức được gọi khi một client mới kết nối đến WebSocket.
     *
     * @param session Đối tượng Session đại diện cho kết nối mới.
     */
    @OnOpen
    public void onOpen(Session session) {
        sessions.put(session.getId(), session); // Thêm session mới vào danh sách
        String joinMessage = "Someone joined the chat! (ID: " + session.getId() + ")";
        LOGGER.log(Level.INFO, joinMessage);
        broadcast(joinMessage); // Gửi thông báo cho tất cả mọi người
    }

    /**
     * Phương thức được gọi khi server nhận được một tin nhắn từ client.
     *
     * @param message Chuỗi tin nhắn nhận được.
     * @param session Đối tượng Session của client gửi tin nhắn.
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        String formattedMessage = session.getId() + ": " + message; // Định dạng tin nhắn (ví dụ: ID_người_gửi: nội_dung)
        LOGGER.log(Level.INFO, "Received message from {0}: {1}", new Object[]{session.getId(), message});
        broadcast(formattedMessage); // Chuyển tiếp tin nhắn đến tất cả mọi người
    }

    /**
     * Phương thức được gọi khi một client ngắt kết nối khỏi WebSocket.
     *
     * @param session Đối tượng Session của client vừa ngắt kết nối.
     */
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId()); // Xóa session khỏi danh sách
        String leaveMessage = "Someone left the chat! (ID: " + session.getId() + ")";
        LOGGER.log(Level.INFO, leaveMessage);
        broadcast(leaveMessage); // Gửi thông báo cho tất cả mọi người
    }

    /**
     * Phương thức được gọi khi có lỗi xảy ra trong quá trình giao tiếp WebSocket.
     *
     * @param session   Đối tượng Session nơi lỗi xảy ra.
     * @param throwable Đối tượng Throwable chứa thông tin lỗi.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.log(Level.SEVERE, "Error in WebSocket session {0}: {1}", new Object[]{session.getId(), throwable.getMessage()});
        // Có thể đóng session nếu lỗi nghiêm trọng
        try {
            session.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error closing session after onError: {0}", e.getMessage());
        }
    }
}
