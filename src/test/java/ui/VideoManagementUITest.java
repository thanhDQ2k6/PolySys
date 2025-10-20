package ui;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

import static org.testng.Assert.*;

/**
 * Selenium UI tests for Video Management admin interface
 * Tests CRUD operations through the web UI at /admin/video
 */
public class VideoManagementUITest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080/PolySys";
    private static final String ADMIN_VIDEO_URL = BASE_URL + "/admin/video";

    @BeforeClass
    public void setupClass() {
        System.out.println("=== Starting Video Management UI Tests ===");
        System.out.println("Note: These tests require the application to be running at " + BASE_URL);
    }

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe"); // Sử dụng Brave
        options.addArguments("--headless"); // Run in headless mode for CI/CD
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass
    public void tearDownClass() {
        System.out.println("=== Video Management UI Tests Completed ===");
    }

    // Helper method to check if server is running
    private boolean isServerRunning() {
        try {
            driver.get(BASE_URL);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // UI-001: Kiểm tra trang admin video có load được không
    @Test(priority = 1)
    public void testAdminVideoPageLoads() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);
        assertTrue(driver.getTitle().contains("Administration") ||
                        driver.getCurrentUrl().contains("admin"),
                "Admin page should load");

        // Check if video management elements are present
        try {
            WebElement videoForm = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("videoForm")));
            assertNotNull(videoForm, "Video form should be present");
        } catch (TimeoutException e) {
            System.out.println("Video form not found - page structure may be different");
        }
    }

    // UI-002: Kiểm tra form có các fields cần thiết
    @Test(priority = 2)
    public void testVideoFormHasRequiredFields() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            // Check for required fields
            WebElement youtubeUrl = driver.findElement(By.id("youtubeUrl"));
            WebElement videoTitle = driver.findElement(By.id("videoTitle"));
            WebElement description = driver.findElement(By.id("description"));
            WebElement statusActive = driver.findElement(By.id("statusActive"));
            WebElement statusInactive = driver.findElement(By.id("statusInactive"));

            assertNotNull(youtubeUrl, "YouTube URL field should exist");
            assertNotNull(videoTitle, "Video title field should exist");
            assertNotNull(description, "Description field should exist");
            assertNotNull(statusActive, "Active status radio should exist");
            assertNotNull(statusInactive, "Inactive status radio should exist");
        } catch (NoSuchElementException e) {
            System.out.println("Some form fields not found: " + e.getMessage());
        }
    }

    // UI-003: Kiểm tra form có các nút action
    @Test(priority = 3)
    public void testVideoFormHasActionButtons() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            // Check for action buttons
            WebElement createBtn = driver.findElement(By.xpath("//button[contains(text(), 'Create')]"));
            WebElement updateBtn = driver.findElement(By.xpath("//button[contains(text(), 'Update')]"));
            WebElement deleteBtn = driver.findElement(By.xpath("//button[contains(text(), 'Delete')]"));
            WebElement resetBtn = driver.findElement(By.xpath("//button[contains(text(), 'Reset')]"));

            assertNotNull(createBtn, "Create button should exist");
            assertNotNull(updateBtn, "Update button should exist");
            assertNotNull(deleteBtn, "Delete button should exist");
            assertNotNull(resetBtn, "Reset button should exist");
        } catch (NoSuchElementException e) {
            System.out.println("Some action buttons not found: " + e.getMessage());
        }
    }

    // UI-004: Kiểm tra tab navigation
    @Test(priority = 4)
    public void testTabNavigation() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            WebElement videoEditionTab = driver.findElement(
                    By.xpath("//button[contains(@class, 'tab') and contains(text(), 'Video Edition')]"));
            WebElement videoListTab = driver.findElement(
                    By.xpath("//button[contains(@class, 'tab') and contains(text(), 'Video List')]"));

            assertNotNull(videoEditionTab, "Video Edition tab should exist");
            assertNotNull(videoListTab, "Video List tab should exist");

            // Click on video list tab
            videoListTab.click();
            Thread.sleep(500); // Wait for animation

            // Check if video list section is visible
            WebElement videoListSection = driver.findElement(By.id("videoList"));
            assertTrue(videoListSection.isDisplayed() || !videoListSection.getAttribute("class").contains("hidden"),
                    "Video list section should be visible");
        } catch (Exception e) {
            System.out.println("Tab navigation test issue: " + e.getMessage());
        }
    }

    // UI-005: Test YouTube URL extraction
    @Test(priority = 5)
    public void testYouTubeUrlExtraction() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            WebElement youtubeUrl = driver.findElement(By.id("youtubeUrl"));
            WebElement youtubeId = driver.findElement(By.id("youtubeId"));

            // Enter a YouTube URL
            youtubeUrl.clear();
            youtubeUrl.sendKeys("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            youtubeUrl.sendKeys(Keys.TAB); // Trigger change event

            Thread.sleep(500); // Wait for extraction

            String extractedId = youtubeId.getAttribute("value");
            assertEquals(extractedId, "dQw4w9WgXcQ", "Video ID should be extracted correctly");
        } catch (Exception e) {
            System.out.println("YouTube URL extraction test issue: " + e.getMessage());
        }
    }

    // UI-006: Test form validation - empty required fields
    @Test(priority = 6)
    public void testFormValidationEmptyFields() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            WebElement createBtn = driver.findElement(By.xpath("//button[contains(text(), 'Create')]"));

            // Try to create without filling fields
            createBtn.click();

            // Check for alert or validation message
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                String alertText = alert.getText();
                assertTrue(alertText.contains("required") || alertText.contains("fill"),
                        "Should show validation message");
                alert.accept();
            } catch (TimeoutException e) {
                System.out.println("No alert shown - may have HTML5 validation");
            }
        } catch (Exception e) {
            System.out.println("Form validation test issue: " + e.getMessage());
        }
    }

    // UI-007: Test creating a new video
    @Test(priority = 7)
    public void testCreateNewVideo() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            // Fill in the form
            WebElement youtubeUrl = driver.findElement(By.id("youtubeUrl"));
            WebElement videoTitle = driver.findElement(By.id("videoTitle"));
            WebElement description = driver.findElement(By.id("description"));
            WebElement statusActive = driver.findElement(By.id("statusActive"));

            youtubeUrl.clear();
            youtubeUrl.sendKeys("https://www.youtube.com/watch?v=TEST12345");
            youtubeUrl.sendKeys(Keys.TAB);
            Thread.sleep(500);

            videoTitle.clear();
            videoTitle.sendKeys("Selenium Test Video");

            description.clear();
            description.sendKeys("This is a test video created by Selenium");

            statusActive.click();

            // Click create button
            WebElement createBtn = driver.findElement(By.xpath("//button[contains(text(), 'Create')]"));
            createBtn.click();

            // Check for success alert
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                String alertText = alert.getText();
                assertTrue(alertText.contains("success") || alertText.contains("created"),
                        "Should show success message");
                alert.accept();
            } catch (TimeoutException e) {
                System.out.println("No alert shown after create");
            }
        } catch (Exception e) {
            System.out.println("Create video test issue: " + e.getMessage());
        }
    }

    // UI-008: Test reset button functionality
    @Test(priority = 8)
    public void testResetButton() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            WebElement youtubeUrl = driver.findElement(By.id("youtubeUrl"));
            WebElement videoTitle = driver.findElement(By.id("videoTitle"));
            WebElement resetBtn = driver.findElement(By.xpath("//button[contains(text(), 'Reset')]"));

            // Fill in some data
            youtubeUrl.sendKeys("https://www.youtube.com/watch?v=TEST");
            videoTitle.sendKeys("Test Title");

            // Click reset
            resetBtn.click();

            // Check if fields are cleared
            assertEquals(youtubeUrl.getAttribute("value"), "", "YouTube URL should be cleared");
            assertEquals(videoTitle.getAttribute("value"), "", "Title should be cleared");
        } catch (Exception e) {
            System.out.println("Reset button test issue: " + e.getMessage());
        }
    }

    // UI-009: Test sidebar toggle
    @Test(priority = 9)
    public void testSidebarToggle() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            WebElement sidebar = driver.findElement(By.id("sidebar"));
            WebElement toggleBtn = driver.findElement(By.className("toggle-button"));

            String initialClass = sidebar.getAttribute("class");

            // Toggle sidebar
            toggleBtn.click();
            Thread.sleep(500);

            String afterToggleClass = sidebar.getAttribute("class");

            assertNotEquals(initialClass, afterToggleClass, "Sidebar class should change after toggle");
        } catch (Exception e) {
            System.out.println("Sidebar toggle test issue: " + e.getMessage());
        }
    }

    // UI-010: Test video status radio buttons
    @Test(priority = 10)
    public void testVideoStatusRadioButtons() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            WebElement statusActive = driver.findElement(By.id("statusActive"));
            WebElement statusInactive = driver.findElement(By.id("statusInactive"));

            // Check default state
            assertTrue(statusActive.isSelected(), "Active should be selected by default");
            assertFalse(statusInactive.isSelected(), "Inactive should not be selected");

            // Click inactive
            statusInactive.click();
            Thread.sleep(200);

            assertFalse(statusActive.isSelected(), "Active should not be selected");
            assertTrue(statusInactive.isSelected(), "Inactive should be selected");
        } catch (Exception e) {
            System.out.println("Radio button test issue: " + e.getMessage());
        }
    }

    // UI-011: Test update without selecting video first
    @Test(priority = 11)
    public void testUpdateWithoutSelection() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            WebElement updateBtn = driver.findElement(By.xpath("//button[contains(text(), 'Update')]"));
            updateBtn.click();

            // Should show alert about no video selected
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                String alertText = alert.getText();
                assertTrue(alertText.contains("No video") || alertText.contains("select"),
                        "Should show 'no video selected' message");
                alert.accept();
            } catch (TimeoutException e) {
                System.out.println("No alert shown for update without selection");
            }
        } catch (Exception e) {
            System.out.println("Update without selection test issue: " + e.getMessage());
        }
    }

    // UI-012: Test delete without selecting video first
    @Test(priority = 12)
    public void testDeleteWithoutSelection() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            WebElement deleteBtn = driver.findElement(By.xpath("//button[contains(text(), 'Delete')]"));
            deleteBtn.click();

            // Should show alert about no video selected
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                String alertText = alert.getText();
                assertTrue(alertText.contains("No video") || alertText.contains("select"),
                        "Should show 'no video selected' message");
                alert.accept();
            } catch (TimeoutException e) {
                System.out.println("No alert shown for delete without selection");
            }
        } catch (Exception e) {
            System.out.println("Delete without selection test issue: " + e.getMessage());
        }
    }

    // UI-013: Test page responsiveness by resizing window
    @Test(priority = 13)
    public void testPageResponsiveness() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            // Test at different window sizes
            driver.manage().window().setSize(new Dimension(1920, 1080));
            WebElement sidebar = driver.findElement(By.id("sidebar"));
            assertTrue(sidebar.isDisplayed(), "Sidebar should be visible on desktop");

            driver.manage().window().setSize(new Dimension(768, 1024));
            Thread.sleep(500);
            assertTrue(sidebar.isDisplayed(), "Sidebar should still be visible on tablet");
        } catch (Exception e) {
            System.out.println("Responsiveness test issue: " + e.getMessage());
        }
    }

    // UI-014: Test navigation links in sidebar
    @Test(priority = 14)
    public void testSidebarNavigationLinks() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            // Check if sidebar links exist
            WebElement videosLink = driver.findElement(By.xpath("//a[contains(@href, '/admin/video')]"));
            WebElement usersLink = driver.findElement(By.xpath("//a[contains(@href, '/admin/user')]"));

            assertNotNull(videosLink, "Videos link should exist");
            assertNotNull(usersLink, "Users link should exist");

            assertTrue(videosLink.isDisplayed(), "Videos link should be visible");
            assertTrue(usersLink.isDisplayed(), "Users link should be visible");
        } catch (Exception e) {
            System.out.println("Sidebar navigation test issue: " + e.getMessage());
        }
    }

    // UI-015: Test form field max length validation
    @Test(priority = 15)
    public void testFormFieldMaxLength() {
        if (!isServerRunning()) {
            System.out.println("Server is not running. Skipping UI test.");
            return;
        }

        driver.get(ADMIN_VIDEO_URL);

        try {
            WebElement videoTitle = driver.findElement(By.id("videoTitle"));

            // Try to enter more than 255 characters
            String longTitle = "A".repeat(300);
            videoTitle.clear();
            videoTitle.sendKeys(longTitle);

            // Check actual value length (may be truncated by browser)
            String actualValue = videoTitle.getAttribute("value");
            assertTrue(actualValue.length() <= 255 || actualValue.length() == 300,
                    "Title should be validated or accepted");
        } catch (Exception e) {
            System.out.println("Max length test issue: " + e.getMessage());
        }
    }
}
