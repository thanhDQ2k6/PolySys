# Kế hoạch Kiểm thử Hệ thống PolySys

## 1. Giới thiệu

### 1.1 Mục đích
Tài liệu kế hoạch kiểm thử này phác thảo chiến lược kiểm thử toàn diện cho hệ thống quản lý video PolySys. Nó xác định phạm vi, cách tiếp cận, tài nguyên và lịch trình của các hoạt động kiểm thử.

### 1.2 Tổng quan dự án
**Tên dự án**: PolySys - Hệ thống Quản lý Video
**Phiên bản**: 1.0-SNAPSHOT
**Technology Stack**: 
- Backend: Java 11, Hibernate JPA, MySQL
- Frontend: JSP, JSTL, JavaScript
- Testing: TestNG, Selenium WebDriver
- Build: Maven
- CI/CD: GitHub Actions

### 1.3 Phạm vi tài liệu
Tài liệu này bao gồm:
- Kiểm thử đơn vị và tích hợp cho tầng truy cập dữ liệu (DAO)
- Kiểm thử giao diện người dùng (UI) sử dụng Selenium
- Tài liệu test case và quy trình thực thi
- Tích hợp CI/CD và kiểm thử tự động

## 2. Chiến lược Kiểm thử

### 2.1 Các cấp độ Kiểm thử

#### 2.1.1 Kiểm thử Đơn vị (Unit Testing)
- **Phạm vi**: Các phương thức DAO riêng lẻ
- **Framework**: TestNG
- **Độ phủ**: UserDAO, VideoDAO, FavoriteDAO, ShareDAO
- **Tổng số Tests**: 133 test cases

#### 2.1.2 Kiểm thử Tích hợp (Integration Testing)
- **Phạm vi**: Tích hợp cơ sở dữ liệu, quan hệ thực thể
- **Framework**: TestNG với JPA/Hibernate
- **Độ phủ**: Các thao tác CRUD, truy vấn tùy chỉnh, giao dịch

#### 2.1.3 Kiểm thử UI (UI Testing)
- **Phạm vi**: Giao diện người dùng và quy trình làm việc
- **Framework**: Selenium WebDriver + TestNG
- **Độ phủ**: Đăng nhập, Duyệt Video, Quản lý Admin
- **Tổng số Tests**: 40 test cases

### 2.2 Các loại Kiểm thử

#### 2.2.1 Kiểm thử Chức năng (Functional Testing)
- Xác minh tất cả các tính năng hoạt động như được chỉ định
- Kiểm thử các thao tác CRUD cho tất cả các thực thể
- Xác thực logic nghiệp vụ và quy trình làm việc

#### 2.2.2 Kỹ thuật Kiểm thử Hộp đen (Black-Box Testing)
- **Phân vùng Tương đương**: Các lớp dữ liệu hợp lệ/không hợp lệ
- **Phân tích Giá trị Biên**: Độ dài trường min/max
- **Kiểm thử Bảng Quyết định**: Tổ hợp các ràng buộc

#### 2.2.3 Kiểm thử Tiêu cực (Negative Testing)
- Kiểm thử với giá trị null
- Kiểm thử với dữ liệu không hợp lệ (quá dài, sai định dạng)
- Kiểm thử vi phạm khóa trùng lặp
- Kiểm thử vi phạm ràng buộc

#### 2.2.4 Kiểm thử Bảo mật (Security Testing)
- Ẩn mật khẩu trong UI
- Quản lý phiên
- Xác thực và phân quyền
- Ngăn chặn SQL injection (thông qua JPA)

#### 2.2.5 Kiểm thử Khả năng Sử dụng (Usability Testing)
- Thiết kế responsive (desktop, tablet, mobile)
- Luồng điều hướng
- Độ rõ ràng của thông báo lỗi

## 3. Độ phủ Kiểm thử

### 3.1 Kiểm thử Backend DAO

| Thành phần | Test Cases | Độ phủ |
|-----------|-----------|----------|
| UserDAO | 40 | Create (23), Read (5), Update (5), Delete (3), Custom (4) |
| VideoDAO | 50 | Create (28), Read (4), Update (5), Delete (3), Custom (10) |
| FavoriteDAO | 20 | Create (5), Read (6), Delete (2), Count (2), Advanced (5) |
| ShareDAO | 23 | Create (8), Read (5), Delete (2), Count (2), Advanced (6) |
| **Tổng** | **133** | **CRUD đầy đủ + Logic nghiệp vụ** |

### 3.2 Kiểm thử UI

| Thành phần | Test Cases | Độ phủ |
|-----------|-----------|----------|
| Login UI | 10 | Tải trang, xác thực, validation, session |
| Video Browsing UI | 15 | Hiển thị, tìm kiếm, phân trang, tương tác |
| Video Management UI | 15 | CRUD Admin, validation form, điều hướng |
| **Tổng** | **40** | **Quy trình người dùng đầy đủ** |

### 3.3 Độ phủ Xác thực Trường

#### Thực thể User
- **id**: VARCHAR(20), NOT NULL, UNIQUE
  - Tests: null, rỗng, 1 ký tự, 20 ký tự, 21 ký tự, trùng lặp
- **password**: VARCHAR(50), NOT NULL
  - Tests: null, rỗng, 50 ký tự, 51 ký tự
- **fullName**: VARCHAR(50), NOT NULL
  - Tests: null, rỗng, 50 ký tự, 51 ký tự
- **email**: VARCHAR(50), NOT NULL, UNIQUE
  - Tests: null, định dạng không hợp lệ, hợp lệ, 50 ký tự, 51 ký tự, trùng lặp
- **admin**: BOOLEAN, NOT NULL
  - Tests: true, false

#### Thực thể Video
- **id**: VARCHAR(11), NOT NULL, UNIQUE
  - Tests: null, rỗng, <11, 11 ký tự, >11, trùng lặp
- **title**: VARCHAR(255), NOT NULL
  - Tests: null, rỗng, 1 ký tự, 255 ký tự, 256 ký tự
- **posterUrl**: VARCHAR(255), NOT NULL
  - Tests: null, rỗng, 255 ký tự, 256 ký tự
- **description**: LONGTEXT, nullable
  - Tests: null, rỗng, rất dài (10000 ký tự)
- **active**: BOOLEAN, NOT NULL
  - Tests: true, false
- **views**: INT, NOT NULL
  - Tests: 0, âm, số lớn
- **link**: VARCHAR(255), NOT NULL
  - Tests: null, rỗng, 255 ký tự, 256 ký tự

#### Thực thể Favorite
- **user**: Foreign Key, NOT NULL
- **video**: Foreign Key, NOT NULL
- **likeDate**: DATE, NOT NULL
- **Ràng buộc Unique**: (user, video)
  - Tests: trường null, tổ hợp trùng lặp, ngày quá khứ/tương lai

#### Thực thể Share
- **user**: Foreign Key, NOT NULL
- **video**: Foreign Key, NOT NULL
- **emails**: VARCHAR(50), NOT NULL
  - Tests: null, rỗng, 50 ký tự, 51 ký tự, nhiều emails
- **shareDate**: DATE, NOT NULL
  - Tests: null, ngày quá khứ/tương lai

## 4. Môi trường Kiểm thử

### 4.1 Yêu cầu Phần cứng
- **Development**: Máy development tiêu chuẩn
- **CI/CD**: GitHub Actions Ubuntu runner

### 4.2 Yêu cầu Phần mềm
- **JDK**: 11 hoặc cao hơn
- **MySQL**: 8.0 hoặc cao hơn
- **Maven**: 3.6+
- **Chrome/Chromium**: Phiên bản mới nhất
- **ChromeDriver**: Tương thích với phiên bản trình duyệt

### 4.3 Dữ liệu Kiểm thử
- **Users**: 5 test users (user01-user05, admin)
- **Videos**: 21 test videos (V01-V21)
- **Favorites**: Dữ liệu favorite mẫu
- **Shares**: Dữ liệu share mẫu

### 4.4 Thiết lập Cơ sở dữ liệu
```sql
Database: polysys
Character Set: utf8mb4
Collation: utf8mb4_unicode_ci
```

## 5. Thực thi Kiểm thử

### 5.1 Chiến lược Thực thi Kiểm thử
1. **Local Development**:
   - Chạy các test classes riêng lẻ trong quá trình phát triển
   - Chạy full test suite trước khi commit
   
2. **Continuous Integration**:
   - Thực thi test tự động trên pull requests
   - MySQL service container cho database tests
   - Headless browser cho UI tests

### 5.2 Lệnh Thực thi Kiểm thử

```bash
# Chạy tất cả tests
mvn test

# Chạy test class cụ thể
mvn test -Dtest=UserDAOTest
mvn test -Dtest=VideoDAOTest
mvn test -Dtest=FavoriteDAOTest
mvn test -Dtest=ShareDAOTest
mvn test -Dtest=LoginUITest
mvn test -Dtest=VideoBrowsingUITest
mvn test -Dtest=VideoManagementUITest

# Chỉ chạy DAO tests
mvn test -Dtest=*DAOTest

# Chỉ chạy UI tests
mvn test -Dtest=*UITest

# Sử dụng shell script (khuyến nghị)
./run-tests.sh          # Tất cả tests
./run-tests.sh backend  # Chỉ backend tests
./run-tests.sh ui       # Chỉ UI tests
```

### 5.3 CI/CD Pipeline
- **Kích hoạt**: Pull request đến main branch
- **Các bước**:
  1. Checkout code
  2. Setup JDK 11
  3. Khởi động MySQL service
  4. Load database schema và seed data
  5. Build ứng dụng (bỏ qua tests)
  6. Chạy DAO tests
  7. Upload báo cáo test

## 6. Sản phẩm Bàn giao Kiểm thử

### 6.1 Tài liệu Kiểm thử
- ✅ MasterDocument.md - Tài liệu kiểm thử tổng hợp (tổng quan và hướng dẫn)
- ✅ TestTypes.md - Danh sách đầy đủ tất cả các loại kiểm thử được sử dụng
- ✅ TestPlan.md - Kế hoạch kiểm thử chi tiết (tài liệu này)
- ✅ TestCases.md - Kết quả thực thi kiểm thử và phân tích
- ✅ UserDAO-TestCases.md - Test cases User DAO
- ✅ VideoDAO-TestCases.md - Test cases Video DAO
- ✅ FavoriteDAO-TestCases.md - Test cases Favorite DAO
- ✅ ShareDAO-TestCases.md - Test cases Share DAO
- ✅ LoginUI-TestCases.md - Test cases Login UI
- ✅ VideoBrowsing-TestCases.md - Test cases Video browsing UI

### 6.2 Mã Kiểm thử
- ✅ src/test/java/dao/UserDAOTest.java
- ✅ src/test/java/dao/VideoDAOTest.java
- ✅ src/test/java/dao/FavoriteDAOTest.java
- ✅ src/test/java/dao/ShareDAOTest.java
- ✅ src/test/java/ui/LoginUITest.java
- ✅ src/test/java/ui/VideoBrowsingUITest.java
- ✅ src/test/java/ui/VideoManagementUITest.java

### 6.3 Báo cáo Kiểm thử
- Maven Surefire Reports (HTML/XML/TXT)
- TestNG Reports (HTML)
- CI/CD Artifacts (upload lên GitHub Actions)

## 7. Tiêu chí Vào và Ra

### 7.1 Tiêu chí Vào
- ✅ Database schema đã được tạo
- ✅ Dữ liệu test đã được tải
- ✅ Ứng dụng build thành công
- ✅ Môi trường test đã được cấu hình
- ✅ Tất cả mã test biên dịch thành công

### 7.2 Tiêu chí Ra
- ✅ Tất cả test cases đã được thực thi
- ✅ Tỷ lệ pass ≥ 85% cho backend tests
- ✅ Tỷ lệ pass ≥ 75% cho UI tests
- ✅ Tất cả lỗi nghiêm trọng đã được giải quyết
- ✅ Báo cáo test đã được tạo

## 8. Quản lý Lỗi

### 8.1 Mức độ Nghiêm trọng của Lỗi
- **Critical**: Crash hệ thống, mất dữ liệu, vi phạm bảo mật
- **High**: Tính năng chính không hoạt động
- **Medium**: Tính năng hoạt động có vấn đề
- **Low**: Vấn đề giao diện, lỗi nhỏ

### 8.2 Các Test Failures Dự kiến
Một số tests được thiết kế để fail khi kiểm thử vi phạm ràng buộc:
- Tests với độ dài trường > max (dự kiến: Exception)
- Tests với NULL trên trường NOT NULL (dự kiến: Exception)
- Tests với khóa unique trùng lặp (dự kiến: EntityExistsException)

Đây là **failures dự kiến** để xác thực các ràng buộc hệ thống.

## 9. Quản lý Rủi ro

### 9.1 Rủi ro Kiểm thử

| Rủi ro | Tác động | Xác suất | Giảm thiểu |
|------|--------|-------------|------------|
| Database không khả dụng | Cao | Thấp | Tự động setup trong CI/CD |
| Thay đổi phần tử UI | Trung bình | Trung bình | Element locators linh hoạt |
| Tương thích trình duyệt | Trung bình | Thấp | Sử dụng ChromeDriver chuẩn |
| Xung đột dữ liệu test | Thấp | Trung bình | Dữ liệu test độc lập |
| CI/CD timeout | Trung bình | Thấp | Tối ưu hóa thực thi test |

## 10. Lịch trình Kiểm thử

### 10.1 Các giai đoạn Kiểm thử
1. **Unit Testing**: Liên tục trong quá trình phát triển
2. **Integration Testing**: Sau khi triển khai DAO
3. **UI Testing**: Sau khi triển khai frontend
4. **Regression Testing**: Trước mỗi lần phát hành
5. **CI/CD Testing**: Trên mỗi pull request

## 11. Trách nhiệm

### 11.1 Vai trò Nhóm Kiểm thử
- **Test Lead**: Chiến lược và điều phối kiểm thử tổng thể
- **Backend Tester**: Phát triển và thực thi DAO tests
- **Frontend Tester**: Phát triển và thực thi UI tests
- **DevOps**: Thiết lập và bảo trì CI/CD pipeline

## 12. Công cụ và Công nghệ

### 12.1 Testing Frameworks
- **TestNG 7.8.0**: Framework thực thi test
- **Selenium 4.36.0**: Tự động hóa Web UI
- **JUnit 5.10.2**: Framework kiểm thử thay thế
- **Maven Surefire 3.2.5**: Plugin chạy test

### 12.2 Công cụ Phát triển
- **Maven**: Tự động hóa build
- **Git**: Kiểm soát phiên bản
- **GitHub Actions**: CI/CD
- **IntelliJ IDEA / Eclipse**: IDE

## 13. Kết luận

Kế hoạch kiểm thử toàn diện này đảm bảo độ phủ kiểm thử kỹ lưỡng cho hệ thống PolySys. Với tổng cộng 173 test cases bao phủ chức năng backend và frontend, hệ thống được kiểm thử tốt về độ tin cậy, bảo mật và khả năng sử dụng.

### 13.1 Tóm tắt Độ phủ Kiểm thử
- ✅ **Backend**: 133 tests bao phủ tất cả các thao tác DAO
- ✅ **Frontend**: 40 tests bao phủ quy trình người dùng
- ✅ **Kỹ thuật**: Hộp đen, giá trị biên, tương đương, bảng quyết định
- ✅ **Tự động hóa**: TestNG + Selenium + CI/CD
- ✅ **Tài liệu**: Tài liệu test case toàn diện

---

**Phiên bản Tài liệu**: 1.0
**Cập nhật lần cuối**: 2025-10-20
**Trạng thái**: Đang hoạt động
