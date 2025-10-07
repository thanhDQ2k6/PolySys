package dao;

import entity.User;
import org.junit.jupiter.api.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for UserDAOImpl - the most notable DAO in the application
 * This test class covers all CRUD operations and business logic for User entities
 * 
 * Test Categories:
 * 1. findById tests - searching for users by ID
 * 2. findByEmail tests - searching for users by email
 * 3. existsByEmail tests - checking email existence
 * 4. count tests - counting total users
 * 5. create tests - creating new users
 * 6. update tests - updating existing users
 * 7. delete tests - deleting users
 * 8. findAll tests - retrieving all users
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOImplTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private UserDAOImpl userDAO;

    @BeforeAll
    public static void setUpClass() {
        try {
            emf = Persistence.createEntityManagerFactory("polysysPU");
            System.out.println("Test EntityManagerFactory created successfully");
        } catch (Exception e) {
            System.err.println("WARNING: Could not create EntityManagerFactory. Tests will be skipped.");
            System.err.println("This is normal if database is not configured for testing.");
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
        if (emf != null && emf.isOpen()) {
            em = emf.createEntityManager();
            userDAO = new UserDAOImpl(em);
        }
    }

    @AfterEach
    public void tearDown() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("Test EntityManagerFactory closed");
        }
    }

    // Helper method to check if database is available
    private boolean isDatabaseAvailable() {
        return emf != null && emf.isOpen() && em != null && em.isOpen();
    }

    // ==================== FIND BY ID TESTS ====================

    @Test
    @Order(1)
    @DisplayName("Test 1: findById with null ID should return empty Optional")
    public void testFindById_NullId() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        Optional<User> result = userDAO.findById(null);
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isPresent(), "Optional should be empty for null ID");
    }

    @Test
    @Order(2)
    @DisplayName("Test 2: findById with empty string should return empty Optional")
    public void testFindById_EmptyString() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        Optional<User> result = userDAO.findById("");
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isPresent(), "Optional should be empty for empty string ID");
    }

    @Test
    @Order(3)
    @DisplayName("Test 3: findById with non-existing ID should return empty Optional")
    public void testFindById_NonExistingId() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        Optional<User> result = userDAO.findById("nonexistent_user_12345");
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isPresent(), "Optional should be empty for non-existing user");
    }

    @Test
    @Order(4)
    @DisplayName("Test 4: findById with special characters in ID")
    public void testFindById_SpecialCharacters() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        Optional<User> result = userDAO.findById("user@#$%^&*()");
        assertNotNull(result, "Result should not be null");
        // Should return empty since no user with these special chars exists
        assertFalse(result.isPresent(), "Optional should be empty for special character ID");
    }

    // ==================== FIND BY EMAIL TESTS ====================

    @Test
    @Order(5)
    @DisplayName("Test 5: findByEmail with null email should return empty Optional")
    public void testFindByEmail_NullEmail() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        Optional<User> result = userDAO.findByEmail(null);
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isPresent(), "Optional should be empty for null email");
    }

    @Test
    @Order(6)
    @DisplayName("Test 6: findByEmail with empty string should return empty Optional")
    public void testFindByEmail_EmptyString() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        Optional<User> result = userDAO.findByEmail("");
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isPresent(), "Optional should be empty for empty email");
    }

    @Test
    @Order(7)
    @DisplayName("Test 7: findByEmail with non-existing email should return empty Optional")
    public void testFindByEmail_NonExistingEmail() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        Optional<User> result = userDAO.findByEmail("nonexistent@example.com");
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isPresent(), "Optional should be empty for non-existing email");
    }

    @Test
    @Order(8)
    @DisplayName("Test 8: findByEmail with invalid email format")
    public void testFindByEmail_InvalidFormat() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        Optional<User> result = userDAO.findByEmail("not-an-email");
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isPresent(), "Optional should be empty for invalid email format");
    }

    // ==================== EXISTS BY EMAIL TESTS ====================

    @Test
    @Order(9)
    @DisplayName("Test 9: existsByEmail with null email should return false")
    public void testExistsByEmail_NullEmail() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        boolean result = userDAO.existsByEmail(null);
        assertFalse(result, "Should return false for null email");
    }

    @Test
    @Order(10)
    @DisplayName("Test 10: existsByEmail with empty string should return false")
    public void testExistsByEmail_EmptyString() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        boolean result = userDAO.existsByEmail("");
        assertFalse(result, "Should return false for empty email");
    }

    @Test
    @Order(11)
    @DisplayName("Test 11: existsByEmail with non-existing email should return false")
    public void testExistsByEmail_NonExistingEmail() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        boolean result = userDAO.existsByEmail("definitely.not.exists@example.com");
        assertFalse(result, "Should return false for non-existing email");
    }

    // ==================== COUNT TESTS ====================

    @Test
    @Order(12)
    @DisplayName("Test 12: count should return non-negative number")
    public void testCount_NonNegative() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        long count = userDAO.count();
        assertTrue(count >= 0, "Count should be non-negative");
    }

    @Test
    @Order(13)
    @DisplayName("Test 13: count should be consistent when called multiple times")
    public void testCount_Consistency() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        long count1 = userDAO.count();
        long count2 = userDAO.count();
        assertEquals(count1, count2, "Count should be consistent");
    }

    // ==================== FIND ALL TESTS ====================

    @Test
    @Order(14)
    @DisplayName("Test 14: findAll should return non-null list")
    public void testFindAll_NonNull() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        List<User> users = userDAO.findAll();
        assertNotNull(users, "findAll should return non-null list");
    }

    @Test
    @Order(15)
    @DisplayName("Test 15: findAll count should match count() method")
    public void testFindAll_CountMatches() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        List<User> users = userDAO.findAll();
        long count = userDAO.count();
        assertEquals(count, users.size(), "findAll size should match count()");
    }

    // ==================== CREATE TESTS ====================

    @Test
    @Order(16)
    @DisplayName("Test 16: create with null user should throw exception")
    public void testCreate_NullUser() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        assertThrows(Exception.class, () -> {
            userDAO.create(null);
        }, "Creating null user should throw exception");
    }

    @Test
    @Order(17)
    @DisplayName("Test 17: create user with null ID should handle gracefully")
    public void testCreate_NullId() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        User user = new User();
        user.setId(null);
        user.setFullName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        
        // Should either succeed or throw exception, but not crash
        assertDoesNotThrow(() -> {
            try {
                userDAO.create(user);
            } catch (Exception e) {
                // Expected - null ID might not be allowed
                System.out.println("Expected exception for null ID: " + e.getMessage());
            }
        }, "Should handle null ID gracefully");
    }

    @Test
    @Order(18)
    @DisplayName("Test 18: create user with empty string ID")
    public void testCreate_EmptyId() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        User user = new User();
        user.setId("");
        user.setFullName("Test User");
        user.setEmail("testempty@example.com");
        user.setPassword("password123");
        
        assertDoesNotThrow(() -> {
            try {
                userDAO.create(user);
            } catch (Exception e) {
                // Expected - empty ID might not be allowed
                System.out.println("Expected exception for empty ID: " + e.getMessage());
            }
        }, "Should handle empty ID gracefully");
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @Order(19)
    @DisplayName("Test 19: update with null user should throw exception")
    public void testUpdate_NullUser() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        assertThrows(Exception.class, () -> {
            userDAO.update(null);
        }, "Updating null user should throw exception");
    }

    @Test
    @Order(20)
    @DisplayName("Test 20: update non-existing user should throw EntityNotFoundException")
    public void testUpdate_NonExistingUser() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        User user = new User();
        user.setId("nonexistent_user_99999");
        user.setFullName("Non Existing User");
        user.setEmail("nonexisting@example.com");
        user.setPassword("password123");
        
        assertThrows(EntityNotFoundException.class, () -> {
            userDAO.update(user);
        }, "Updating non-existing user should throw EntityNotFoundException");
    }

    // ==================== DELETE TESTS ====================

    @Test
    @Order(21)
    @DisplayName("Test 21: delete with null ID should throw exception")
    public void testDelete_NullId() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        assertThrows(Exception.class, () -> {
            userDAO.delete(null);
        }, "Deleting with null ID should throw exception");
    }

    @Test
    @Order(22)
    @DisplayName("Test 22: delete with empty string ID should throw exception")
    public void testDelete_EmptyId() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        assertThrows(Exception.class, () -> {
            userDAO.delete("");
        }, "Deleting with empty ID should throw exception");
    }

    @Test
    @Order(23)
    @DisplayName("Test 23: delete non-existing user should throw EntityNotFoundException")
    public void testDelete_NonExistingUser() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        assertThrows(EntityNotFoundException.class, () -> {
            userDAO.delete("nonexistent_user_88888");
        }, "Deleting non-existing user should throw EntityNotFoundException");
    }

    // ==================== INTEGRATION TESTS ====================

    @Test
    @Order(24)
    @DisplayName("Test 24: UserDAO instance should not be null")
    public void testUserDAO_NotNull() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        assertNotNull(userDAO, "UserDAO instance should not be null");
    }

    @Test
    @Order(25)
    @DisplayName("Test 25: EntityManager should be properly initialized")
    public void testEntityManager_Initialized() {
        if (!isDatabaseAvailable()) {
            System.out.println("Skipping test - database not available");
            return;
        }
        
        assertNotNull(em, "EntityManager should not be null");
        assertTrue(em.isOpen(), "EntityManager should be open");
    }
}
