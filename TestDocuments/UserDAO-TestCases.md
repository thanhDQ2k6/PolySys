# UserDAO Test Cases

## Tổng quan Test Cases
Tài liệu này chứa các test cases toàn diện cho việc triển khai UserDAO sử dụng các kỹ thuật kiểm thử hộp đen bao gồm:
- Phân vùng Tương đương (Equivalence Partitioning - EP)
- Phân tích Giá trị Biên (Boundary Value Analysis - BVA)
- Kiểm thử Bảng Quyết định (Decision Table Testing - DT)

## Tham chiếu Database Schema
```sql
CREATE TABLE user (
    Id       varchar(20)      NOT NULL PRIMARY KEY,
    Password varchar(50)      NOT NULL,
    FullName varchar(50)      NOT NULL,
    Email    varchar(50)      NOT NULL UNIQUE,
    Admin    bit DEFAULT b'0' NOT NULL
);
```

---

## Test Cases

| Id | Mô tả | Steps (Thao tác hành động) | Kết quả mong đợi | Kết quả thực tế | Lưu ý |
|----|-------|---------------------------|-----------------|----------------|--------|
| UC-001 | Tạo user hợp lệ với đầy đủ thông tin | 1. Tạo User object với id="testuser1", password="pass123", fullName="Test User", email="test1@example.com", admin=false<br>2. Gọi userDAO.create(user) | User được tạo thành công, trả về true | ✅ PASSED | |
| UC-002 | Tạo user với id là null | 1. Tạo User với id=null<br>2. Gọi create() | Ném ra exception hoặc trả về false | ✅ PASSED | |
| UC-003 | Tạo user với id rỗng ("") | 1. Tạo User với id=""<br>2. Gọi create() | Ném ra exception hoặc trả về false | ✅ PASSED | |
| UC-004 | Tạo user với id = 1 ký tự | 1. Tạo User với id="a"<br>2. Gọi create() | User được tạo thành công | ✅ PASSED | |
| UC-005 | Tạo user với id = 20 ký tự | 1. Tạo User với id="12345678901234567890"<br>2. Gọi create() | User được tạo thành công | ✅ PASSED | |
| UC-006 | Tạo user với id > 20 ký tự | 1. Tạo User với id="123456789012345678901"<br>2. Gọi create() | Ném ra exception do vượt quá độ dài | ✅ PASSED | |
| UC-007 | Tạo user với id đã tồn tại | 1. Tạo user với id="user01" (đã tồn tại)<br>2. Gọi create() | Ném EntityExistsException | ❌ FAILED | Phương thức catch exception và return false thay vì throw exception |
| UC-008 | Tạo user với password null | 1. Tạo User với password=null<br>2. Gọi create() | Ném ra exception do NOT NULL constraint | ❌ FAILED | Phương thức catch exception và return false thay vì throw exception |
| UC-009 | Tạo user với password rỗng | 1. Tạo User với password=""<br>2. Gọi create() | User được tạo (không validate) hoặc exception | ✅ PASSED | |
| UC-010 | Tạo user với password = 50 ký tự | 1. Tạo User với password có 50 ký tự<br>2. Gọi create() | User được tạo thành công | ✅ PASSED | |
| UC-011 | Tạo user với password > 50 ký tự | 1. Tạo User với password có 51 ký tự<br>2. Gọi create() | Ném exception do vượt quá độ dài | ✅ PASSED | |
| UC-012 | Tạo user với fullName null | 1. Tạo User với fullName=null<br>2. Gọi create() | Ném ra exception do NOT NULL constraint | ❌ FAILED | Phương thức catch exception và return false thay vì throw exception |
| UC-013 | Tạo user với fullName rỗng | 1. Tạo User với fullName=""<br>2. Gọi create() | User được tạo hoặc exception | ✅ PASSED | |
| UC-014 | Tạo user với fullName = 50 ký tự | 1. Tạo User với fullName có 50 ký tự<br>2. Gọi create() | User được tạo thành công | ✅ PASSED | |
| UC-015 | Tạo user với fullName > 50 ký tự | 1. Tạo User với fullName có 51 ký tự<br>2. Gọi create() | Ném exception do vượt quá độ dài | ✅ PASSED | |
| UC-016 | Tạo user với email null | 1. Tạo User với email=null<br>2. Gọi create() | Ném ra exception do NOT NULL constraint | ❌ FAILED | Phương thức catch exception và return false thay vì throw exception |
| UC-017 | Tạo user với email không hợp lệ | 1. Tạo User với email="invalid-email"<br>2. Gọi create() | User được tạo (không validate format) hoặc exception | ✅ PASSED | |
| UC-018 | Tạo user với email hợp lệ | 1. Tạo User với email="valid@email.com"<br>2. Gọi create() | User được tạo thành công | ✅ PASSED | |
| UC-019 | Tạo user với email = 50 ký tự | 1. Tạo User với email có 50 ký tự<br>2. Gọi create() | User được tạo thành công | ✅ PASSED | |
| UC-020 | Tạo user với email > 50 ký tự | 1. Tạo User với email có 51 ký tự<br>2. Gọi create() | Ném exception do vượt quá độ dài | ❌ FAILED | Phương thức catch exception và return false thay vì throw exception |
| UC-021 | Tạo user với email đã tồn tại | 1. Tạo user với email="admin@polysys.com" (đã tồn tại)<br>2. Gọi create() | Ném exception do UNIQUE constraint | ✅ PASSED | |
| UC-022 | Tạo user với admin=true | 1. Tạo User với admin=true<br>2. Gọi create() | User được tạo với quyền admin | ✅ PASSED | |
| UC-023 | Tạo user với admin=false | 1. Tạo User với admin=false<br>2. Gọi create() | User được tạo với quyền thường | ✅ PASSED | |
| UC-024 | Tìm user theo id tồn tại | 1. Gọi userDAO.findById("user01")<br>2. Kiểm tra result | Trả về Optional chứa user | ✅ PASSED | |
| UC-025 | Tìm user theo id không tồn tại | 1. Gọi userDAO.findById("nonexistent")<br>2. Kiểm tra result | Trả về Optional.empty() | ✅ PASSED | |
| UC-026 | Tìm user với id null | 1. Gọi userDAO.findById(null)<br>2. Kiểm tra result | Trả về Optional.empty() hoặc exception | ✅ PASSED | |
| UC-027 | Lấy tất cả users | 1. Gọi userDAO.findAll()<br>2. Kiểm tra kích thước list | Trả về danh sách users (ít nhất 5 users từ CSV) | ✅ PASSED | |
| UC-028 | Cập nhật user hợp lệ | 1. Lấy user "user01"<br>2. Thay đổi fullName<br>3. Gọi update() | User được cập nhật thành công | ✅ PASSED | |
| UC-029 | Cập nhật user không tồn tại | 1. Tạo User với id không tồn tại<br>2. Gọi update() | Ném EntityNotFoundException | ✅ PASSED | |
| UC-030 | Cập nhật email thành email đã tồn tại | 1. Lấy user "user01"<br>2. Đổi email thành email của "user02"<br>3. Gọi update() | Ném exception do UNIQUE constraint | ✅ PASSED | |
| UC-031 | Cập nhật password của user | 1. Lấy user "user01"<br>2. Đổi password<br>3. Gọi update()<br>4. Tìm lại user | Password được cập nhật thành công | ✅ PASSED | |
| UC-032 | Cập nhật admin role | 1. Lấy user "user01"<br>2. Đổi admin từ false sang true<br>3. Gọi update() | Admin role được cập nhật | ✅ PASSED | |
| UC-033 | Xóa user tồn tại | 1. Tạo user mới<br>2. Gọi delete(id)<br>3. Tìm lại user | User bị xóa, findById trả về empty | ✅ PASSED | |
| UC-034 | Xóa user không tồn tại | 1. Gọi delete("nonexistent") | Ném EntityNotFoundException | ✅ PASSED | |
| UC-035 | Xóa user với id null | 1. Gọi delete(null) | Ném exception | ✅ PASSED | |
| UC-036 | Tìm user theo email tồn tại | 1. Gọi userDAO.findByEmail("admin@polysys.com")<br>2. Kiểm tra result | Trả về Optional chứa user với email đó | ✅ PASSED | |
| UC-037 | Tìm user theo email không tồn tại | 1. Gọi userDAO.findByEmail("nonexistent@test.com")<br>2. Kiểm tra result | Trả về Optional.empty() | ✅ PASSED | |
| UC-038 | Kiểm tra email tồn tại | 1. Gọi userDAO.existsByEmail("admin@polysys.com")<br>2. Kiểm tra result | Trả về true | ✅ PASSED | |
| UC-039 | Kiểm tra email không tồn tại | 1. Gọi userDAO.existsByEmail("nonexistent@test.com")<br>2. Kiểm tra result | Trả về false | ✅ PASSED | |
| UC-040 | Đếm số lượng users | 1. Gọi userDAO.count()<br>2. Kiểm tra result | Trả về số lượng users trong DB (ít nhất 5) | ✅ PASSED | |

---

## Decision Table - Create User

| Condition | Test 1 | Test 2 | Test 3 | Test 4 | Test 5 |
|-----------|--------|--------|--------|--------|--------|
| Id valid | Y | N | Y | Y | Y |
| Password valid | Y | Y | N | Y | Y |
| Email valid | Y | Y | Y | N | Y |
| Email unique | Y | Y | Y | Y | N |
| **Action** | **Success** | **Fail** | **Fail** | **Fail** | **Fail** |

## Notes
- Test cases cover all CRUD operations (Create, Read, Update, Delete)
- Boundary value analysis focuses on string length constraints
- Equivalence partitioning covers valid/invalid inputs
- Decision table ensures all combinations of constraints are tested
