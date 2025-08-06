const messageInput = document.getElementById('messageInput');
const sendButton = document.getElementById('sendButton');
const chatMessages = document.getElementById('chatMessages');
const clientCountDisplay = document.getElementById('client-count'); // Thêm element để hiển thị số lượng client

let username = null; // Biến lưu trữ username của người dùng hiện tại
let webSocket = null; // Biến để giữ kết nối WebSocket

// Hàm để thêm tin nhắn vào giao diện
// Cập nhật để nhận đối tượng Message và xử lý theo type
function addMessageToUI(msg) {
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message');

    let senderText = '';
    let messageContent = '';
    let isMyMessage = false;

    switch (msg.type) {
        case 0: // Joined
            senderText = 'System';
            messageContent = msg.text; // Text đã có sẵn "username joined the chat!"
            messageDiv.classList.add('system-message'); // Có thể thêm class riêng cho tin nhắn hệ thống
            break;
        case 1: // Left
            senderText = 'System';
            messageContent = msg.text; // Text đã có sẵn "username left the chat!"
            messageDiv.classList.add('system-message');
            break;
        case 2: // Chat message
            senderText = msg.sender;
            messageContent = msg.text;
            if (msg.sender === username) { // Kiểm tra nếu là tin nhắn của mình
                isMyMessage = true;
                messageDiv.classList.add('my-message');
            }
            break;
        default: // Unknown or Error type
            senderText = 'System (Error)';
            messageContent = msg.text;
            messageDiv.classList.add('error-message'); // Thêm class riêng cho lỗi
            break;
    }

    const usernameStrong = document.createElement('strong');
    usernameStrong.textContent = senderText;

    const messageTextNode = document.createTextNode(messageContent);

    messageDiv.appendChild(usernameStrong);
    messageDiv.appendChild(messageTextNode);
    chatMessages.appendChild(messageDiv);

    // Cuộn xuống cuối để hiển thị tin nhắn mới nhất
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

// Hàm khởi tạo kết nối WebSocket và xử lý các sự kiện
function initWebSocket() {
    // Yêu cầu người dùng nhập username cho đến khi hợp lệ
    while (username === null || username.trim() === '') {
        username = prompt("Vui lòng nhập tên người dùng của bạn:");
        if (username !== null) {
            username = username.trim();
        }
    }

    // Lấy tên miền và cổng hiện tại
    const host = window.location.host;
    // Lấy context path của ứng dụng (ví dụ: /PolySys_war)
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));

    // URL của WebSocket Server Endpoint với username
    const wsUrl = `ws://${host}${contextPath}/json/chat/${username}`;
    console.log("Attempting to connect to WebSocket URL:", wsUrl);

    webSocket = new WebSocket(wsUrl);

    // Xử lý sự kiện khi kết nối được mở
    webSocket.onopen = function(event) {
        console.log("WebSocket connection opened:", event);
        // Tin nhắn "joined the chat" sẽ được server gửi lại, không cần hiển thị ở đây
    };

    // Xử lý sự kiện khi nhận được tin nhắn từ server
    webSocket.onmessage = function(event) {
        console.log("Received message from server:", event.data);
        try {
            // Parse chuỗi JSON thành đối tượng Message
            const msg = JSON.parse(event.data);

            // Hiển thị tin nhắn lên giao diện
            addMessageToUI(msg);

            // Cập nhật số lượng client nếu tin nhắn có type là 0 (joined) hoặc 1 (left)
            if (msg.type === 0 || msg.type === 1) {
                if (clientCountDisplay) { // Kiểm tra xem element có tồn tại không
                    clientCountDisplay.textContent = `Chatters: ${msg.count}`;
                }
            }

        } catch (e) {
            console.error("Error parsing message from server:", e);
            addMessageToUI({ text: "Error receiving message from server.", type: -1, sender: "System", count: 0 });
        }
    };

    // Xử lý sự kiện khi có lỗi xảy ra
    webSocket.onerror = function(event) {
        console.error("WebSocket error:", event);
        alert('Đã xảy ra lỗi kết nối WebSocket. Vui lòng kiểm tra console.'); // Blocking alert
        // Có thể thêm logic để thử kết nối lại hoặc hiển thị thông báo thân thiện hơn
    };

    // Xử lý sự kiện khi kết nối bị đóng
    webSocket.onclose = function(event) {
        console.log("WebSocket connection closed:", event);
        // event.reason chứa lý do đóng kết nối (nếu có)
        alert(event.reason || 'Kết nối đã đóng. Tạm biệt!'); // Blocking alert
        // Có thể thêm logic để hiển thị nút "Kết nối lại"
    };
}

// Hàm gửi tin nhắn qua WebSocket
function sendMessage() {
    const messageText = messageInput.value.trim();
    if (messageText === '') {
        return; // Không gửi tin nhắn rỗng
    }

    // Kiểm tra trạng thái WebSocket trước khi gửi
    if (webSocket && webSocket.readyState === WebSocket.OPEN) {
        // Tạo đối tượng Message theo cấu trúc đã định nghĩa
        const msg = {
            text: messageText,
            type: 2, // 2 là loại tin nhắn lời thoại
            sender: username, // Gửi username của người gửi
            count: 0 // Count là null/0 cho tin nhắn lời thoại
        };

        // Chuyển đổi đối tượng Message thành chuỗi JSON và gửi đi
        webSocket.send(JSON.stringify(msg));

        // Xóa nội dung input sau khi gửi
        messageInput.value = '';

        // Tin nhắn của mình sẽ được server broadcast lại,
        // nên không cần addMessageToUI ở đây để tránh trùng lặp
        // addMessageToUI({ text: messageText, type: 2, sender: username });
    } else {
        alert('Kết nối WebSocket chưa sẵn sàng hoặc đã đóng. Vui lòng thử lại.');
        console.warn('Attempted to send message while WebSocket not open. Current state:', webSocket ? webSocket.readyState : 'null');
    }
}

// Gắn sự kiện cho nút Gửi
sendButton.addEventListener('click', sendMessage);

// Gắn sự kiện cho ô input (nhấn Enter)
messageInput.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // Ngăn hành vi mặc định của Enter (ví dụ: xuống dòng trong textarea)
        sendMessage();
    }
});

// Khởi tạo WebSocket khi DOM đã tải xong
document.addEventListener('DOMContentLoaded', initWebSocket);