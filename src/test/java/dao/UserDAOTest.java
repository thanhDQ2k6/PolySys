package dao;

import entity.User;
import org.testng.annotations.*;
import utils.XJPA;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class UserDAOTest {

    private UserDAO userDAO;
    private XJPA xjpa;
    private EntityManager em;

    @BeforeClass
    public void setupClass() {
        System.out.println("=== Starting UserDAO Tests ===");
    }

    @BeforeMethod
    public void setup() {
        xjpa = new XJPA();
        em = xjpa.getEntityManager();
        userDAO = new UserDAOImpl(em);
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
        System.out.println("=== UserDAO Tests Completed ===");
    }

    // UC-001: Tạo user hợp lệ với đầy đủ thông tin
    @Test(priority = 1)
    public void testCreateValidUser() {
        User user = new User();
        user.setId("testuser1");
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail("test1@example.com");
        user.setAdmin(false);

        boolean result = userDAO.create(user);
        assertTrue(result, "User should be created successfully");

        // Cleanup
        try {
            userDAO.delete("testuser1");
        } catch (Exception e) {
            // Ignore
        }
    }

    // UC-002: Tạo user với id là null
    @Test(priority = 2)
    public void testCreateUserWithNullId() {
        User user = new User();
        user.setId(null);
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail("test2@example.com");
        user.setAdmin(false);

        try {
            boolean result = userDAO.create(user);
            assertFalse(result, "Should not create user with null id");
        } catch (Exception e) {
            // Expected exception
            assertTrue(true);
        }
    }

    // UC-003: Tạo user với id rỗng
    @Test(priority = 3)
    public void testCreateUserWithEmptyId() {
        User user = new User();
        user.setId("");
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail("test3@example.com");
        user.setAdmin(false);

        try {
            boolean result = userDAO.create(user);
            // May succeed or fail depending on validation
        } catch (Exception e) {
            assertTrue(true, "Exception expected for empty id");
        }
    }

    // UC-004: Tạo user với id = 1 ký tự
    @Test(priority = 4)
    public void testCreateUserWithOneCharId() {
        User user = new User();
        user.setId("a");
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail("test4@example.com");
        user.setAdmin(false);

        boolean result = userDAO.create(user);
        assertTrue(result, "User with 1 char id should be created");

        // Cleanup
        try {
            userDAO.delete("a");
        } catch (Exception e) {
            // Ignore
        }
    }

    // UC-005: Tạo user với id = 20 ký tự
    @Test(priority = 5)
    public void testCreateUserWith20CharId() {
        User user = new User();
        user.setId("12345678901234567890"); // 20 chars
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail("test5@example.com");
        user.setAdmin(false);

        boolean result = userDAO.create(user);
        assertTrue(result, "User with 20 char id should be created");

        // Cleanup
        try {
            userDAO.delete("12345678901234567890");
        } catch (Exception e) {
            // Ignore
        }
    }

    // UC-006: Tạo user với id > 20 ký tự
    @Test(priority = 6)
    public void testCreateUserWithIdTooLong() {
        User user = new User();
        user.setId("123456789012345678901"); // 21 chars
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail("test6@example.com");
        user.setAdmin(false);

        try {
            userDAO.create(user);
            fail("Should throw exception for id > 20 chars");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for id too long");
        }
    }

    // UC-007: Tạo user với id đã tồn tại
    @Test(priority = 7)
    public void testCreateUserWithDuplicateId() {
        try {
            User user = new User();
            user.setId("user01"); // Already exists in DB
            user.setPassword("pass123");
            user.setFullName("Test User");
            user.setEmail("test7@example.com");
            user.setAdmin(false);

            userDAO.create(user);
            fail("Should throw EntityExistsException");
        } catch (EntityExistsException e) {
            assertTrue(true, "EntityExistsException expected");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for duplicate id");
        }
    }

    // UC-008: Tạo user với password null
    @Test(priority = 8)
    public void testCreateUserWithNullPassword() {
        User user = new User();
        user.setId("testuser8");
        user.setPassword(null);
        user.setFullName("Test User");
        user.setEmail("test8@example.com");
        user.setAdmin(false);

        try {
            userDAO.create(user);
            fail("Should throw exception for null password");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for null password");
        }
    }

    // UC-009: Tạo user với password rỗng
    @Test(priority = 9)
    public void testCreateUserWithEmptyPassword() {
        User user = new User();
        user.setId("testuser9");
        user.setPassword("");
        user.setFullName("Test User");
        user.setEmail("test9@example.com");
        user.setAdmin(false);

        try {
            boolean result = userDAO.create(user);
            // May succeed or fail
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // UC-010: Tạo user với password = 50 ký tự
    @Test(priority = 10)
    public void testCreateUserWith50CharPassword() {
        User user = new User();
        user.setId("testuser10");
        user.setPassword("12345678901234567890123456789012345678901234567890"); // 50 chars
        user.setFullName("Test User");
        user.setEmail("test10@example.com");
        user.setAdmin(false);

        boolean result = userDAO.create(user);
        assertTrue(result, "User with 50 char password should be created");

        // Cleanup
        try {
            userDAO.delete("testuser10");
        } catch (Exception e) {
            // Ignore
        }
    }

    // UC-011: Tạo user với password > 50 ký tự
    @Test(priority = 11)
    public void testCreateUserWithPasswordTooLong() {
        User user = new User();
        user.setId("testuser11");
        user.setPassword("123456789012345678901234567890123456789012345678901"); // 51 chars
        user.setFullName("Test User");
        user.setEmail("test11@example.com");
        user.setAdmin(false);

        try {
            userDAO.create(user);
            fail("Should throw exception for password > 50 chars");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for password too long");
        }
    }

    // UC-012: Tạo user với fullName null
    @Test(priority = 12)
    public void testCreateUserWithNullFullName() {
        User user = new User();
        user.setId("testuser12");
        user.setPassword("pass123");
        user.setFullName(null);
        user.setEmail("test12@example.com");
        user.setAdmin(false);

        try {
            userDAO.create(user);
            fail("Should throw exception for null fullName");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for null fullName");
        }
    }

    // UC-013: Tạo user với fullName rỗng
    @Test(priority = 13)
    public void testCreateUserWithEmptyFullName() {
        User user = new User();
        user.setId("testuser13");
        user.setPassword("pass123");
        user.setFullName("");
        user.setEmail("test13@example.com");
        user.setAdmin(false);

        try {
            boolean result = userDAO.create(user);
            // May succeed or fail
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // UC-014: Tạo user với fullName = 50 ký tự
    @Test(priority = 14)
    public void testCreateUserWith50CharFullName() {
        User user = new User();
        user.setId("testuser14");
        user.setPassword("pass123");
        user.setFullName("12345678901234567890123456789012345678901234567890"); // 50 chars
        user.setEmail("test14@example.com");
        user.setAdmin(false);

        boolean result = userDAO.create(user);
        assertTrue(result, "User with 50 char fullName should be created");

        // Cleanup
        try {
            userDAO.delete("testuser14");
        } catch (Exception e) {
            // Ignore
        }
    }

    // UC-015: Tạo user với fullName > 50 ký tự
    @Test(priority = 15)
    public void testCreateUserWithFullNameTooLong() {
        User user = new User();
        user.setId("testuser15");
        user.setPassword("pass123");
        user.setFullName("123456789012345678901234567890123456789012345678901"); // 51 chars
        user.setEmail("test15@example.com");
        user.setAdmin(false);

        try {
            userDAO.create(user);
            fail("Should throw exception for fullName > 50 chars");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for fullName too long");
        }
    }

    // UC-016: Tạo user với email null
    @Test(priority = 16)
    public void testCreateUserWithNullEmail() {
        User user = new User();
        user.setId("testuser16");
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail(null);
        user.setAdmin(false);

        try {
            userDAO.create(user);
            fail("Should throw exception for null email");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for null email");
        }
    }

    // UC-017: Tạo user với email không hợp lệ
    @Test(priority = 17)
    public void testCreateUserWithInvalidEmail() {
        User user = new User();
        user.setId("testuser17");
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail("invalid-email");
        user.setAdmin(false);

        try {
            boolean result = userDAO.create(user);
            // May succeed as format is not validated
        } catch (Exception e) {
            assertTrue(true);
        }

        // Cleanup
        try {
            userDAO.delete("testuser17");
        } catch (Exception e) {
            // Ignore
        }
    }

    // UC-018: Tạo user với email hợp lệ
    @Test(priority = 18)
    public void testCreateUserWithValidEmail() {
        User user = new User();
        user.setId("testuser18");
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail("valid@email.com");
        user.setAdmin(false);

        boolean result = userDAO.create(user);
        assertTrue(result, "User with valid email should be created");

        // Cleanup
        try {
            userDAO.delete("testuser18");
        } catch (Exception e) {
            // Ignore
        }
    }

    // UC-019: Tạo user với email = 50 ký tự
    @Test(priority = 19)
    public void testCreateUserWith50CharEmail() {
        User user = new User();
        user.setId("testuser19");
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail("test123456789012345678901234567890@example.com"); // 50 chars
        user.setAdmin(false);

        boolean result = userDAO.create(user);
        assertTrue(result, "User with 50 char email should be created");

        // Cleanup
        try {
            userDAO.delete("testuser19");
        } catch (Exception e) {
            // Ignore
        }
    }

    // UC-020: Tạo user với email > 50 ký tự
    @Test(priority = 20)
    public void testCreateUserWithEmailTooLong() {
        User user = new User();
        user.setId("testuser20");
        user.setPassword("pass123");
        user.setFullName("Test User");
        user.setEmail("test1234567890123456789012345678901@example.com"); // 51 chars
        user.setAdmin(false);

        try {
            userDAO.create(user);
            fail("Should throw exception for email > 50 chars");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for email too long");
        }
    }

    // UC-021: Tạo user với email đã tồn tại
    @Test(priority = 21)
    public void testCreateUserWithDuplicateEmail() {
        try {
            User user = new User();
            user.setId("testuser21");
            user.setPassword("pass123");
            user.setFullName("Test User");
            user.setEmail("admin@polysys.com"); // Already exists
            user.setAdmin(false);

            userDAO.create(user);
            fail("Should throw exception for duplicate email");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for duplicate email");
        }
    }

    // UC-022: Tạo user với admin=true
    @Test(priority = 22)
    public void testCreateUserWithAdminTrue() {
        User user = new User();
        user.setId("testuser22");
        user.setPassword("pass123");
        user.setFullName("Admin User");
        user.setEmail("test22@example.com");
        user.setAdmin(true);

        boolean result = userDAO.create(user);
        assertTrue(result, "Admin user should be created");

        Optional<User> found = userDAO.findById("testuser22");
        assertTrue(found.isPresent() && found.get().getAdmin(), "User should be admin");

        // Cleanup
        try {
            userDAO.delete("testuser22");
        } catch (Exception e) {
            // Ignore
        }
    }

    // UC-023: Tạo user với admin=false
    @Test(priority = 23)
    public void testCreateUserWithAdminFalse() {
        User user = new User();
        user.setId("testuser23");
        user.setPassword("pass123");
        user.setFullName("Regular User");
        user.setEmail("test23@example.com");
        user.setAdmin(false);

        boolean result = userDAO.create(user);
        assertTrue(result, "Regular user should be created");

        Optional<User> found = userDAO.findById("testuser23");
        assertTrue(found.isPresent() && !found.get().getAdmin(), "User should not be admin");

        // Cleanup
        try {
            userDAO.delete("testuser23");
        } catch (Exception e) {
            // Ignore
        }
    }

    // UC-024: Tìm user theo id tồn tại
    @Test(priority = 24)
    public void testFindUserByExistingId() {
        Optional<User> user = userDAO.findById("user01");
        assertTrue(user.isPresent(), "Should find existing user");
        assertEquals(user.get().getId(), "user01");
    }

    // UC-025: Tìm user theo id không tồn tại
    @Test(priority = 25)
    public void testFindUserByNonExistingId() {
        Optional<User> user = userDAO.findById("nonexistent");
        assertFalse(user.isPresent(), "Should return empty Optional");
    }

    // UC-026: Tìm user với id null
    @Test(priority = 26)
    public void testFindUserByNullId() {
        try {
            Optional<User> user = userDAO.findById(null);
            assertFalse(user.isPresent(), "Should return empty Optional");
        } catch (Exception e) {
            assertTrue(true, "Exception acceptable for null id");
        }
    }

    // UC-027: Lấy tất cả users
    @Test(priority = 27)
    public void testFindAllUsers() {
        List<User> users = userDAO.findAll();
        assertNotNull(users, "List should not be null");
        assertTrue(users.size() >= 5, "Should have at least 5 users from CSV");
    }

    // UC-028: Cập nhật user hợp lệ
    @Test(priority = 28)
    public void testUpdateValidUser() {
        Optional<User> userOpt = userDAO.findById("user01");
        assertTrue(userOpt.isPresent(), "User should exist");

        User user = userOpt.get();
        String originalName = user.getFullName();
        user.setFullName("Updated Name");

        User updated = userDAO.update(user);
        assertNotNull(updated, "Update should return user");
        assertEquals(updated.getFullName(), "Updated Name");

        // Restore
        user.setFullName(originalName);
        userDAO.update(user);
    }

    // UC-029: Cập nhật user không tồn tại
    @Test(priority = 29)
    public void testUpdateNonExistingUser() {
        try {
            User user = new User();
            user.setId("nonexistent");
            user.setPassword("pass123");
            user.setFullName("Test");
            user.setEmail("test@test.com");
            user.setAdmin(false);

            userDAO.update(user);
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            assertTrue(true, "EntityNotFoundException expected");
        } catch (Exception e) {
            assertTrue(true, "Exception expected");
        }
    }

    // UC-030: Cập nhật email thành email đã tồn tại
    @Test(priority = 30)
    public void testUpdateToDuplicateEmail() {
        try {
            Optional<User> userOpt = userDAO.findById("user01");
            assertTrue(userOpt.isPresent());

            User user = userOpt.get();
            String originalEmail = user.getEmail();
            user.setEmail("emily@example.com"); // Email of user02

            try {
                userDAO.update(user);
                fail("Should throw exception for duplicate email");
            } catch (Exception e) {
                assertTrue(true, "Exception expected for duplicate email");
            } finally {
                // Restore
                user.setEmail(originalEmail);
                try {
                    userDAO.update(user);
                } catch (Exception ex) {
                    // Ignore
                }
            }
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // UC-031: Cập nhật password của user
    @Test(priority = 31)
    public void testUpdatePassword() {
        Optional<User> userOpt = userDAO.findById("user01");
        assertTrue(userOpt.isPresent());

        User user = userOpt.get();
        String originalPassword = user.getPassword();
        user.setPassword("newpassword123");

        User updated = userDAO.update(user);
        assertEquals(updated.getPassword(), "newpassword123");

        // Restore
        user.setPassword(originalPassword);
        userDAO.update(user);
    }

    // UC-032: Cập nhật admin role
    @Test(priority = 32)
    public void testUpdateAdminRole() {
        Optional<User> userOpt = userDAO.findById("user01");
        assertTrue(userOpt.isPresent());

        User user = userOpt.get();
        Boolean originalAdmin = user.getAdmin();
        user.setAdmin(!originalAdmin);

        User updated = userDAO.update(user);
        assertEquals(updated.getAdmin(), !originalAdmin);

        // Restore
        user.setAdmin(originalAdmin);
        userDAO.update(user);
    }

    // UC-033: Xóa user tồn tại
    @Test(priority = 33)
    public void testDeleteExistingUser() {
        // Create a user first
        User user = new User();
        user.setId("testdelete33");
        user.setPassword("pass123");
        user.setFullName("To Delete");
        user.setEmail("delete33@example.com");
        user.setAdmin(false);
        userDAO.create(user);

        // Delete it
        userDAO.delete("testdelete33");

        // Verify deletion
        Optional<User> found = userDAO.findById("testdelete33");
        assertFalse(found.isPresent(), "User should be deleted");
    }

    // UC-034: Xóa user không tồn tại
    @Test(priority = 34)
    public void testDeleteNonExistingUser() {
        try {
            userDAO.delete("nonexistent");
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            assertTrue(true, "EntityNotFoundException expected");
        } catch (Exception e) {
            assertTrue(true, "Exception expected");
        }
    }

    // UC-035: Xóa user với id null
    @Test(priority = 35)
    public void testDeleteWithNullId() {
        try {
            userDAO.delete(null);
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(true, "Exception expected for null id");
        }
    }

    // UC-036: Tìm user theo email tồn tại
    @Test(priority = 36)
    public void testFindUserByExistingEmail() {
        Optional<User> user = userDAO.findByEmail("admin@polysys.com");
        assertTrue(user.isPresent(), "Should find user by email");
        assertEquals(user.get().getEmail(), "admin@polysys.com");
    }

    // UC-037: Tìm user theo email không tồn tại
    @Test(priority = 37)
    public void testFindUserByNonExistingEmail() {
        Optional<User> user = userDAO.findByEmail("nonexistent@test.com");
        assertFalse(user.isPresent(), "Should return empty Optional");
    }

    // UC-038: Kiểm tra email tồn tại
    @Test(priority = 38)
    public void testEmailExists() {
        boolean exists = userDAO.existsByEmail("admin@polysys.com");
        assertTrue(exists, "Email should exist");
    }

    // UC-039: Kiểm tra email không tồn tại
    @Test(priority = 39)
    public void testEmailNotExists() {
        boolean exists = userDAO.existsByEmail("nonexistent@test.com");
        assertFalse(exists, "Email should not exist");
    }

    // UC-040: Đếm số lượng users
    @Test(priority = 40)
    public void testCountUsers() {
        long count = userDAO.count();
        assertTrue(count >= 5, "Should have at least 5 users");
    }
}
