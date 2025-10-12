# UserDAO Test Cases

## Test Case Overview
This document contains comprehensive test cases for the UserDAO implementation using black-box testing techniques including:
- Equivalence Partitioning (EP)
- Boundary Value Analysis (BVA)
- Decision Table Testing (DT)

## Database Schema Reference
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
| UC-001 | Tạo user hợp lệ với đầy đủ thông tin | 1. Tạo User object với id="testuser1", password="pass123", fullName="Test User", email="test1@example.com", admin=false<br>2. Gọi userDAO.create(user) | User được tạo thành công, trả về true | | EP: Valid input partition |
| UC-002 | Tạo user với id là null | 1. Tạo User với id=null<br>2. Gọi create() | Ném ra exception hoặc trả về false | | EP: Invalid id partition |
| UC-003 | Tạo user với id rỗng ("") | 1. Tạo User với id=""<br>2. Gọi create() | Ném ra exception hoặc trả về false | | BVA: Minimum boundary |
| UC-004 | Tạo user với id = 1 ký tự | 1. Tạo User với id="a"<br>2. Gọi create() | User được tạo thành công | | BVA: Minimum valid |
| UC-005 | Tạo user với id = 20 ký tự | 1. Tạo User với id="12345678901234567890"<br>2. Gọi create() | User được tạo thành công | | BVA: Maximum boundary |
| UC-006 | Tạo user với id > 20 ký tự | 1. Tạo User với id="123456789012345678901"<br>2. Gọi create() | Ném ra exception do vượt quá độ dài | | BVA: Above maximum |
| UC-007 | Tạo user với id đã tồn tại | 1. Tạo user với id="user01" (đã tồn tại)<br>2. Gọi create() | Ném EntityExistsException | | EP: Duplicate key |
| UC-008 | Tạo user với password null | 1. Tạo User với password=null<br>2. Gọi create() | Ném ra exception do NOT NULL constraint | | EP: Invalid password |
| UC-009 | Tạo user với password rỗng | 1. Tạo User với password=""<br>2. Gọi create() | User được tạo (không validate) hoặc exception | | BVA: Minimum password |
| UC-010 | Tạo user với password = 50 ký tự | 1. Tạo User với password có 50 ký tự<br>2. Gọi create() | User được tạo thành công | | BVA: Maximum boundary |
| UC-011 | Tạo user với password > 50 ký tự | 1. Tạo User với password có 51 ký tự<br>2. Gọi create() | Ném exception do vượt quá độ dài | | BVA: Above maximum |
| UC-012 | Tạo user với fullName null | 1. Tạo User với fullName=null<br>2. Gọi create() | Ném ra exception do NOT NULL constraint | | EP: Invalid fullName |
| UC-013 | Tạo user với fullName rỗng | 1. Tạo User với fullName=""<br>2. Gọi create() | User được tạo hoặc exception | | BVA: Minimum fullName |
| UC-014 | Tạo user với fullName = 50 ký tự | 1. Tạo User với fullName có 50 ký tự<br>2. Gọi create() | User được tạo thành công | | BVA: Maximum boundary |
| UC-015 | Tạo user với fullName > 50 ký tự | 1. Tạo User với fullName có 51 ký tự<br>2. Gọi create() | Ném exception do vượt quá độ dài | | BVA: Above maximum |
| UC-016 | Tạo user với email null | 1. Tạo User với email=null<br>2. Gọi create() | Ném ra exception do NOT NULL constraint | | EP: Invalid email |
| UC-017 | Tạo user với email không hợp lệ | 1. Tạo User với email="invalid-email"<br>2. Gọi create() | User được tạo (không validate format) hoặc exception | | EP: Invalid email format |
| UC-018 | Tạo user với email hợp lệ | 1. Tạo User với email="valid@email.com"<br>2. Gọi create() | User được tạo thành công | | EP: Valid email |
| UC-019 | Tạo user với email = 50 ký tự | 1. Tạo User với email có 50 ký tự<br>2. Gọi create() | User được tạo thành công | | BVA: Maximum boundary |
| UC-020 | Tạo user với email > 50 ký tự | 1. Tạo User với email có 51 ký tự<br>2. Gọi create() | Ném exception do vượt quá độ dài | | BVA: Above maximum |
| UC-021 | Tạo user với email đã tồn tại | 1. Tạo user với email="admin@polysys.com" (đã tồn tại)<br>2. Gọi create() | Ném exception do UNIQUE constraint | | EP: Duplicate email |
| UC-022 | Tạo user với admin=true | 1. Tạo User với admin=true<br>2. Gọi create() | User được tạo với quyền admin | | EP: Admin role |
| UC-023 | Tạo user với admin=false | 1. Tạo User với admin=false<br>2. Gọi create() | User được tạo với quyền thường | | EP: Regular user |
| UC-024 | Tìm user theo id tồn tại | 1. Gọi userDAO.findById("user01")<br>2. Kiểm tra result | Trả về Optional chứa user | | EP: Valid find |
| UC-025 | Tìm user theo id không tồn tại | 1. Gọi userDAO.findById("nonexistent")<br>2. Kiểm tra result | Trả về Optional.empty() | | EP: Invalid find |
| UC-026 | Tìm user với id null | 1. Gọi userDAO.findById(null)<br>2. Kiểm tra result | Trả về Optional.empty() hoặc exception | | EP: Null input |
| UC-027 | Lấy tất cả users | 1. Gọi userDAO.findAll()<br>2. Kiểm tra kích thước list | Trả về danh sách users (ít nhất 5 users từ CSV) | | EP: Find all |
| UC-028 | Cập nhật user hợp lệ | 1. Lấy user "user01"<br>2. Thay đổi fullName<br>3. Gọi update() | User được cập nhật thành công | | EP: Valid update |
| UC-029 | Cập nhật user không tồn tại | 1. Tạo User với id không tồn tại<br>2. Gọi update() | Ném EntityNotFoundException | | EP: Invalid update |
| UC-030 | Cập nhật email thành email đã tồn tại | 1. Lấy user "user01"<br>2. Đổi email thành email của "user02"<br>3. Gọi update() | Ném exception do UNIQUE constraint | | DT: Duplicate email |
| UC-031 | Cập nhật password của user | 1. Lấy user "user01"<br>2. Đổi password<br>3. Gọi update()<br>4. Tìm lại user | Password được cập nhật thành công | | EP: Password update |
| UC-032 | Cập nhật admin role | 1. Lấy user "user01"<br>2. Đổi admin từ false sang true<br>3. Gọi update() | Admin role được cập nhật | | EP: Role update |
| UC-033 | Xóa user tồn tại | 1. Tạo user mới<br>2. Gọi delete(id)<br>3. Tìm lại user | User bị xóa, findById trả về empty | | EP: Valid delete |
| UC-034 | Xóa user không tồn tại | 1. Gọi delete("nonexistent") | Ném EntityNotFoundException | | EP: Invalid delete |
| UC-035 | Xóa user với id null | 1. Gọi delete(null) | Ném exception | | EP: Null delete |
| UC-036 | Tìm user theo email tồn tại | 1. Gọi userDAO.findByEmail("admin@polysys.com")<br>2. Kiểm tra result | Trả về Optional chứa user với email đó | | EP: Valid email find |
| UC-037 | Tìm user theo email không tồn tại | 1. Gọi userDAO.findByEmail("nonexistent@test.com")<br>2. Kiểm tra result | Trả về Optional.empty() | | EP: Invalid email find |
| UC-038 | Kiểm tra email tồn tại | 1. Gọi userDAO.existsByEmail("admin@polysys.com")<br>2. Kiểm tra result | Trả về true | | EP: Email exists |
| UC-039 | Kiểm tra email không tồn tại | 1. Gọi userDAO.existsByEmail("nonexistent@test.com")<br>2. Kiểm tra result | Trả về false | | EP: Email not exists |
| UC-040 | Đếm số lượng users | 1. Gọi userDAO.count()<br>2. Kiểm tra result | Trả về số lượng users trong DB (ít nhất 5) | | EP: Count users |

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
