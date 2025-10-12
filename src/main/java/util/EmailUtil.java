package util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {
    // Replace with your Gmail and app password
    private static final String FROM_EMAIL = "thanhdqts00628@fpt.edu.vn"; // <-- Put your email here
    private static final String APP_PASSWORD = "cgvk rjbp avuf yrwz"; // <-- Put your app password here

    /**
     * Gửi email với nội dung và tiêu đề cho người nhận
     * @param toEmail Email người nhận
     * @param subject Tiêu đề
     * @param content Nội dung
     * @throws MessagingException Nếu gửi thất bại
     */
    public static void sendEmail(String toEmail, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }

    /**
     * Tạo nội dung email chia sẻ video YouTube
     * @param recipientEmail Email người nhận
     * @param videoTitle Tiêu đề video
     * @param youtubeLink Link YouTube
     */
    public static void constructAndSendShareEmail(String recipientEmail, String videoTitle, String youtubeLink) throws MessagingException {
        // Tiêu đề email cố định
        String subject = "[Share] Check out this video: " + videoTitle;
        // Nội dung email, có thể chỉnh sửa cho đẹp hơn
        String content = "Hi!\n\nA friend shared a video with you:\n\n" +
                videoTitle + "\n\n" +
                "Watch now: " + youtubeLink + "\n\nEnjoy!";
        // Gửi email
        sendEmail(recipientEmail, subject, content);
    }
}
