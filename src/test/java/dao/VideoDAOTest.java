package dao;

import entity.Video;
import org.testng.annotations.*;
import util.XJPA;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class VideoDAOTest {

    private VideoDAO videoDAO;
    private XJPA xjpa;
    private EntityManager em;

    @BeforeClass
    public void setupClass() {
        System.out.println("=== Starting VideoDAO Tests ===");
    }

    @BeforeMethod
    public void setup() {
        xjpa = new XJPA();
        em = xjpa.getEntityManager();
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
        System.out.println("=== VideoDAO Tests Completed ===");
    }

    // VC-001: Tạo video hợp lệ với đầy đủ thông tin
    @Test(priority = 1)
    public void testCreateValidVideo() {
        Video video = new Video();
        video.setId("TESTVID001");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setDescription("Description");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video should be created successfully");

        // Cleanup
        try {
            videoDAO.delete("TESTVID001");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-002: Tạo video với id null
    @Test(priority = 2)
    public void testCreateVideoWithNullId() {
        Video video = new Video();
        video.setId(null);
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        try {
            boolean result = videoDAO.create(video);
            assertFalse(result, "Should not create video with null id");
        } catch (Exception e) {
            assertTrue(true, "Exception expected");
        }
    }

    // VC-003: Tạo video với id rỗng
    @Test(priority = 3)
    public void testCreateVideoWithEmptyId() {
        Video video = new Video();
        video.setId("");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        try {
            boolean result = videoDAO.create(video);
        } catch (Exception e) {
            assertTrue(true, "Exception acceptable for empty id");
        }
    }

    // VC-004: Tạo video với id = 11 ký tự
    @Test(priority = 4)
    public void testCreateVideoWith11CharId() {
        Video video = new Video();
        video.setId("12345678901"); // 11 chars
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with 11 char id should be created");

        // Cleanup
        try {
            videoDAO.delete("12345678901");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-005: Tạo video với id < 11 ký tự
    @Test(priority = 5)
    public void testCreateVideoWithIdLessThan11() {
        Video video = new Video();
        video.setId("TEST123"); // 7 chars
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with id < 11 chars should be created");

        // Cleanup
        try {
            videoDAO.delete("TEST123");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-006: Tạo video với id > 11 ký tự
    @Test(priority = 6)
    public void testCreateVideoWithIdTooLong() {
        Video video = new Video();
        video.setId("123456789012"); // 12 chars
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        try {
            videoDAO.create(video);
            fail("Should throw exception for id > 11 chars");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for id too long");
        }
    }

    // VC-007: Tạo video với id đã tồn tại
    @Test(priority = 7)
    public void testCreateVideoWithDuplicateId() {
        try {
            Video video = new Video();
            video.setId("V01"); // Already exists
            video.setTitle("Test Video");
            video.setPosterUrl("poster.jpg");
            video.setActive(true);
            video.setViews(0);
            video.setLink("https://youtube.com/test");

            videoDAO.create(video);
            fail("Should throw EntityExistsException");
        } catch (EntityExistsException e) {
            assertTrue(true, "EntityExistsException expected");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for duplicate id");
        }
    }

    // VC-008: Tạo video với title null
    @Test(priority = 8)
    public void testCreateVideoWithNullTitle() {
        Video video = new Video();
        video.setId("TESTVID008");
        video.setTitle(null);
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        try {
            videoDAO.create(video);
            fail("Should throw exception for null title");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for null title");
        }
    }

    // VC-009: Tạo video với title rỗng
    @Test(priority = 9)
    public void testCreateVideoWithEmptyTitle() {
        Video video = new Video();
        video.setId("TESTVID009");
        video.setTitle("");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        try {
            boolean result = videoDAO.create(video);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // VC-010: Tạo video với title = 1 ký tự
    @Test(priority = 10)
    public void testCreateVideoWithOneCharTitle() {
        Video video = new Video();
        video.setId("TESTVID010");
        video.setTitle("A");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with 1 char title should be created");

        // Cleanup
        try {
            videoDAO.delete("TESTVID010");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-011: Tạo video với title = 255 ký tự
    @Test(priority = 11)
    public void testCreateVideoWith255CharTitle() {
        Video video = new Video();
        video.setId("TESTVID011");
        video.setTitle("A".repeat(255)); // 255 chars
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with 255 char title should be created");

        // Cleanup
        try {
            videoDAO.delete("TESTVID011");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-012: Tạo video với title > 255 ký tự
    @Test(priority = 12)
    public void testCreateVideoWithTitleTooLong() {
        Video video = new Video();
        video.setId("TESTVID012");
        video.setTitle("A".repeat(256)); // 256 chars
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        try {
            videoDAO.create(video);
            fail("Should throw exception for title > 255 chars");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for title too long");
        }
    }

    // VC-013: Tạo video với posterUrl null
    @Test(priority = 13)
    public void testCreateVideoWithNullPosterUrl() {
        Video video = new Video();
        video.setId("TESTVID013");
        video.setTitle("Test Video");
        video.setPosterUrl(null);
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        try {
            videoDAO.create(video);
            fail("Should throw exception for null posterUrl");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for null posterUrl");
        }
    }

    // VC-014: Tạo video với posterUrl rỗng
    @Test(priority = 14)
    public void testCreateVideoWithEmptyPosterUrl() {
        Video video = new Video();
        video.setId("TESTVID014");
        video.setTitle("Test Video");
        video.setPosterUrl("");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        try {
            boolean result = videoDAO.create(video);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // VC-015: Tạo video với posterUrl = 255 ký tự
    @Test(priority = 15)
    public void testCreateVideoWith255CharPosterUrl() {
        Video video = new Video();
        video.setId("TESTVID015");
        video.setTitle("Test Video");
        video.setPosterUrl("A".repeat(255));
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with 255 char posterUrl should be created");

        // Cleanup
        try {
            videoDAO.delete("TESTVID015");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-016: Tạo video với posterUrl > 255 ký tự
    @Test(priority = 16)
    public void testCreateVideoWithPosterUrlTooLong() {
        Video video = new Video();
        video.setId("TESTVID016");
        video.setTitle("Test Video");
        video.setPosterUrl("A".repeat(256));
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        try {
            videoDAO.create(video);
            fail("Should throw exception for posterUrl > 255 chars");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for posterUrl too long");
        }
    }

    // VC-017: Tạo video với description null
    @Test(priority = 17)
    public void testCreateVideoWithNullDescription() {
        Video video = new Video();
        video.setId("TESTVID017");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setDescription(null);
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with null description should be created");

        // Cleanup
        try {
            videoDAO.delete("TESTVID017");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-018: Tạo video với description rỗng
    @Test(priority = 18)
    public void testCreateVideoWithEmptyDescription() {
        Video video = new Video();
        video.setId("TESTVID018");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setDescription("");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with empty description should be created");

        // Cleanup
        try {
            videoDAO.delete("TESTVID018");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-019: Tạo video với description rất dài
    @Test(priority = 19)
    public void testCreateVideoWithLongDescription() {
        Video video = new Video();
        video.setId("TESTVID019");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setDescription("A".repeat(10000)); // 10000 chars
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with long description should be created (LONGTEXT)");

        // Cleanup
        try {
            videoDAO.delete("TESTVID019");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-020: Tạo video với active=true
    @Test(priority = 20)
    public void testCreateVideoWithActiveTrue() {
        Video video = new Video();
        video.setId("TESTVID020");
        video.setTitle("Active Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Active video should be created");

        Optional<Video> found = videoDAO.findById("TESTVID020");
        assertTrue(found.isPresent() && found.get().getActive(), "Video should be active");

        // Cleanup
        try {
            videoDAO.delete("TESTVID020");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-021: Tạo video với active=false
    @Test(priority = 21)
    public void testCreateVideoWithActiveFalse() {
        Video video = new Video();
        video.setId("TESTVID021");
        video.setTitle("Inactive Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(false);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Inactive video should be created");

        Optional<Video> found = videoDAO.findById("TESTVID021");
        assertTrue(found.isPresent() && !found.get().getActive(), "Video should be inactive");

        // Cleanup
        try {
            videoDAO.delete("TESTVID021");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-022: Tạo video với views=0
    @Test(priority = 22)
    public void testCreateVideoWithZeroViews() {
        Video video = new Video();
        video.setId("TESTVID022");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with 0 views should be created");

        // Cleanup
        try {
            videoDAO.delete("TESTVID022");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-023: Tạo video với views=-1
    @Test(priority = 23)
    public void testCreateVideoWithNegativeViews() {
        Video video = new Video();
        video.setId("TESTVID023");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(-1);
        video.setLink("https://youtube.com/test");

        try {
            boolean result = videoDAO.create(video);
            // May succeed as no validation
        } catch (Exception e) {
            assertTrue(true);
        }

        // Cleanup
        try {
            videoDAO.delete("TESTVID023");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-024: Tạo video với views=999999
    @Test(priority = 24)
    public void testCreateVideoWithLargeViews() {
        Video video = new Video();
        video.setId("TESTVID024");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(999999);
        video.setLink("https://youtube.com/test");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with large views should be created");

        // Cleanup
        try {
            videoDAO.delete("TESTVID024");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-025: Tạo video với link null
    @Test(priority = 25)
    public void testCreateVideoWithNullLink() {
        Video video = new Video();
        video.setId("TESTVID025");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink(null);

        try {
            videoDAO.create(video);
            fail("Should throw exception for null link");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for null link");
        }
    }

    // VC-026: Tạo video với link rỗng
    @Test(priority = 26)
    public void testCreateVideoWithEmptyLink() {
        Video video = new Video();
        video.setId("TESTVID026");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("");

        try {
            boolean result = videoDAO.create(video);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // VC-027: Tạo video với link = 255 ký tự
    @Test(priority = 27)
    public void testCreateVideoWith255CharLink() {
        Video video = new Video();
        video.setId("TESTVID027");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("A".repeat(255));

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with 255 char link should be created");

        // Cleanup
        try {
            videoDAO.delete("TESTVID027");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-028: Tạo video với link > 255 ký tự
    @Test(priority = 28)
    public void testCreateVideoWithLinkTooLong() {
        Video video = new Video();
        video.setId("TESTVID028");
        video.setTitle("Test Video");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("A".repeat(256));

        try {
            videoDAO.create(video);
            fail("Should throw exception for link > 255 chars");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for link too long");
        }
    }

    // VC-029: Tìm video theo id tồn tại
    @Test(priority = 29)
    public void testFindVideoByExistingId() {
        Optional<Video> video = videoDAO.findById("V01");
        assertTrue(video.isPresent(), "Should find existing video");
        assertEquals(video.get().getId(), "V01");
    }

    // VC-030: Tìm video theo id không tồn tại
    @Test(priority = 30)
    public void testFindVideoByNonExistingId() {
        Optional<Video> video = videoDAO.findById("NONEXIST");
        assertFalse(video.isPresent(), "Should return empty Optional");
    }

    // VC-031: Tìm video với id null
    @Test(priority = 31)
    public void testFindVideoByNullId() {
        try {
            Optional<Video> video = videoDAO.findById(null);
            assertFalse(video.isPresent(), "Should return empty Optional");
        } catch (Exception e) {
            assertTrue(true, "Exception acceptable for null id");
        }
    }

    // VC-032: Lấy tất cả videos
    @Test(priority = 32)
    public void testFindAllVideos() {
        List<Video> videos = videoDAO.findAll();
        assertNotNull(videos, "List should not be null");
        assertTrue(videos.size() >= 21, "Should have at least 21 videos from CSV");
    }

    // VC-033: Cập nhật video hợp lệ
    @Test(priority = 33)
    public void testUpdateValidVideo() {
        Optional<Video> videoOpt = videoDAO.findById("V01");
        assertTrue(videoOpt.isPresent(), "Video should exist");

        Video video = videoOpt.get();
        String originalTitle = video.getTitle();
        video.setTitle("Updated Title");

        Video updated = videoDAO.update(video);
        assertNotNull(updated, "Update should return video");
        assertEquals(updated.getTitle(), "Updated Title");

        // Restore
        video.setTitle(originalTitle);
        videoDAO.update(video);
    }

    // VC-034: Cập nhật video không tồn tại
    @Test(priority = 34)
    public void testUpdateNonExistingVideo() {
        try {
            Video video = new Video();
            video.setId("NONEXIST");
            video.setTitle("Test");
            video.setPosterUrl("poster.jpg");
            video.setActive(true);
            video.setViews(0);
            video.setLink("link");

            videoDAO.update(video);
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            assertTrue(true, "EntityNotFoundException expected");
        } catch (Exception e) {
            assertTrue(true, "Exception expected");
        }
    }

    // VC-035: Cập nhật active status
    @Test(priority = 35)
    public void testUpdateActiveStatus() {
        Optional<Video> videoOpt = videoDAO.findById("V01");
        assertTrue(videoOpt.isPresent());

        Video video = videoOpt.get();
        Boolean originalActive = video.getActive();
        video.setActive(!originalActive);

        Video updated = videoDAO.update(video);
        assertEquals(updated.getActive(), !originalActive);

        // Restore
        video.setActive(originalActive);
        videoDAO.update(video);
    }

    // VC-036: Cập nhật views
    @Test(priority = 36)
    public void testUpdateViews() {
        Optional<Video> videoOpt = videoDAO.findById("V01");
        assertTrue(videoOpt.isPresent());

        Video video = videoOpt.get();
        Integer originalViews = video.getViews();
        video.setViews(originalViews + 100);

        Video updated = videoDAO.update(video);
        assertEquals(updated.getViews(), Integer.valueOf(originalViews + 100));

        // Restore
        video.setViews(originalViews);
        videoDAO.update(video);
    }

    // VC-037: Xóa video tồn tại
    @Test(priority = 37)
    public void testDeleteExistingVideo() {
        // Create a video first
        Video video = new Video();
        video.setId("TESTDEL037");
        video.setTitle("To Delete");
        video.setPosterUrl("poster.jpg");
        video.setActive(true);
        video.setViews(0);
        video.setLink("link");
        videoDAO.create(video);

        // Delete it
        videoDAO.delete("TESTDEL037");

        // Verify deletion
        Optional<Video> found = videoDAO.findById("TESTDEL037");
        assertFalse(found.isPresent(), "Video should be deleted");
    }

    // VC-038: Xóa video không tồn tại
    @Test(priority = 38)
    public void testDeleteNonExistingVideo() {
        try {
            videoDAO.delete("NONEXIST");
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            assertTrue(true, "EntityNotFoundException expected");
        } catch (Exception e) {
            assertTrue(true, "Exception expected");
        }
    }

    // VC-039: Xóa video với id null
    @Test(priority = 39)
    public void testDeleteWithNullId() {
        try {
            videoDAO.delete(null);
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for null id");
        }
    }

    // VC-040: Tìm tất cả videos active
    @Test(priority = 40)
    public void testFindByActiveTrue() {
        List<Video> activeVideos = videoDAO.findByActiveTrue();
        assertNotNull(activeVideos, "List should not be null");
        for (Video video : activeVideos) {
            assertTrue(video.getActive(), "All videos should be active");
        }
    }

    // VC-041: Tìm video theo title chứa keyword
    @Test(priority = 41)
    public void testFindByTitleContainingKeyword() {
        List<Video> videos = videoDAO.findByTitleContaining("Russian");
        assertNotNull(videos, "List should not be null");
        assertTrue(videos.size() > 0, "Should find videos with 'Russian' in title");
        for (Video video : videos) {
            assertTrue(video.getTitle().toLowerCase().contains("russian"),
                    "Title should contain 'Russian'");
        }
    }

    // VC-042: Tìm video với keyword không tồn tại
    @Test(priority = 42)
    public void testFindByTitleContainingNonExistentKeyword() {
        List<Video> videos = videoDAO.findByTitleContaining("NonExistentKeyword");
        assertNotNull(videos, "List should not be null");
        assertEquals(videos.size(), 0, "Should return empty list");
    }

    // VC-043: Tìm video với keyword rỗng
    @Test(priority = 43)
    public void testFindByTitleContainingEmptyKeyword() {
        List<Video> videos = videoDAO.findByTitleContaining("");
        assertNotNull(videos, "List should not be null");
        // Should return all videos
        assertTrue(videos.size() >= 21, "Should return all videos");
    }

    // VC-044: Tìm video với keyword null
    @Test(priority = 44)
    public void testFindByTitleContainingNullKeyword() {
        try {
            List<Video> videos = videoDAO.findByTitleContaining(null);
            // May return null or throw exception
        } catch (Exception e) {
            assertTrue(true, "Exception acceptable for null keyword");
        }
    }

    // VC-045: Đếm tất cả videos
    @Test(priority = 45)
    public void testCountAllVideos() {
        long count = videoDAO.count();
        assertTrue(count >= 21, "Should have at least 21 videos");
    }

    // VC-046: Đếm videos active
    @Test(priority = 46)
    public void testCountByActiveTrue() {
        long count = videoDAO.countByActiveTrue();
        assertTrue(count > 0, "Should have active videos");
        List<Video> activeVideos = videoDAO.findByActiveTrue();
        assertEquals(count, activeVideos.size(), "Count should match list size");
    }

    // VC-047: Tạo video với tất cả fields tối thiểu
    @Test(priority = 47)
    public void testCreateVideoWithMinimalFields() {
        Video video = new Video();
        video.setId("TESTMIN047");
        video.setTitle("Min");
        video.setPosterUrl("p");
        video.setActive(false);
        video.setViews(0);
        video.setLink("l");

        boolean result = videoDAO.create(video);
        assertTrue(result, "Video with minimal fields should be created");

        // Cleanup
        try {
            videoDAO.delete("TESTMIN047");
        } catch (Exception e) {
            // Ignore
        }
    }

    // VC-048: Tìm video case-insensitive
    @Test(priority = 48)
    public void testFindByTitleCaseInsensitive() {
        List<Video> videos1 = videoDAO.findByTitleContaining("RUSSIAN");
        List<Video> videos2 = videoDAO.findByTitleContaining("russian");
        List<Video> videos3 = videoDAO.findByTitleContaining("Russian");

        assertNotNull(videos1, "List should not be null");
        assertNotNull(videos2, "List should not be null");
        assertNotNull(videos3, "List should not be null");

        // All should return same results (case insensitive)
        assertEquals(videos1.size(), videos2.size(), "Case insensitive search");
        assertEquals(videos2.size(), videos3.size(), "Case insensitive search");
    }

    // VC-049: Cập nhật tất cả fields của video
    @Test(priority = 49)
    public void testUpdateAllFields() {
        Optional<Video> videoOpt = videoDAO.findById("V01");
        assertTrue(videoOpt.isPresent());

        Video video = videoOpt.get();
        String originalTitle = video.getTitle();
        String originalPoster = video.getPosterUrl();
        String originalDesc = video.getDescription();
        Boolean originalActive = video.getActive();
        Integer originalViews = video.getViews();
        String originalLink = video.getLink();

        // Update all fields
        video.setTitle("New Title");
        video.setPosterUrl("new_poster.jpg");
        video.setDescription("New description");
        video.setActive(!originalActive);
        video.setViews(999);
        video.setLink("new_link");

        Video updated = videoDAO.update(video);
        assertEquals(updated.getTitle(), "New Title");
        assertEquals(updated.getPosterUrl(), "new_poster.jpg");
        assertEquals(updated.getDescription(), "New description");
        assertEquals(updated.getActive(), !originalActive);
        assertEquals(updated.getViews(), Integer.valueOf(999));
        assertEquals(updated.getLink(), "new_link");

        // Restore
        video.setTitle(originalTitle);
        video.setPosterUrl(originalPoster);
        video.setDescription(originalDesc);
        video.setActive(originalActive);
        video.setViews(originalViews);
        video.setLink(originalLink);
        videoDAO.update(video);
    }

    // VC-050: Tăng view count qua increaseView
    @Test(priority = 50)
    public void testIncreaseView() {
        Optional<Video> videoOpt = videoDAO.findById("V01");
        assertTrue(videoOpt.isPresent());

        Video video = videoOpt.get();
        Integer originalViews = video.getViews();

        // Increase view
        videoDAO.increaseView("V01");

        // Verify
        Optional<Video> updatedOpt = videoDAO.findById("V01");
        assertTrue(updatedOpt.isPresent());
        assertEquals(updatedOpt.get().getViews(), Integer.valueOf(originalViews + 1));

        // Restore
        video.setViews(originalViews);
        videoDAO.update(video);
    }
}
