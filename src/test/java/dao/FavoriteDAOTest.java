package dao;

import entity.Favorite;
import entity.User;
import entity.Video;
import org.testng.annotations.*;
import util.XJPA;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class FavoriteDAOTest {

    private FavoriteDAO favoriteDAO;
    private UserDAO userDAO;
    private VideoDAO videoDAO;
    private XJPA xjpa;
    private EntityManager em;

    @BeforeClass
    public void setupClass() {
        System.out.println("=== Starting FavoriteDAO Tests ===");
    }

    @BeforeMethod
    public void setup() {
        xjpa = new XJPA();
        em = xjpa.getEntityManager();
        favoriteDAO = new FavoriteDAOImpl(em);
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
        System.out.println("=== FavoriteDAO Tests Completed ===");
    }

    // FC-001: Tạo favorite hợp lệ
    @Test(priority = 1)
    public void testCreateValidFavorite() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V01");
        
        assertTrue(user.isPresent() && video.isPresent(), "User and Video should exist");
        
        Favorite favorite = new Favorite();
        favorite.setUser(user.get());
        favorite.setVideo(video.get());
        favorite.setLikeDate(LocalDate.now());

        favoriteDAO.create(favorite);

        // Verify
        Optional<Favorite> found = favoriteDAO.findByUserAndVideo("user01", "V01");
        assertTrue(found.isPresent(), "Favorite should be created");

        // Cleanup
        try {
            favoriteDAO.delete("user01", "V01");
        } catch (Exception e) {
            // Ignore
        }
    }

    // FC-002: Tạo favorite với user null
    @Test(priority = 2, expectedExceptions = Exception.class)
    public void testCreateFavoriteWithNullUser() {
        Optional<Video> video = videoDAO.findById("V01");
        assertTrue(video.isPresent());
        
        Favorite favorite = new Favorite();
        favorite.setUser(null);
        favorite.setVideo(video.get());
        favorite.setLikeDate(LocalDate.now());

        favoriteDAO.create(favorite);
    }

    // FC-003: Tạo favorite với video null
    @Test(priority = 3, expectedExceptions = Exception.class)
    public void testCreateFavoriteWithNullVideo() {
        Optional<User> user = userDAO.findById("user01");
        assertTrue(user.isPresent());
        
        Favorite favorite = new Favorite();
        favorite.setUser(user.get());
        favorite.setVideo(null);
        favorite.setLikeDate(LocalDate.now());

        favoriteDAO.create(favorite);
    }

    // FC-004: Tạo favorite với likeDate null
    @Test(priority = 4, expectedExceptions = Exception.class)
    public void testCreateFavoriteWithNullLikeDate() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V02");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Favorite favorite = new Favorite();
        favorite.setUser(user.get());
        favorite.setVideo(video.get());
        favorite.setLikeDate(null);

        favoriteDAO.create(favorite);
    }

    // FC-005: Tạo favorite đã tồn tại (duplicate)
    @Test(priority = 5)
    public void testCreateDuplicateFavorite() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V03");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        // Create first favorite
        Favorite favorite1 = new Favorite();
        favorite1.setUser(user.get());
        favorite1.setVideo(video.get());
        favorite1.setLikeDate(LocalDate.now());
        favoriteDAO.create(favorite1);

        try {
            // Try to create duplicate
            Favorite favorite2 = new Favorite();
            favorite2.setUser(user.get());
            favorite2.setVideo(video.get());
            favorite2.setLikeDate(LocalDate.now());
            favoriteDAO.create(favorite2);
            fail("Should throw EntityExistsException for duplicate");
        } catch (EntityExistsException e) {
            assertTrue(true, "EntityExistsException expected");
        } finally {
            // Cleanup
            try {
                favoriteDAO.delete("user01", "V03");
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    // FC-006: Tìm favorites theo userId hợp lệ
    @Test(priority = 6)
    public void testFindByUserIdValid() {
        List<Favorite> favorites = favoriteDAO.findByUserId("user01");
        assertNotNull(favorites, "List should not be null");
    }

    // FC-007: Tìm favorites theo userId không tồn tại
    @Test(priority = 7)
    public void testFindByUserIdNonExistent() {
        List<Favorite> favorites = favoriteDAO.findByUserId("nonexistent");
        assertNotNull(favorites, "List should not be null");
        assertEquals(favorites.size(), 0, "Should return empty list");
    }

    // FC-008: Tìm favorites theo videoId hợp lệ
    @Test(priority = 8)
    public void testFindByVideoIdValid() {
        List<Favorite> favorites = favoriteDAO.findByVideoId("V01");
        assertNotNull(favorites, "List should not be null");
    }

    // FC-009: Tìm favorites theo videoId không tồn tại
    @Test(priority = 9)
    public void testFindByVideoIdNonExistent() {
        List<Favorite> favorites = favoriteDAO.findByVideoId("NONEXIST");
        assertNotNull(favorites, "List should not be null");
        assertEquals(favorites.size(), 0, "Should return empty list");
    }

    // FC-010: Tìm favorite theo user và video hợp lệ
    @Test(priority = 10)
    public void testFindByUserAndVideoValid() {
        Optional<User> user = userDAO.findById("user02");
        Optional<Video> video = videoDAO.findById("V04");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        // Create favorite first
        Favorite favorite = new Favorite();
        favorite.setUser(user.get());
        favorite.setVideo(video.get());
        favorite.setLikeDate(LocalDate.now());
        favoriteDAO.create(favorite);

        // Find it
        Optional<Favorite> found = favoriteDAO.findByUserAndVideo("user02", "V04");
        assertTrue(found.isPresent(), "Should find favorite");

        // Cleanup
        try {
            favoriteDAO.delete("user02", "V04");
        } catch (Exception e) {
            // Ignore
        }
    }

    // FC-011: Tìm favorite theo user và video không tồn tại
    @Test(priority = 11)
    public void testFindByUserAndVideoNonExistent() {
        Optional<Favorite> found = favoriteDAO.findByUserAndVideo("user01", "NONEXIST");
        assertFalse(found.isPresent(), "Should return empty Optional");
    }

    // FC-012: Xóa favorite hợp lệ
    @Test(priority = 12)
    public void testDeleteValidFavorite() {
        Optional<User> user = userDAO.findById("user03");
        Optional<Video> video = videoDAO.findById("V05");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        // Create favorite
        Favorite favorite = new Favorite();
        favorite.setUser(user.get());
        favorite.setVideo(video.get());
        favorite.setLikeDate(LocalDate.now());
        favoriteDAO.create(favorite);

        // Delete it
        favoriteDAO.delete("user03", "V05");

        // Verify deletion
        Optional<Favorite> found = favoriteDAO.findByUserAndVideo("user03", "V05");
        assertFalse(found.isPresent(), "Favorite should be deleted");
    }

    // FC-013: Xóa favorite không tồn tại
    @Test(priority = 13, expectedExceptions = EntityNotFoundException.class)
    public void testDeleteNonExistentFavorite() {
        favoriteDAO.delete("user01", "NONEXIST");
    }

    // FC-014: Đếm favorites theo videoId
    @Test(priority = 14)
    public void testCountByVideoId() {
        long count = favoriteDAO.countByVideoId("V01");
        assertTrue(count >= 0, "Count should be non-negative");
    }

    // FC-015: Đếm favorites theo videoId không tồn tại
    @Test(priority = 15)
    public void testCountByVideoIdNonExistent() {
        long count = favoriteDAO.countByVideoId("NONEXIST");
        assertEquals(count, 0, "Count should be 0 for non-existent video");
    }

    // FC-016: Tạo nhiều favorites cho cùng user
    @Test(priority = 16)
    public void testCreateMultipleFavoritesForSameUser() {
        Optional<User> user = userDAO.findById("user01");
        assertTrue(user.isPresent());
        
        // Create favorites for different videos
        Favorite fav1 = new Favorite();
        fav1.setUser(user.get());
        fav1.setVideo(videoDAO.findById("V06").get());
        fav1.setLikeDate(LocalDate.now());
        favoriteDAO.create(fav1);

        Favorite fav2 = new Favorite();
        fav2.setUser(user.get());
        fav2.setVideo(videoDAO.findById("V07").get());
        fav2.setLikeDate(LocalDate.now());
        favoriteDAO.create(fav2);

        List<Favorite> favorites = favoriteDAO.findByUserId("user01");
        assertTrue(favorites.size() >= 2, "Should have at least 2 favorites");

        // Cleanup
        try {
            favoriteDAO.delete("user01", "V06");
            favoriteDAO.delete("user01", "V07");
        } catch (Exception e) {
            // Ignore
        }
    }

    // FC-017: Tạo nhiều favorites cho cùng video
    @Test(priority = 17)
    public void testCreateMultipleFavoritesForSameVideo() {
        Optional<Video> video = videoDAO.findById("V08");
        assertTrue(video.isPresent());
        
        // Create favorites from different users
        Favorite fav1 = new Favorite();
        fav1.setUser(userDAO.findById("user01").get());
        fav1.setVideo(video.get());
        fav1.setLikeDate(LocalDate.now());
        favoriteDAO.create(fav1);

        Favorite fav2 = new Favorite();
        fav2.setUser(userDAO.findById("user02").get());
        fav2.setVideo(video.get());
        fav2.setLikeDate(LocalDate.now());
        favoriteDAO.create(fav2);

        List<Favorite> favorites = favoriteDAO.findByVideoId("V08");
        assertTrue(favorites.size() >= 2, "Should have at least 2 favorites");

        // Cleanup
        try {
            favoriteDAO.delete("user01", "V08");
            favoriteDAO.delete("user02", "V08");
        } catch (Exception e) {
            // Ignore
        }
    }

    // FC-018: Kiểm tra sắp xếp theo likeDate DESC
    @Test(priority = 18)
    public void testFindByUserIdOrderByLikeDateDesc() {
        Optional<User> user = userDAO.findById("user04");
        assertTrue(user.isPresent());
        
        // Create favorites with different dates
        Favorite fav1 = new Favorite();
        fav1.setUser(user.get());
        fav1.setVideo(videoDAO.findById("V09").get());
        fav1.setLikeDate(LocalDate.now().minusDays(2));
        favoriteDAO.create(fav1);

        Favorite fav2 = new Favorite();
        fav2.setUser(user.get());
        fav2.setVideo(videoDAO.findById("V10").get());
        fav2.setLikeDate(LocalDate.now());
        favoriteDAO.create(fav2);

        List<Favorite> favorites = favoriteDAO.findByUserId("user04");
        assertTrue(favorites.size() >= 2, "Should have at least 2 favorites");
        
        // Check order - newest first
        if (favorites.size() >= 2) {
            assertTrue(
                favorites.get(0).getLikeDate().isAfter(favorites.get(1).getLikeDate()) ||
                favorites.get(0).getLikeDate().isEqual(favorites.get(1).getLikeDate()),
                "Should be ordered by likeDate DESC"
            );
        }

        // Cleanup
        try {
            favoriteDAO.delete("user04", "V09");
            favoriteDAO.delete("user04", "V10");
        } catch (Exception e) {
            // Ignore
        }
    }

    // FC-019: Tạo favorite với likeDate quá khứ
    @Test(priority = 19)
    public void testCreateFavoriteWithPastDate() {
        Optional<User> user = userDAO.findById("user05");
        Optional<Video> video = videoDAO.findById("V11");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Favorite favorite = new Favorite();
        favorite.setUser(user.get());
        favorite.setVideo(video.get());
        favorite.setLikeDate(LocalDate.of(2020, 1, 1));

        favoriteDAO.create(favorite);

        Optional<Favorite> found = favoriteDAO.findByUserAndVideo("user05", "V11");
        assertTrue(found.isPresent(), "Favorite with past date should be created");

        // Cleanup
        try {
            favoriteDAO.delete("user05", "V11");
        } catch (Exception e) {
            // Ignore
        }
    }

    // FC-020: Tạo favorite với likeDate tương lai
    @Test(priority = 20)
    public void testCreateFavoriteWithFutureDate() {
        Optional<User> user = userDAO.findById("user01");
        Optional<Video> video = videoDAO.findById("V12");
        
        assertTrue(user.isPresent() && video.isPresent());
        
        Favorite favorite = new Favorite();
        favorite.setUser(user.get());
        favorite.setVideo(video.get());
        favorite.setLikeDate(LocalDate.now().plusDays(30));

        favoriteDAO.create(favorite);

        Optional<Favorite> found = favoriteDAO.findByUserAndVideo("user01", "V12");
        assertTrue(found.isPresent(), "Favorite with future date should be created");

        // Cleanup
        try {
            favoriteDAO.delete("user01", "V12");
        } catch (Exception e) {
            // Ignore
        }
    }
}
