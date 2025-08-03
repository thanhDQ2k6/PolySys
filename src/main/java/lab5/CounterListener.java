package lab5;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebListener
public class CounterListener implements ServletContextListener, HttpSessionListener {

    private static final String count = "visitors";

    /**
     * Phương thức này được gọi khi ServletContext được khởi tạo.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        long visitorsCount = 0;

        // Chia sẻ số đếm qua application scope
        context.setAttribute(count, visitorsCount);
        System.out.println("ServletContext created. Guest visited: " + visitorsCount);
    }

    /**
     * Phương thức này được gọi khi ServletContext bị hủy.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
    }

    /**
     * Phương thức này được gọi khi một phiên (session) mới được tạo.
     * Nó tăng số đếm lên 1 và cập nhật vào application scope.
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        // Đồng bộ hóa việc truy cập vào biến shared để tránh race condition
        synchronized (this) {
            Long visitorsCount = (Long) context.getAttribute(count);
            if (visitorsCount == null) {
                visitorsCount = 0L; // Khởi tạo nếu chưa có
            }
            visitorsCount++;
            context.setAttribute(count, visitorsCount);
            System.out.println("New session created. Total guest visited: " + visitorsCount);
        }
    }

    /**
     * Phương thức này được gọi khi một phiên (session) bị hủy.
     * (Không cần làm gì trong trường hợp này, vì yêu cầu chỉ là đếm lượt truy cập mới).
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Có thể để trống hoặc thêm logic khác nếu cần
    }
}
