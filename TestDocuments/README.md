# Thư mục TestDocuments

Thư mục này chứa toàn bộ tài liệu kiểm thử cho hệ thống quản lý video PolySys.

## Bắt đầu nhanh

**Bắt đầu từ đây:** Đọc `MasterDocument.md` để có cái nhìn tổng quan và hướng dẫn đầy đủ.

## Cấu trúc tài liệu

### Tài liệu chính

1. **MasterDocument.md** - Tài liệu kiểm thử tổng hợp
   - Tổng quan đầy đủ về bộ test suite
   - Hướng dẫn chạy tests
   - Tóm tắt các test cases
   - Yêu cầu và thiết lập

2. **TestTypes.md** - Danh sách đầy đủ các loại kiểm thử
   - Kiểm thử đơn vị (Unit testing)
   - Kiểm thử tích hợp (Integration testing)
   - Kiểm thử giao diện (UI testing)
   - Các kỹ thuật kiểm thử hộp đen
   - Kiểm thử bảo mật và khả năng sử dụng

3. **TestPlan.md** - Kế hoạch kiểm thử chi tiết
   - Chiến lược kiểm thử
   - Chi tiết độ phủ kiểm thử
   - Thiết lập môi trường kiểm thử
   - Tích hợp CI/CD
   - Tiêu chí vào/ra

4. **TestCases.md** - Kết quả thực thi kiểm thử
   - Trạng thái biên dịch
   - Kết quả mong đợi
   - Tóm tắt độ phủ kiểm thử
   - Các kỹ thuật kiểm thử hộp đen được áp dụng

### Tài liệu Test Cases chi tiết

5. **UserDAO-TestCases.md** - 40 test cases cho UserDAO
6. **VideoDAO-TestCases.md** - 50 test cases cho VideoDAO
7. **FavoriteDAO-TestCases.md** - 20 test cases cho FavoriteDAO
8. **ShareDAO-TestCases.md** - 23 test cases cho ShareDAO
9. **LoginUI-TestCases.md** - 10 test cases giao diện đăng nhập
10. **VideoBrowsing-TestCases.md** - 15 test cases giao diện duyệt video

### Script thực thi

11. **run-tests.sh** - Script tự động chạy tests
    - Chạy tất cả tests hoặc các bộ test cụ thể
    - Hỗ trợ chạy riêng backend và UI tests

## Chạy Tests

```bash
# Từ thư mục gốc dự án, chạy tất cả tests
./TestDocuments/run-tests.sh

# Chạy bộ test cụ thể
./TestDocuments/run-tests.sh backend
./TestDocuments/run-tests.sh ui
./TestDocuments/run-tests.sh user
./TestDocuments/run-tests.sh video
```

## Thống kê Tests

- **Tổng số Tests**: 173 (133 backend + 40 UI)
- **Kỹ thuật kiểm thử**: Phân vùng tương đương, Phân tích giá trị biên, Kiểm thử bảng quyết định
- **Tự động hóa**: 100% tự động với TestNG + Selenium
- **CI/CD**: Tích hợp với GitHub Actions

## Tổ chức tài liệu

Tất cả tài liệu kiểm thử được tổ chức trong một thư mục duy nhất để:
- ✅ Dễ dàng điều hướng
- ✅ Cấu trúc rõ ràng
- ✅ Tài liệu tập trung
- ✅ Bảo trì tốt hơn
- ✅ Không có file rải rác trong thư mục gốc

---

**Cập nhật lần cuối**: 2025-10-20  
**Trạng thái**: Đang hoạt động
