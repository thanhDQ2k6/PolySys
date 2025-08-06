package websocket;


import controller.MessageDecoder;
import controller.MessageEncoder;
import model.Message;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.EncodeException; // Cần thiết cho sendObject()
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

// Khai báo encoders và decoders trong annotation @ServerEndpoint
// Đảm bảo MessageEncoder và MessageDecoder nằm trong cùng package hoặc được import đúng
@ServerEndpoint(
        value = "/json/chat/{username}", // URL với PathParam cho username
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class}
)
public class JsonChatServerEndpoint {

    private static final Logger LOGGER = Logger.getLogger(JsonChatServerEndpoint.class.getName());

    // Duy trì danh sách session của các client đang kết nối.
    // Sử dụng USERNAME làm khóa để theo dõi người dùng duy nhất trong phòng chat.
    // Nếu bạn muốn nhiều session cho cùng một username, hãy dùng Map<String, Set<Session>> hoặc Map<String, List<Session>>
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    // Key để lưu trữ username trong Session.getUserProperties()
    // Đảm bảo KEY này khớp với cách bạn lưu trữ username trong @OnOpen
    private static final String USERNAME_SESSION_KEY = "username";

    /**
     * Gửi một đối tượng Message đến tất cả các client đang kết nối.
     * WebSocket runtime sẽ tự động sử dụng MessageEncoder để chuyển đổi đối tượng Message thành JSON.
     *
     * @param message Đối tượng Message cần gửi.
     */
    private void broadcast(Message message) {
        LOGGER.log(Level.INFO, "Broadcasting Message object: {0}", message.getText());
        // Duyệt qua tất cả các session đang hoạt động
        sessions.forEach((usernameKey, session) -> { // usernameKey ở đây là username của người nhận
            try {
                // Kiểm tra xem session có đang mở không trước khi gửi
                if (session.isOpen()) {
                    // Sử dụng sendObject() để gửi đối tượng Message.
                    // MessageEncoder sẽ tự động mã hóa nó thành JSON.
                    session.getBasicRemote().sendObject(message);
                }
            } catch (IOException | EncodeException e) { // Bắt cả IOException và EncodeException
                // Ghi log lỗi thay vì chỉ printStackTrace()
                LOGGER.log(Level.WARNING, "Failed to send message to session {0} (User: {1}): {2}",
                        new Object[]{session.getId(), usernameKey, e.getMessage(), e}); // Thêm e vào cuối mảng Object[] để in stack trace
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
     * @param username Tên người dùng được lấy từ PathParam trong URL.
     * @param session Đối tượng Session đại diện cho kết nối mới.
     */
    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        // Kiểm tra xem username đã tồn tại trong phòng chat chưa
        if (sessions.containsKey(username)) {
            LOGGER.log(Level.WARNING, "Username {0} already exists. Rejecting new connection.", username);
            try {
                // Gửi một tin nhắn lỗi về client trước khi đóng session
                Message errorMessage = new Message("Username '" + username + "' is already taken. Please choose another one.", -1, "System", 0); // type -1 cho lỗi
                session.getBasicRemote().sendObject(errorMessage);
                session.close(); // Đóng session nếu username đã tồn tại
            } catch (IOException | EncodeException e) {
                LOGGER.log(Level.SEVERE, "Error sending error message or closing session for duplicate username: {0}", e.getMessage());
            }
            // Không throw RuntimeException để tránh làm crash container, hãy đóng session một cách graceful
            return; // Dừng xử lý OnOpen cho kết nối này
        } else {
            // Lưu username vào UserProperties của session để có thể truy cập sau này
            session.getUserProperties().put(USERNAME_SESSION_KEY, username);
            sessions.put(username, session); // Thêm session mới vào danh sách với username làm khóa

            int currentClientCount = sessions.size();
            // Tạo đối tượng Message cho sự kiện tham gia
            // text: "joined the chat"
            // type: 0 (vào)
            // sender: username của người vừa tham gia
            // count: số lượng client hiện tại
            Message joinMessage = new Message(username + " joined the chat!", 0, username, currentClientCount);
            LOGGER.log(Level.INFO, "Client {0} ({1}) joined. Total clients: {2}", new Object[]{session.getId(), username, currentClientCount});
            this.broadcast(joinMessage); // Gửi thông báo cho tất cả mọi người
        }
    }

    /**
     * Phương thức được gọi khi server nhận được một tin nhắn từ client.
     * Tin nhắn được nhận dưới dạng đối tượng Message nhờ MessageDecoder.
     *
     * @param receivedMessage Đối tượng Message nhận được từ client.
     * @param session Đối tượng Session của client gửi tin nhắn.
     */
    @OnMessage
    public void onMessage(Message receivedMessage, Session session) {
        String senderUsername = (String) session.getUserProperties().get(USERNAME_SESSION_KEY);

        // Tạo đối tượng Message để broadcast
        // text: nội dung tin nhắn từ client
        // type: 2 (lời thoại)
        // sender: username của người gửi
        // count: 0 (hoặc null nếu kiểu là Integer và bạn muốn bỏ qua)
        // Đảm bảo rằng tin nhắn gửi đi có sender là username thực tế
        Message chatMessage = new Message(receivedMessage.getText(), 2, senderUsername, 0);
        LOGGER.log(Level.INFO, "Received message from {0} ({1}): {2}", new Object[]{session.getId(), senderUsername, receivedMessage.getText()});
        this.broadcast(chatMessage); // Chuyển tiếp tin nhắn đến tất cả mọi người
    }

    /**
     * Phương thức được gọi khi một client ngắt kết nối khỏi WebSocket.
     *
     * @param session Đối tượng Session của client vừa ngắt kết nối.
     */
    @OnClose
    public void onClose(Session session) {
        String username = (String) session.getUserProperties().get(USERNAME_SESSION_KEY); // Lấy username trước khi session bị hủy hoàn toàn

        if (username != null) { // Đảm bảo username tồn tại
            sessions.remove(username); // Xóa session khỏi danh sách bằng username
            int currentClientCount = sessions.size();

            // Tạo đối tượng Message cho sự kiện rời khỏi
            // text: "left the chat"
            // type: 1 (ra)
            // sender: username của người vừa rời đi
            // count: số lượng client hiện tại
            Message leaveMessage = new Message(username + " left the chat!", 1, username, currentClientCount);
            LOGGER.log(Level.INFO, "Client {0} ({1}) left. Total clients: {2}", new Object[]{session.getId(), username, currentClientCount});
            this.broadcast(leaveMessage); // Gửi thông báo cho tất cả mọi người
        } else {
            LOGGER.log(Level.WARNING, "Session {0} closed, but username not found in properties.", session.getId());
        }
    }

    /**
     * Phương thức được gọi khi có lỗi xảy ra trong quá trình giao tiếp WebSocket.
     *
     * @param session Đối tượng Session nơi lỗi xảy ra.
     * @param throwable Đối tượng Throwable chứa thông tin lỗi.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        String username = (String) session.getUserProperties().get(USERNAME_SESSION_KEY);
        // Ghi log lỗi đầy đủ với stack trace
        LOGGER.log(Level.SEVERE, "Error in WebSocket session {0} (User: {1}): {2}",
                new Object[]{session.getId(), username, throwable.getMessage(), throwable});

        // Tomcat thường tự động đóng session sau onError.
        // Việc gọi session.close() ở đây có thể không cần thiết hoặc gây lỗi nếu session đã đóng.
        // Tuy nhiên, nếu bạn muốn đảm bảo, có thể bọc trong try-catch.
        try {
            if (session.isOpen()) { // Chỉ đóng nếu session còn mở
                session.close();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error closing session after onError: {0}", e.getMessage());
        }
    }
}

