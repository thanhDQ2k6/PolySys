# Các loại kiểm thử được sử dụng trong dự án PolySys

## Tổng quan
Tài liệu này cung cấp danh sách đầy đủ tất cả các loại kiểm thử và kỹ thuật kiểm thử được áp dụng trong quy trình kiểm thử hệ thống quản lý video PolySys.

## Các cấp độ kiểm thử

### 1. Kiểm thử đơn vị (Unit Testing)
**Mô tả**: Kiểm thử các thành phần hoặc phương thức riêng lẻ một cách độc lập.

**Phạm vi**:
- Các phương thức DAO (Create, Read, Update, Delete)
- Xác thực logic nghiệp vụ
- Các thao tác truy cập dữ liệu

**Công cụ sử dụng**:
- TestNG 7.8.0
- JUnit 5.10.2

**Số lượng test**: 133 unit tests backend

---

### 2. Kiểm thử tích hợp (Integration Testing)
**Mô tả**: Kiểm thử sự tương tác giữa các thành phần và hệ thống bên ngoài.

**Phạm vi**:
- Tích hợp cơ sở dữ liệu (MySQL + JPA/Hibernate)
- Quan hệ giữa các thực thể (User-Favorite, User-Share, Video-Favorite, Video-Share)
- Quản lý giao dịch
- Các thao tác lưu trữ

**Công cụ sử dụng**:
- TestNG với JPA/Hibernate
- MySQL 8.0

**Số lượng test**: Bao gồm trong 133 tests backend

---

### 3. Kiểm thử giao diện người dùng (UI Testing)
**Mô tả**: Kiểm thử giao diện người dùng và quy trình làm việc end-to-end.

**Phạm vi**:
- Chức năng đăng nhập
- Duyệt và tìm kiếm video
- Quản lý video của admin
- Xác thực form
- Điều hướng và tương tác

**Công cụ sử dụng**:
- Selenium WebDriver 4.36.0
- TestNG
- ChromeDriver

**Số lượng test**: 40 UI tests

---

## Các kỹ thuật kiểm thử

### 1. Kiểm thử hộp đen (Black-Box Testing)
**Mô tả**: Kiểm thử dựa trên yêu cầu mà không cần biết về cài đặt nội bộ.

**Áp dụng cho**:
- Tất cả các thao tác DAO
- Quy trình làm việc UI
- Xác thực logic nghiệp vụ

**Kỹ thuật sử dụng**:
- Phân vùng tương đương
- Phân tích giá trị biên
- Kiểm thử bảng quyết định

---

### 2. Phân vùng tương đương (Equivalence Partitioning - EP)
**Mô tả**: Chia dữ liệu đầu vào thành các phân vùng hợp lệ và không hợp lệ.

**Ví dụ**:
- **Phân vùng hợp lệ**: Kiểu dữ liệu đúng, trong phạm vi ràng buộc, không rỗng
- **Phân vùng không hợp lệ**: Giá trị NULL, chuỗi rỗng, ngoài phạm vi, trùng lặp

**Áp dụng cho**:
- Các trường User: id, password, fullName, email, admin
- Các trường Video: id, title, posterUrl, description, active, views, link
- Các trường Favorite: user, video, likeDate
- Các trường Share: user, video, emails, shareDate

---

### 3. Phân tích giá trị biên (Boundary Value Analysis - BVA)
**Mô tả**: Kiểm thử tại các ranh giới của miền đầu vào.

**Ví dụ về giá trị biên**:
- **Thực thể User**:
  - id: 0, 1, 20, 21 ký tự (max: 20)
  - password: 0, 1, 50, 51 ký tự (max: 50)
  - fullName: 0, 1, 50, 51 ký tự (max: 50)
  - email: 0, 1, 50, 51 ký tự (max: 50)

- **Thực thể Video**:
  - id: 0, 1, 11, 12 ký tự (cố định: 11)
  - title: 0, 1, 255, 256 ký tự (max: 255)
  - posterUrl: 0, 1, 255, 256 ký tự (max: 255)
  - link: 0, 1, 255, 256 ký tự (max: 255)
  - description: LONGTEXT (kiểm thử với 10000+ ký tự)

**Áp dụng cho**: Tất cả các trường có ràng buộc trong tất cả các thực thể

---

### 4. Kiểm thử bảng quyết định (Decision Table Testing - DT)
**Mô tả**: Kiểm thử tất cả các tổ hợp của các điều kiện và kết quả của chúng.

**Ví dụ cho việc tạo User**:
| Điều kiện | Test 1 | Test 2 | Test 3 | Test 4 |
|-----------|--------|--------|--------|--------|
| ID hợp lệ | ✓ | ✗ | ✓ | ✓ |
| Password hợp lệ | ✓ | ✓ | ✗ | ✓ |
| Email hợp lệ | ✓ | ✓ | ✓ | ✗ |
| Email duy nhất | ✓ | ✓ | ✓ | ✗ |
| **Kết quả** | **Thành công** | **Thất bại** | **Thất bại** | **Thất bại** |

**Áp dụng cho**: Các kịch bản xác thực ràng buộc phức tạp

---

### 5. Kiểm thử tích cực (Positive Testing)
**Mô tả**: Kiểm thử với dữ liệu đầu vào hợp lệ để xác minh hành vi mong đợi.

**Ví dụ**:
- Tạo user với tất cả các trường hợp lệ
- Cập nhật video với dữ liệu hợp lệ
- Xóa favorite tồn tại
- Tìm kiếm với từ khóa hợp lệ

**Số lượng test**: ~120 test cases tích cực

---

### 6. Kiểm thử tiêu cực (Negative Testing)
**Mô tả**: Kiểm thử với dữ liệu đầu vào không hợp lệ để xác minh xử lý lỗi.

**Ví dụ**:
- Tạo user với các trường bắt buộc NULL
- Cập nhật với ID không tồn tại
- Xóa với ID không hợp lệ
- Chèn khóa duy nhất trùng lặp
- Vượt quá ràng buộc độ dài trường

**Số lượng test**: ~53 test cases tiêu cực

---

### 7. Kiểm thử chức năng (Functional Testing)
**Mô tả**: Xác minh rằng các tính năng hoạt động theo yêu cầu.

**Phạm vi**:
- Các thao tác CRUD cho tất cả các thực thể
- Các phương thức DAO tùy chỉnh (findByEmail, findByActiveTrue, v.v.)
- Chức năng tìm kiếm và lọc
- Đếm lượt xem
- Quản lý favorite
- Chức năng chia sẻ

**Số lượng test**: Tất cả 173 tests bao gồm kiểm thử chức năng

---

### 8. Kiểm thử bảo mật (Security Testing)
**Mô tả**: Kiểm thử các khía cạnh bảo mật của ứng dụng.

**Phạm vi**:
- Ẩn mật khẩu trong form đăng nhập
- Quản lý phiên
- Xác thực đăng nhập
- Ngăn chặn SQL injection (thông qua truy vấn tham số hóa JPA)
- Kiểm soát truy cập (admin vs người dùng thường)

**Số lượng test**: 5 UI tests tập trung vào bảo mật

---

### 9. Kiểm thử khả năng sử dụng (Usability Testing)
**Mô tả**: Kiểm thử trải nghiệm người dùng và chất lượng giao diện.

**Phạm vi**:
- Thiết kế responsive (desktop, tablet, mobile)
- Luồng điều hướng
- Thông báo xác thực form
- Độ rõ ràng của thông báo lỗi
- Chức năng nút và liên kết

**Số lượng test**: 8 UI tests tập trung vào khả năng sử dụng

---

### 10. Kiểm thử hồi quy (Regression Testing)
**Mô tả**: Chạy lại các tests để đảm bảo các thay đổi mới không làm hỏng chức năng hiện có.

**Triển khai**:
- Thực thi toàn bộ test suite trong CI/CD
- Tự động hóa trên mỗi pull request
- Quy trình GitHub Actions

**Phạm vi**: Tất cả 173 tests chạy tự động

---

### 11. Kiểm thử khói (Smoke Testing)
**Mô tả**: Các tests nhanh để xác minh chức năng cơ bản.

**Phạm vi**:
- Kết nối cơ sở dữ liệu
- Trạng thái application server
- Các thao tác CRUD cơ bản
- Tải trang UI cơ bản

**Triển khai**: Một vài tests đầu tiên trong mỗi test class

---

## Tự động hóa kiểm thử

### Kiểm thử tự động (Automated Testing)
**Framework**: TestNG + Selenium WebDriver + Maven

**Phạm vi**: 
- 100% backend tests (133 tests)
- 100% UI tests (40 tests)

**Thực thi**:
- Local: `mvn test` hoặc `./run-tests.sh`
- CI/CD: GitHub Actions trên pull requests

---

### Tích hợp liên tục (CI/CD)
**Nền tảng**: GitHub Actions

**Quy trình**:
1. Kích hoạt trên pull request
2. Thiết lập JDK 11
3. Khởi động dịch vụ MySQL
4. Load database schema và seed data
5. Build ứng dụng
6. Chạy tất cả tests
7. Tạo và upload báo cáo test

---

## Quản lý dữ liệu kiểm thử

### Các loại dữ liệu kiểm thử
1. **Seed Data**: Tải sẵn từ các file CSV (5 users, 21 videos)
2. **Generated Data**: Được tạo trong quá trình thực thi test
3. **Boundary Data**: Các trường hợp biên (độ dài min/max, ký tự đặc biệt)
4. **Invalid Data**: NULL, rỗng, giá trị ngoài phạm vi

### Dọn dẹp dữ liệu
- Tests tự tạo và dọn dẹp dữ liệu của riêng mình
- Một số tests khôi phục lại seed data đã sửa đổi
- Tests được thiết kế để idempotent (có thể lặp lại)

---

## Báo cáo kiểm thử

### Các loại báo cáo
1. **Maven Surefire Reports**: Định dạng HTML, XML, TXT
2. **TestNG Reports**: HTML với kết quả chi tiết
3. **CI/CD Artifacts**: Upload lên GitHub Actions
4. **Markdown Documentation**: Bảng test cases với kết quả thực tế

### Các chỉ số theo dõi
- Tổng số tests đã thực thi
- Số lượng Pass/Fail
- Thời gian thực thi
- Phần trăm độ phủ
- Số lượng lỗi

---

## Tóm tắt

### Tổng quan độ phủ kiểm thử
- **Test Cases**: 173 tổng cộng (133 backend + 40 UI)
- **Loại kiểm thử**: 11 cách tiếp cận kiểm thử khác nhau
- **Kỹ thuật**: 7 kỹ thuật kiểm thử (EP, BVA, DT, v.v.)
- **Tự động hóa**: 100% tự động
- **CI/CD**: Tích hợp đầy đủ với GitHub Actions

### Triết lý kiểm thử
Bộ test suite PolySys tuân theo các best practices của ngành với độ phủ toàn diện sử dụng nhiều kỹ thuật kiểm thử để đảm bảo chất lượng, độ tin cậy và khả năng bảo trì của phần mềm.

---

**Phiên bản tài liệu**: 1.0  
**Cập nhật lần cuối**: 2025-10-20  
**Trạng thái**: Đang hoạt động
