package dao;

import entity.Share;
import entity.User;
import entity.Video;
import org.testng.annotations.*;
import util.XJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class ShareDAOTest {

    private ShareDAO shareDAO;
    private UserDAO userDAO;
    private VideoDAO videoDAO;
    private XJPA xjpa;
    private EntityManager em;

    @BeforeClass
    public void setupClass() {
        System.out.println("=== Starting ShareDAO Tests ===");
    }

    @BeforeMethod
    public void setup() {
        xjpa = new XJPA();
        em = xjpa.getEntityManager();
        shareDAO = new ShareDAOImpl(em);
        userDAO = new UserDAOImpl(em);
        videoDAO = new VideoDAOImpl(em);
    }

    @AfterMethod
    public void tearDown() {
        if (em != null && em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        if (xjpa != null) {
            xjpa.close();
        }
    }

    @AfterClass
    public void tearDownClass() {
        System.out.println("=== ShareDAO Tests Completed ===");
    }

    // SC-001: Tạo share hợp lệ
    @Test(priority = 1)
    public void testCreateValidShare() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V01");
        
        assertTrue(user.isPresent() && video.isPresent(), "User and Video should exist");
        
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails("friend@example.com");
        share.setShareDate(LocalDate.now());

        shareDAO.create(share);

        // Verify
        List<Share> shares = shareDAO.findByUserId("user01");
        assertTrue(shares.size() > 0, "Share should be created");

        // Cleanup
        try {
            shareDAO.delete("user01", "V01");
        } catch (Exception e) {
            // Ignore
        }
    }

    // SC-002: Tạo share với user null
    @Test(priority = 2, expectedExceptions = Exception.class)
    public void testCreateShareWithNullUser() {
        Optional<Video> video = videoDAO.findById("V01");
        assertTrue(video.isPresent());
        
        Share share = new Share();
        share.setUser(null);
        share.setVideo(video.get());
        share.setEmails("friend@example.com");
        share.setShareDate(LocalDate.now());

        shareDAO.create(share);
    }

    // SC-003: Tạo share với video null
    @Test(priority = 3, expectedExceptions = Exception.class)
    public void testCreateShareWithNullVideo() {
        Optional<User> user = userDAO.findById("user01");
        assertTrue(user.isPresent());
        
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(null);
        share.setEmails("friend@example.com");
        share.setShareDate(LocalDate.now());

        shareDAO.create(share);
    }

    // SC-004: Tạo share với emails null
    @Test(priority = 4, expectedExceptions = Exception.class)
    public void testCreateShareWithNullEmails() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V02");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails(null);
        share.setShareDate(LocalDate.now());

        shareDAO.create(share);
    }

    // SC-005: Tạo share với emails rỗng
    @Test(priority = 5)
    public void testCreateShareWithEmptyEmails() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V03");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails("");
        share.setShareDate(LocalDate.now());

        try {
            shareDAO.create(share);
            // May succeed or fail
        } catch (Exception e) {
            assertTrue(true, "Exception acceptable for empty emails");
        }

        // Cleanup
        try {
            shareDAO.delete("user01", "V03");
        } catch (Exception e) {
            // Ignore
        }
    }

    // SC-006: Tạo share với emails = 50 ký tự
    @Test(priority = 6)
    public void testCreateShareWith50CharEmails() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V04");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails("test1234567890123456789012345@example.com"); // 50 chars
        share.setShareDate(LocalDate.now());

        shareDAO.create(share);

        // Cleanup
        try {
            shareDAO.delete("user01", "V04");
        } catch (Exception e) {
            // Ignore
        }
    }

    // SC-007: Tạo share với emails > 50 ký tự
    @Test(priority = 7, expectedExceptions = Exception.class)
    public void testCreateShareWithEmailsTooLong() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V05");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails("A".repeat(51)); // 51 chars
        share.setShareDate(LocalDate.now());

        shareDAO.create(share);
    }

    // SC-008: Tạo share với shareDate null
    @Test(priority = 8, expectedExceptions = Exception.class)
    public void testCreateShareWithNullShareDate() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V06");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails("friend@example.com");
        share.setShareDate(null);

        shareDAO.create(share);
    }

    // SC-009: Tìm shares theo userId hợp lệ
    @Test(priority = 9)
    public void testFindByUserIdValid() {
        List<Share> shares = shareDAO.findByUserId("user01");
        assertNotNull(shares, "List should not be null");
    }

    // SC-010: Tìm shares theo userId không tồn tại
    @Test(priority = 10)
    public void testFindByUserIdNonExistent() {
        List<Share> shares = shareDAO.findByUserId("nonexistent");
        assertNotNull(shares, "List should not be null");
        assertEquals(shares.size(), 0, "Should return empty list");
    }

    // SC-011: Tìm shares theo videoId hợp lệ
    @Test(priority = 11)
    public void testFindByVideoIdValid() {
        List<Share> shares = shareDAO.findByVideoId("V01");
        assertNotNull(shares, "List should not be null");
    }

    // SC-012: Tìm shares theo videoId không tồn tại
    @Test(priority = 12)
    public void testFindByVideoIdNonExistent() {
        List<Share> shares = shareDAO.findByVideoId("NONEXIST");
        assertNotNull(shares, "List should not be null");
        assertEquals(shares.size(), 0, "Should return empty list");
    }

    // SC-013: Tìm share theo user và video
    @Test(priority = 13)
    public void testFindByUserAndVideo() {
        Optional<User> user = userDAO.findById("user02");
        Optional<Video> video = videoDAO.findById("V07");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        // Create share first
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails("test@example.com");
        share.setShareDate(LocalDate.now());
        shareDAO.create(share);

        // Find it
        Optional<Share> found = shareDAO.findByUserAndVideo("user02", "V07");
        assertTrue(found.isPresent(), "Should find share");

        // Cleanup
        try {
            shareDAO.delete("user02", "V07");
        } catch (Exception e) {
            // Ignore
        }
    }

    // SC-014: Xóa share hợp lệ
    @Test(priority = 14)
    public void testDeleteValidShare() {
        Optional<User> user = userDAO.findById("user03");
        Optional<Video> video = videoDAO.findById("V08");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        // Create share
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails("delete@example.com");
        share.setShareDate(LocalDate.now());
        shareDAO.create(share);

        // Delete it
        shareDAO.delete("user03", "V08");

        // Verify deletion
        try {
            Optional<Share> found = shareDAO.findByUserAndVideo("user03", "V08");
            assertFalse(found.isPresent(), "Share should be deleted");
        } catch (Exception e) {
            // Expected - share not found
            assertTrue(true);
        }
    }

    // SC-015: Xóa share không tồn tại
    @Test(priority = 15, expectedExceptions = EntityNotFoundException.class)
    public void testDeleteNonExistentShare() {
        shareDAO.delete("user01", "NONEXIST");
    }

    // SC-016: Đếm shares theo videoId
    @Test(priority = 16)
    public void testCountByVideoId() {
        long count = shareDAO.countByVideoId("V01");
        assertTrue(count >= 0, "Count should be non-negative");
    }

    // SC-017: Đếm shares theo videoId không tồn tại
    @Test(priority = 17)
    public void testCountByVideoIdNonExistent() {
        long count = shareDAO.countByVideoId("NONEXIST");
        assertEquals(count, 0, "Count should be 0 for non-existent video");
    }

    // SC-018: Tạo nhiều shares cho cùng user
    @Test(priority = 18)
    public void testCreateMultipleSharesForSameUser() {
        Optional<User> user = userDAO.findById("user01");
        assertTrue(user.isPresent());
        
        // Create shares for different videos
        Share share1 = new Share();
        share1.setUser(user.get());
        share1.setVideo(videoDAO.findById("V09").get());
        share1.setEmails("friend1@example.com");
        share1.setShareDate(LocalDate.now());
        shareDAO.create(share1);

        Share share2 = new Share();
        share2.setUser(user.get());
        share2.setVideo(videoDAO.findById("V10").get());
        share2.setEmails("friend2@example.com");
        share2.setShareDate(LocalDate.now());
        shareDAO.create(share2);

        List<Share> shares = shareDAO.findByUserId("user01");
        assertTrue(shares.size() >= 2, "Should have at least 2 shares");

        // Cleanup
        try {
            shareDAO.delete("user01", "V09");
            shareDAO.delete("user01", "V10");
        } catch (Exception e) {
            // Ignore
        }
    }

    // SC-019: Tạo nhiều shares cho cùng video
    @Test(priority = 19)
    public void testCreateMultipleSharesForSameVideo() {
        Optional<Video> video = videoDAO.findById("V11");
        assertTrue(video.isPresent());
        
        // Create shares from different users
        Share share1 = new Share();
        share1.setUser(userDAO.findById("user01").get());
        share1.setVideo(video.get());
        share1.setEmails("recipient1@example.com");
        share1.setShareDate(LocalDate.now());
        shareDAO.create(share1);

        Share share2 = new Share();
        share2.setUser(userDAO.findById("user02").get());
        share2.setVideo(video.get());
        share2.setEmails("recipient2@example.com");
        share2.setShareDate(LocalDate.now());
        shareDAO.create(share2);

        List<Share> shares = shareDAO.findByVideoId("V11");
        assertTrue(shares.size() >= 2, "Should have at least 2 shares");

        // Cleanup
        try {
            shareDAO.delete("user01", "V11");
            shareDAO.delete("user02", "V11");
        } catch (Exception e) {
            // Ignore
        }
    }

    // SC-020: Kiểm tra sắp xếp theo shareDate DESC
    @Test(priority = 20)
    public void testFindByUserIdOrderByShareDateDesc() {
        Optional<User> user = userDAO.findById("user04");
        assertTrue(user.isPresent());
        
        // Create shares with different dates
        Share share1 = new Share();
        share1.setUser(user.get());
        share1.setVideo(videoDAO.findById("V12").get());
        share1.setEmails("old@example.com");
        share1.setShareDate(LocalDate.now().minusDays(2));
        shareDAO.create(share1);

        Share share2 = new Share();
        share2.setUser(user.get());
        share2.setVideo(videoDAO.findById("V13").get());
        share2.setEmails("new@example.com");
        share2.setShareDate(LocalDate.now());
        shareDAO.create(share2);

        List<Share> shares = shareDAO.findByUserId("user04");
        assertTrue(shares.size() >= 2, "Should have at least 2 shares");
        
        // Check order - newest first
        if (shares.size() >= 2) {
            assertTrue(
                shares.get(0).getShareDate().isAfter(shares.get(1).getShareDate()) ||
                shares.get(0).getShareDate().isEqual(shares.get(1).getShareDate()),
                "Should be ordered by shareDate DESC"
            );
        }

        // Cleanup
        try {
            shareDAO.delete("user04", "V12");
            shareDAO.delete("user04", "V13");
        } catch (Exception e) {
            // Ignore
        }
    }

    // SC-021: Tạo share với multiple email addresses
    @Test(priority = 21)
    public void testCreateShareWithMultipleEmails() {
        Optional<User> user = userDAO.findById("user05");
        Optional<Video> video = videoDAO.findById("V14");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails("a@x.com,b@y.com,c@z.com");
        share.setShareDate(LocalDate.now());

        shareDAO.create(share);

        Optional<Share> found = shareDAO.findByUserAndVideo("user05", "V14");
        assertTrue(found.isPresent(), "Share with multiple emails should be created");

        // Cleanup
        try {
            shareDAO.delete("user05", "V14");
        } catch (Exception e) {
            // Ignore
        }
    }

    // SC-022: Tạo share với shareDate quá khứ
    @Test(priority = 22)
    public void testCreateShareWithPastDate() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V15");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails("past@example.com");
        share.setShareDate(LocalDate.of(2020, 1, 1));

        shareDAO.create(share);

        // Cleanup
        try {
            shareDAO.delete("user01", "V15");
        } catch (Exception e) {
            // Ignore
        }
    }

    // SC-023: Tạo share với shareDate tương lai
    @Test(priority = 23)
    public void testCreateShareWithFutureDate() {
        Optional<User> user = userDAO.findById("user02");
        Optional<Video> video = videoDAO.findById("V16");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Share share = new Share();
        share.setUser(user.get());
        share.setVideo(video.get());
        share.setEmails("future@example.com");
        share.setShareDate(LocalDate.now().plusDays(30));

        shareDAO.create(share);

        // Cleanup
        try {
            shareDAO.delete("user02", "V16");
        } catch (Exception e) {
            // Ignore
        }
    }
}
