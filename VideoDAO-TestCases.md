# VideoDAO Test Cases

## Test Case Overview
This document contains comprehensive test cases for the VideoDAO implementation using black-box testing techniques including:
- Equivalence Partitioning (EP)
- Boundary Value Analysis (BVA)
- Decision Table Testing (DT)

## Database Schema Reference
```sql
CREATE TABLE video (
    Id     char(11)         NOT NULL PRIMARY KEY,
    Title  varchar(255)     NOT NULL,
    Poster varchar(255)     NOT NULL,
    `Desc` longtext         NULL,
    Active bit DEFAULT b'0' NOT NULL,
    Views  int DEFAULT 0    NOT NULL,
    Link   varchar(255)     NOT NULL
);
```

---

## Test Cases

| Id | Mô tả | Steps (Thao tác hành động) | Kết quả mong đợi | Kết quả thực tế | Lưu ý |
|----|-------|---------------------------|-----------------|----------------|--------|
| VC-001 | Tạo video hợp lệ với đầy đủ thông tin | 1. Tạo Video với id="TESTVID001", title="Test Video", posterUrl="poster.jpg", description="Description", active=true, views=0, link="https://youtube.com/test"<br>2. Gọi videoDAO.create(video) | Video được tạo thành công, trả về true | | EP: Valid input partition |
| VC-002 | Tạo video với id null | 1. Tạo Video với id=null<br>2. Gọi create() | Ném exception hoặc trả về false | | EP: Invalid id |
| VC-003 | Tạo video với id rỗng | 1. Tạo Video với id=""<br>2. Gọi create() | Ném exception hoặc trả về false | | BVA: Minimum boundary |
| VC-004 | Tạo video với id = 11 ký tự | 1. Tạo Video với id="12345678901" (11 ký tự)<br>2. Gọi create() | Video được tạo thành công | | BVA: Exact boundary |
| VC-005 | Tạo video với id < 11 ký tự | 1. Tạo Video với id="TEST123" (7 ký tự)<br>2. Gọi create() | Video được tạo thành công | | BVA: Below boundary |
| VC-006 | Tạo video với id > 11 ký tự | 1. Tạo Video với id="123456789012" (12 ký tự)<br>2. Gọi create() | Ném exception do vượt quá độ dài | | BVA: Above boundary |
| VC-007 | Tạo video với id đã tồn tại | 1. Tạo video với id="V01" (đã có trong DB)<br>2. Gọi create() | Ném EntityExistsException | | EP: Duplicate key |
| VC-008 | Tạo video với title null | 1. Tạo Video với title=null<br>2. Gọi create() | Ném exception do NOT NULL constraint | | EP: Invalid title |
| VC-009 | Tạo video với title rỗng | 1. Tạo Video với title=""<br>2. Gọi create() | Video được tạo hoặc exception | | BVA: Minimum title |
| VC-010 | Tạo video với title = 1 ký tự | 1. Tạo Video với title="A"<br>2. Gọi create() | Video được tạo thành công | | BVA: Minimum valid |
| VC-011 | Tạo video với title = 255 ký tự | 1. Tạo Video với title có 255 ký tự<br>2. Gọi create() | Video được tạo thành công | | BVA: Maximum boundary |
| VC-012 | Tạo video với title > 255 ký tự | 1. Tạo Video với title có 256 ký tự<br>2. Gọi create() | Ném exception do vượt quá độ dài | | BVA: Above maximum |
| VC-013 | Tạo video với posterUrl null | 1. Tạo Video với posterUrl=null<br>2. Gọi create() | Ném exception do NOT NULL constraint | | EP: Invalid poster |
| VC-014 | Tạo video với posterUrl rỗng | 1. Tạo Video với posterUrl=""<br>2. Gọi create() | Video được tạo hoặc exception | | BVA: Minimum poster |
| VC-015 | Tạo video với posterUrl = 255 ký tự | 1. Tạo Video với posterUrl có 255 ký tự<br>2. Gọi create() | Video được tạo thành công | | BVA: Maximum boundary |
| VC-016 | Tạo video với posterUrl > 255 ký tự | 1. Tạo Video với posterUrl có 256 ký tự<br>2. Gọi create() | Ném exception do vượt quá độ dài | | BVA: Above maximum |
| VC-017 | Tạo video với description null | 1. Tạo Video với description=null<br>2. Gọi create() | Video được tạo thành công (nullable) | | EP: Null description valid |
| VC-018 | Tạo video với description rỗng | 1. Tạo Video với description=""<br>2. Gọi create() | Video được tạo thành công | | EP: Empty description |
| VC-019 | Tạo video với description rất dài | 1. Tạo Video với description có 10000 ký tự<br>2. Gọi create() | Video được tạo thành công (LONGTEXT) | | EP: Large text |
| VC-020 | Tạo video với active=true | 1. Tạo Video với active=true<br>2. Gọi create() | Video được tạo và active | | EP: Active video |
| VC-021 | Tạo video với active=false | 1. Tạo Video với active=false<br>2. Gọi create() | Video được tạo và inactive | | EP: Inactive video |
| VC-022 | Tạo video với views=0 | 1. Tạo Video với views=0<br>2. Gọi create() | Video được tạo với views=0 | | BVA: Minimum views |
| VC-023 | Tạo video với views=-1 | 1. Tạo Video với views=-1<br>2. Gọi create() | Video được tạo (không validate âm) hoặc exception | | BVA: Below minimum |
| VC-024 | Tạo video với views=999999 | 1. Tạo Video với views=999999<br>2. Gọi create() | Video được tạo thành công | | BVA: Large number |
| VC-025 | Tạo video với link null | 1. Tạo Video với link=null<br>2. Gọi create() | Ném exception do NOT NULL constraint | | EP: Invalid link |
| VC-026 | Tạo video với link rỗng | 1. Tạo Video với link=""<br>2. Gọi create() | Video được tạo hoặc exception | | BVA: Minimum link |
| VC-027 | Tạo video với link = 255 ký tự | 1. Tạo Video với link có 255 ký tự<br>2. Gọi create() | Video được tạo thành công | | BVA: Maximum boundary |
| VC-028 | Tạo video với link > 255 ký tự | 1. Tạo Video với link có 256 ký tự<br>2. Gọi create() | Ném exception do vượt quá độ dài | | BVA: Above maximum |
| VC-029 | Tìm video theo id tồn tại | 1. Gọi videoDAO.findById("V01")<br>2. Kiểm tra result | Trả về Optional chứa video | | EP: Valid find |
| VC-030 | Tìm video theo id không tồn tại | 1. Gọi videoDAO.findById("NONEXIST")<br>2. Kiểm tra result | Trả về Optional.empty() | | EP: Invalid find |
| VC-031 | Tìm video với id null | 1. Gọi videoDAO.findById(null)<br>2. Kiểm tra result | Trả về Optional.empty() hoặc exception | | EP: Null input |
| VC-032 | Lấy tất cả videos | 1. Gọi videoDAO.findAll()<br>2. Kiểm tra kích thước list | Trả về danh sách videos (ít nhất 21 từ CSV) | | EP: Find all |
| VC-033 | Cập nhật video hợp lệ | 1. Lấy video "V01"<br>2. Thay đổi title<br>3. Gọi update() | Video được cập nhật thành công | | EP: Valid update |
| VC-034 | Cập nhật video không tồn tại | 1. Tạo Video với id không tồn tại<br>2. Gọi update() | Ném EntityNotFoundException | | EP: Invalid update |
| VC-035 | Cập nhật active status | 1. Lấy video "V01"<br>2. Đổi active từ true sang false<br>3. Gọi update() | Active status được cập nhật | | EP: Status update |
| VC-036 | Cập nhật views | 1. Lấy video "V01"<br>2. Tăng views<br>3. Gọi update() | Views được cập nhật | | EP: Views update |
| VC-037 | Xóa video tồn tại | 1. Tạo video mới<br>2. Gọi delete(id)<br>3. Tìm lại video | Video bị xóa, findById trả về empty | | EP: Valid delete |
| VC-038 | Xóa video không tồn tại | 1. Gọi delete("NONEXIST") | Ném EntityNotFoundException | | EP: Invalid delete |
| VC-039 | Xóa video với id null | 1. Gọi delete(null) | Ném exception | | EP: Null delete |
| VC-040 | Tìm tất cả videos active | 1. Gọi videoDAO.findByActiveTrue()<br>2. Kiểm tra result | Trả về danh sách chỉ videos có active=true | | EP: Filter by active |
| VC-041 | Tìm video theo title chứa keyword | 1. Gọi videoDAO.findByTitleContaining("Russian")<br>2. Kiểm tra result | Trả về danh sách videos có title chứa "Russian" | | EP: Search by keyword |
| VC-042 | Tìm video với keyword không tồn tại | 1. Gọi videoDAO.findByTitleContaining("NonExistentKeyword")<br>2. Kiểm tra result | Trả về danh sách rỗng | | EP: No match search |
| VC-043 | Tìm video với keyword rỗng | 1. Gọi videoDAO.findByTitleContaining("")<br>2. Kiểm tra result | Trả về tất cả videos | | BVA: Empty keyword |
| VC-044 | Tìm video với keyword null | 1. Gọi videoDAO.findByTitleContaining(null)<br>2. Kiểm tra result | Ném exception hoặc trả về empty | | EP: Null keyword |
| VC-045 | Đếm tất cả videos | 1. Gọi videoDAO.count()<br>2. Kiểm tra result | Trả về số lượng videos trong DB | | EP: Count all |
| VC-046 | Đếm videos active | 1. Gọi videoDAO.countByActiveTrue()<br>2. Kiểm tra result | Trả về số lượng videos có active=true | | EP: Count active |
| VC-047 | Tạo video với tất cả fields tối thiểu | 1. Tạo Video chỉ với các fields bắt buộc<br>2. Gọi create() | Video được tạo với default values | | EP: Minimum fields |
| VC-048 | Tìm video case-insensitive | 1. Gọi findByTitleContaining("RUSSIAN")<br>2. Kiểm tra result | Trả về videos chứa "Russian" (case insensitive) | | EP: Case insensitive search |
| VC-049 | Cập nhật tất cả fields của video | 1. Lấy video<br>2. Đổi tất cả fields<br>3. Update<br>4. Verify | Tất cả fields được cập nhật | | EP: Full update |
| VC-050 | Tăng view count qua increaseView | 1. Lấy video "V01" và views hiện tại<br>2. Gọi increaseView("V01")<br>3. Kiểm tra views mới | Views tăng thêm 1 | | EP: Increase view |

---

## Decision Table - Create Video

| Condition | Test 1 | Test 2 | Test 3 | Test 4 | Test 5 | Test 6 |
|-----------|--------|--------|--------|--------|--------|--------|
| Id valid & unique | Y | N | Y | Y | Y | Y |
| Title valid | Y | Y | N | Y | Y | Y |
| Poster valid | Y | Y | Y | N | Y | Y |
| Link valid | Y | Y | Y | Y | N | Y |
| All lengths OK | Y | Y | Y | Y | Y | N |
| **Action** | **Success** | **Fail** | **Fail** | **Fail** | **Fail** | **Fail** |

## Decision Table - findByTitleContaining

| Condition | Test 1 | Test 2 | Test 3 | Test 4 |
|-----------|--------|--------|--------|--------|
| Keyword is valid | Y | Y | N | Y |
| Keyword matches | Y | N | - | Y |
| Case sensitive | N | N | - | Y |
| **Result** | **List with matches** | **Empty list** | **Exception/Empty** | **Case insensitive match** |

## Notes
- Test cases cover all CRUD operations and custom query methods
- Boundary value analysis focuses on string length constraints (11, 255 chars)
- Equivalence partitioning covers valid/invalid inputs for all fields
- Decision tables ensure all constraint combinations are tested
- Custom methods (findByActiveTrue, findByTitleContaining, countByActiveTrue) are thoroughly tested
- increaseView method is tested separately for view count increment logic
