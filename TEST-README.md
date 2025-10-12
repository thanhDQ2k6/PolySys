# PolySys Test Suite Documentation

## Tổng quan (Overview)

Bộ test suite này được thiết kế để kiểm thử toàn diện hệ thống PolySys, bao gồm:
- **Backend DAO Tests**: Kiểm thử các lớp truy cập dữ liệu (UserDAO, VideoDAO)
- **UI Tests**: Kiểm thử giao diện quản lý video của admin sử dụng Selenium

## Cấu trúc Test Cases

### 1. UserDAO Test Cases (40 test cases)
📄 File: `UserDAO-TestCases.md`

Kiểm thử tất cả các chức năng của UserDAO sử dụng các kỹ thuật kiểm thử hộp đen:
- **Phân vùng tương đương (Equivalence Partitioning)**: Dữ liệu hợp lệ/không hợp lệ
- **Phân tích giá trị biên (Boundary Value Analysis)**: Độ dài tối thiểu/tối đa (id:20, password:50, fullName:50, email:50)
- **Bảng quyết định (Decision Table)**: Kết hợp các ràng buộc (UNIQUE, NOT NULL)

**Các chức năng được kiểm thử:**
- ✅ Create user (23 test cases): Kiểm thử tất cả trường hợp biên, null, duplicate
- ✅ Read user (5 test cases): findById, findAll, findByEmail, existsByEmail, count
- ✅ Update user (5 test cases): Cập nhật hợp lệ, không tồn tại, duplicate email, từng trường
- ✅ Delete user (3 test cases): Xóa hợp lệ, không tồn tại, null ID
- ✅ Custom methods (4 test cases): Các phương thức đặc biệt của UserDAO

### 2. VideoDAO Test Cases (50 test cases)
📄 File: `VideoDAO-TestCases.md`

Kiểm thử tất cả các chức năng của VideoDAO sử dụng các kỹ thuật kiểm thử hộp đen:
- **Phân vùng tương đương**: Dữ liệu hợp lệ/không hợp lệ cho tất cả các trường
- **Phân tích giá trị biên**: Độ dài (id:11, title:255, posterUrl:255, link:255)
- **Bảng quyết định**: Kết hợp các ràng buộc

**Các chức năng được kiểm thử:**
- ✅ Create video (28 test cases): Tất cả trường hợp biên, null, duplicate, description rất dài
- ✅ Read video (4 test cases): findById, findAll, null ID
- ✅ Update video (5 test cases): Cập nhật hợp lệ, không tồn tại, status, views, tất cả fields
- ✅ Delete video (3 test cases): Xóa hợp lệ, không tồn tại, null ID
- ✅ Custom methods (10 test cases): findByActiveTrue, findByTitleContaining (case-insensitive), countByActiveTrue, increaseView

### 3. UI Test Cases (15 test cases)
📄 Class: `src/test/java/ui/VideoManagementUITest.java`

Kiểm thử giao diện quản lý video của admin:
- ✅ Page load và hiển thị form (4 test cases)
- ✅ Navigation và tabs (2 test cases)  
- ✅ Form validation (3 test cases)
- ✅ CRUD operations (3 test cases)
- ✅ User interactions (3 test cases): Radio buttons, sidebar, responsive

## Cách chạy tests

### Phương pháp 1: Sử dụng script tự động (Khuyến nghị)

```bash
# Chạy tất cả tests (backend + UI)
./run-tests.sh

# Chỉ chạy backend tests
./run-tests.sh backend

# Chỉ chạy UI tests (cần server đang chạy)
./run-tests.sh ui

# Chỉ chạy UserDAO tests
./run-tests.sh user

# Chỉ chạy VideoDAO tests
./run-tests.sh video
```

### Phương pháp 2: Chạy thủ công với Maven

```bash
# 1. Setup database
mysql -u root -p12345 < "src/main/resources/Database Generator/db-boot.sql"

# 2. Chạy tất cả tests
mvn test

# 3. Chạy một test class cụ thể
mvn test -Dtest=UserDAOTest
mvn test -Dtest=VideoDAOTest
mvn test -Dtest=VideoManagementUITest

# 4. Chạy một test method cụ thể
mvn test -Dtest=UserDAOTest#testCreateValidUser
```

## Yêu cầu hệ thống

### Để chạy Backend Tests:
1. ✅ MySQL Server 8.0+ đang chạy
2. ✅ Database `polysys` đã được tạo và import schema
3. ✅ Dữ liệu mẫu từ CSV đã được load (5 users, 21 videos)
4. ✅ Credentials: user=`root`, password=`12345` (hoặc cập nhật trong `persistence.xml`)

### Để chạy UI Tests:
1. ✅ Tất cả yêu cầu của Backend Tests
2. ✅ Application server đang chạy tại `http://localhost:8080/PolySys`
3. ✅ Chrome/Chromium browser đã cài đặt
4. ✅ ChromeDriver tương thích (WebDriverManager tự động tải)

## Kết quả mong đợi

### Backend Tests (90 test cases)
- **Tỷ lệ pass**: 80-90 tests (88-100%)
- **Tỷ lệ fail**: 0-10 tests
- **Lý do fail**: Tests kiểm tra constraint violations (id/email quá dài, NULL required fields, duplicate keys) - đây là expected failures

### UI Tests (15 test cases)
- **Tỷ lệ pass**: 12-15 tests (80-100%)
- **Lý do skip/fail**: Server không chạy, timing issues, element locator changes

## Kỹ thuật kiểm thử áp dụng

### 1. Phân vùng tương đương (Equivalence Partitioning)
Chia dữ liệu đầu vào thành các nhóm tương đương:
- **Valid partition**: Dữ liệu hợp lệ, đúng định dạng, trong phạm vi
- **Invalid partition**: NULL, rỗng, sai định dạng, ngoài phạm vi, duplicate

**Ví dụ**: Email field
- Valid: "user@example.com"
- Invalid format: "invalid-email"
- NULL: null
- Duplicate: email đã tồn tại

### 2. Phân tích giá trị biên (Boundary Value Analysis)
Kiểm tra các giá trị ở biên của miền hợp lệ:
- **Minimum**: Chuỗi rỗng "", 1 ký tự
- **Maximum**: Độ dài tối đa (11, 20, 50, 255 chars)
- **Beyond**: Vượt quá 1 ký tự (Max + 1)

**Ví dụ**: UserID (varchar(20))
- Test với: "", "a" (1 char), "12345678901234567890" (20 chars), "123456789012345678901" (21 chars)

### 3. Bảng quyết định (Decision Table Testing)
Kiểm tra tất cả các tổ hợp của các điều kiện:

**Ví dụ**: Create User
| Điều kiện | Test 1 | Test 2 | Test 3 | Test 4 |
|-----------|--------|--------|--------|--------|
| ID valid | ✓ | ✗ | ✓ | ✓ |
| Password valid | ✓ | ✓ | ✗ | ✓ |
| Email valid | ✓ | ✓ | ✓ | ✗ |
| Email unique | ✓ | ✓ | ✓ | ✗ |
| **Kết quả** | **Success** | **Fail** | **Fail** | **Fail** |

## Cấu trúc thư mục

```
PolySys/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── dao/              # DAO interfaces và implementations
│   │   │   ├── entity/           # Entity classes
│   │   │   └── util/             # Utility classes (XJPA)
│   │   └── resources/
│   │       ├── META-INF/
│   │       │   └── persistence.xml
│   │       └── Database Generator/
│   │           ├── db-boot.sql   # Database schema
│   │           ├── user.csv      # Sample user data
│   │           └── video.csv     # Sample video data
│   └── test/
│       └── java/
│           ├── dao/              # Backend DAO tests
│           │   ├── UserDAOTest.java
│           │   └── VideoDAOTest.java
│           └── ui/               # UI tests
│               └── VideoManagementUITest.java
├── UserDAO-TestCases.md          # User test case documentation
├── VideoDAO-TestCases.md         # Video test case documentation
├── TEST-RESULTS.md               # Test execution results
├── TEST-README.md                # This file
└── run-tests.sh                  # Test execution script
```

## Xem kết quả test

### Terminal output
Kết quả test sẽ hiển thị trực tiếp trên terminal khi chạy Maven.

### Surefire Reports
Chi tiết đầy đủ trong thư mục `target/surefire-reports/`:
- HTML reports: `target/surefire-reports/index.html`
- XML reports: `target/surefire-reports/TEST-*.xml`
- Text reports: `target/surefire-reports/*.txt`

### Markdown Documentation
- `UserDAO-TestCases.md`: Bảng test cases cho UserDAO với cột "Kết quả thực tế"
- `VideoDAO-TestCases.md`: Bảng test cases cho VideoDAO với cột "Kết quả thực tế"
- `TEST-RESULTS.md`: Tổng hợp kết quả và phân tích

## Ghi chú quan trọng

### Test Data Management
- Tests tự tạo và xóa dữ liệu test của riêng mình
- Một số tests sử dụng dữ liệu mẫu từ CSV (user01-03, V01-V21)
- Tests restore dữ liệu gốc sau khi thay đổi
- Tests được thiết kế idempotent (có thể chạy nhiều lần)

### Test Execution Order
- Tests được chạy theo priority (priority = 1, 2, 3, ...)
- Backend tests độc lập với nhau
- UI tests có thể phụ thuộc vào backend

### Troubleshooting

**Lỗi kết nối database:**
```
javax.persistence.PersistenceException: Unable to build entity manager factory
```
→ Kiểm tra MySQL đang chạy và credentials đúng

**Lỗi "Table doesn't exist":**
```
Table 'polysys.user' doesn't exist
```
→ Chạy lại script import schema

**Lỗi UI test timeout:**
```
org.openqa.selenium.TimeoutException
```
→ Kiểm tra server đang chạy và element locators đúng

## Bảo trì và cập nhật

### Khi nào cần cập nhật tests:
1. **Schema thay đổi**: Cập nhật boundary tests khi độ dài field thay đổi
2. **Method mới**: Thêm test cases tương ứng
3. **Business logic thay đổi**: Cập nhật equivalence partitions
4. **UI redesign**: Cập nhật element locators trong UI tests

### Best Practices:
- ✅ Giữ tests độc lập và có thể chạy song song
- ✅ Sử dụng meaningful test names
- ✅ Clean up test data sau mỗi test
- ✅ Document expected failures
- ✅ Maintain test documentation cùng với code

## Liên hệ và hỗ trợ

Nếu có vấn đề với test suite, vui lòng:
1. Kiểm tra `TEST-RESULTS.md` để xem expected behaviors
2. Xem logs trong `target/surefire-reports/`
3. Kiểm tra database connectivity
4. Verify application server status (cho UI tests)

---

**Lưu ý**: Test suite này được thiết kế theo tiêu chuẩn black-box testing với coverage toàn diện. Tất cả tests đã được compiled thành công và sẵn sàng để thực thi khi môi trường được cấu hình đầy đủ.
