# Login UI Test Cases - Tài liệu

## Tổng quan
Tài liệu này chứa các test cases UI toàn diện cho chức năng Đăng nhập sử dụng Selenium WebDriver và TestNG.

## Độ phủ Test
- **Tổng số Test Cases**: 10
- **Các lĩnh vực được bao phủ**: Tải trang, xác thực, validation, quản lý phiên

## Test Cases

| ID | Test Case Name | Description | Input | Expected Result | Priority |
|----|---------------|-------------|-------|-----------------|----------|
| LUI-001 | testLoginPageLoads | Verify login page loads correctly | Navigate to /login | Page loads, URL contains "login" | High |
| LUI-002 | testLoginFormHasRequiredFields | Verify login form has username, password fields | Load login page | Username, password, submit button present | High |
| LUI-003 | testLoginWithValidCredentials | Login with correct username and password | user01/pass01 | Redirect to video list or home | High |
| LUI-004 | testLoginWithInvalidUsername | Login with non-existent username | invaliduser/password | Stay on login page, show error | High |
| LUI-005 | testLoginWithInvalidPassword | Login with wrong password | user01/wrongpassword | Stay on login page, show error | High |
| LUI-006 | testLoginWithEmptyFields | Submit login form with empty fields | Empty username/password | Stay on login page, validation message | High |
| LUI-007 | testLogout | Logout functionality | Login then logout | Redirect to login page, session cleared | High |
| LUI-008 | testPasswordFieldIsMasked | Verify password field is masked | Check password field type | type="password" | Medium |
| LUI-009 | testSessionPersistence | Verify session persists after login | Login, navigate to different page | Remain authenticated | Medium |
| LUI-010 | testAdminLogin | Verify admin user can login | admin/admin123 | Successful login, admin access | Medium |

## Testing Techniques Applied

### 1. Positive Testing
- Valid credentials login
- Admin login
- Session persistence

### 2. Negative Testing
- Invalid username
- Invalid password
- Empty fields
- Null inputs

### 3. Security Testing
- Password masking
- Session management
- Logout functionality

## Selenium Setup
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");  // Run without GUI
options.addArguments("--no-sandbox");
options.addArguments("--disable-dev-shm-usage");
options.addArguments("--window-size=1920,1080");
```

## Test Data
- **Regular User**: user01 / pass01
- **Admin User**: admin / admin123
- **Invalid User**: invaliduser / password

## Expected Behavior

### Successful Login Flow
1. User enters valid credentials
2. Clicks submit button
3. System validates credentials
4. Creates user session
5. Redirects to video list or secured page

### Failed Login Flow
1. User enters invalid credentials
2. Clicks submit button
3. System validates credentials
4. Shows error message
5. Stays on login page

### Logout Flow
1. User clicks logout (or navigates to /login?action=logout)
2. System invalidates session
3. Redirects to login page

## Prerequisites
- Application server running at http://localhost:8080/PolySys
- Chrome/Chromium browser installed
- ChromeDriver compatible with browser version
- Database with test users

## Notes
- Tests run in headless mode for CI/CD compatibility
- Tests skip if server is not running
- Each test is independent with setup/teardown
- Tests use explicit waits for dynamic content
