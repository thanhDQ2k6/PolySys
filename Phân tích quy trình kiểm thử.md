# Tổng hợp: Quy trình phát triển phần mềm, vai trò kiểm thử, so sánh manual vs automation và tiêu chí chọn công cụ tự động hóa

Tài liệu này tổng hợp 3 phần:
- BÀI 1: 3 quy trình phát triển phần mềm thông dụng (Waterfall, V-Model, Agile) kèm ưu/nhược điểm và vai trò kiểm thử.
- BÀI 2: So sánh manual testing và automation testing (quy trình, ưu/nhược điểm, tình huống áp dụng).
- BÀI 3: Tiêu chí lựa chọn tool, framework trong kiểm thử tự động.

---

## BÀI 1: Tìm hiểu và phân tích các quy trình phát triển phần mềm

### 1) Mô hình Waterfall (Thác Nước)

- Khái niệm:
  - Quy trình tuyến tính tuần tự; mỗi giai đoạn hoàn tất rồi mới chuyển sang giai đoạn tiếp theo, ít (hoặc không) quay lại.
  - Phù hợp khi yêu cầu ổn định, phạm vi rõ ràng, ràng buộc mạnh về tài liệu và tuân thủ.

- Các giai đoạn trong Waterfall:
  - Giai đoạn 1: Khảo sát và yêu cầu (Requirements)
    - Thu thập, phân tích yêu cầu; lập SRS (Software Requirements Specification), RTM (Requirement Traceability Matrix).
  - Giai đoạn 2: Thiết kế hệ thống (System Design)
    - Kiến trúc tổng thể, thiết kế dữ liệu, giao diện, thành phần; HLD/LLD.
  - Giai đoạn 3: Triển khai/hiện thực (Implementation)
    - Lập trình theo thiết kế; code review; build.
  - Giai đoạn 4: Kiểm thử (Verification/Validation)
    - Kiểm thử hệ thống dựa trên SRS; báo cáo lỗi; có thể tách kiểm thử nghiệm thu.
  - Giai đoạn 5: Triển khai (Deployment)
    - Cài đặt, chuyển giao, hướng dẫn sử dụng.
  - Giai đoạn 6: Bảo trì (Maintenance)
    - Sửa lỗi, nâng cấp nhỏ, vận hành.

- Ưu điểm:
  - Dễ quản lý, tiến độ rõ; tài liệu đầy đủ; phù hợp dự án yêu cầu ổn định; dễ tuân thủ tiêu chuẩn.

- Nhược điểm:
  - Khó thích ứng thay đổi; phản hồi người dùng trễ; phát hiện rủi ro muộn; chi phí sửa lỗi cuối kỳ cao.

- Kiểm thử trong Waterfall (công việc chính):
  - Lập Test Strategy/Test Plan sau SRS; thiết kế test case dựa SRS/HLD; chuẩn bị dữ liệu test; thiết lập môi trường.
  - Mức kiểm thử:
    - Unit test: do dev sau Implementation.
    - Integration test: sau unit; do QA/dev.
    - System test: so với SRS; do QA.
    - UAT: do người dùng/đại diện.
  - Artefacts: Test Plan, Test Case/Checklist, RTM, Defect report, Test Summary Report.
  - Rủi ro: feedback chậm, dồn lỗi về cuối vòng đời, automation thường trễ (phụ thuộc UI ổn định).

---

### 2) Mô hình V-Model (Verification and Validation)

- Khái niệm:
  - Phát triển “hình chữ V”, nhấn mạnh kiểm thử song hành: mỗi giai đoạn đặc tả/thiết kế (bên trái) có cặp kiểm thử tương ứng (bên phải).
  - Tư duy “kiểm thử sớm” (shift-left), thiết kế test song song với đặc tả.

- Các giai đoạn trong V-Model:
  - Giai đoạn 1: Phân tích yêu cầu nghiệp vụ (Business Requirements) → UAT
  - Giai đoạn 2: Yêu cầu hệ thống/phần mềm (System/Software Requirements) → System Test
  - Giai đoạn 3: Thiết kế tổng thể (High-Level Design) → Integration Test
  - Giai đoạn 4: Thiết kế chi tiết (Low-Level Design) → Unit Test (định nghĩa)
  - Giai đoạn 5: Lập trình (Implementation) → Unit Test (thực thi)
  - Giai đoạn 6: Tích hợp và kiểm thử tích hợp (Integration & Testing)
  - Giai đoạn 7: Kiểm thử hệ thống và nghiệm thu (System Test & UAT)

- Ưu điểm:
  - Thiết kế test sớm; RTM mạnh; giảm rủi ro phát hiện muộn; nâng cao chất lượng tài liệu.

- Nhược điểm:
  - Tài liệu nặng; thay đổi muộn vẫn khó; yêu cầu kỷ luật cao; chi phí đầu kỳ lớn.

- Kiểm thử trong V-Model:
  - Mapping kiểm thử:
    - Business Requirements ↔ UAT (kịch bản nghiệp vụ, tiêu chí chấp nhận).
    - System Requirements ↔ System Test (end-to-end, non-functional).
    - HLD ↔ Integration Test (giao tiếp module, API).
    - LLD/Code ↔ Unit Test (hàm/lớp).
  - Hoạt động:
    - Viết test plan/design song song từng giai đoạn đặc tả.
    - Dùng RTM đảm bảo đủ phủ yêu cầu; non-functional (performance, security, usability) lên kế hoạch sớm và gắn vào System Test.
  - Artefacts: Test Strategy theo tầng, Test Design Spec theo cặp giai đoạn, RTM chi tiết, báo cáo xác minh/val.

---

### 3) Mô hình Agile (Scrum/Kanban/XP)

- Khái niệm:
  - Phát triển lặp (iterative) và tăng trưởng (incremental), lấy phản hồi nhanh, thích ứng thay đổi.
  - Scrum phổ biến: Sprint 1–4 tuần; Kanban chú trọng luồng công việc; XP nhấn mạnh kỹ thuật (TDD, refactor).

- Các nguyên tắc của Agile (tóm lược Agile Manifesto):
  - Ưu tiên cá nhân & tương tác; phần mềm chạy được; hợp tác khách hàng; phản hồi thay đổi.
  - Giao hàng sớm; nhịp độ bền vững; kỹ thuật tốt; đơn giản; tự tổ chức; phản tư thường xuyên.

- Các giai đoạn trong Agile (Scrum điển hình):
  - Lập Product Backlog; Refinement (làm rõ, ưu tiên).
  - Sprint Planning → Sprint Backlog.
  - Thiết kế–Phát triển–Kiểm thử trong sprint (Dev+QA cùng team).
  - Daily Scrum; CI/CD; Sprint Review/Demo; Retrospective; Release theo nhịp.

- Ưu điểm:
  - Thích ứng nhanh; rút ngắn time-to-value; giảm rủi ro; liên tục cải tiến; chất lượng nhờ feedback sớm + automation.

- Nhược điểm:
  - Yêu cầu khách hàng tham gia liên tục; khó dự báo phạm vi dài hạn; đòi hỏi kỷ luật và tự quản cao; nguy cơ nợ kỹ thuật nếu bỏ qua kỹ thuật tốt.

- Kiểm thử trong Agile:
  - QA “in-sprint”: tham gia từ refinement (thêm acceptance criteria), chuẩn bị test sớm.
  - ATDD/BDD (Gherkin), TDD; kiểm thử liên tục trong CI/CD; Test Pyramid (unit > API/integration > UI).
  - Loại hình: unit, API, UI, exploratory, regression automation, non-functional (perf, security, accessibility).
  - Artefacts: Definition of Done có tiêu chí test/coverage; test suite tự động; báo cáo pipeline; defect triage trong sprint.

---

### Phân tích chi tiết công việc kiểm thử theo từng mô hình

- Waterfall:
  - Lập Test Strategy/Plan sau SRS; thiết kế test case trước khi code xong nhưng thường thực thi muộn.
  - Trọng tâm System/UAT; regression cuối kỳ; automation thường sau khi UI ổn định.
  - Rào cản: feedback chậm; chi phí thay đổi cao; rủi ro dồn cuối.

- Agile:
  - QA đồng hành: viết acceptance criteria, ví dụ BDD; thiết kế test song song dev; pair testing.
  - Ưu tiên automation sớm (đặc biệt unit/API); chạy pipeline mỗi commit; quản lý flakiness; mock/stub để cô lập.
  - Exploratory test trong sprint; non-functional xen kẽ hoặc job đêm.

- V-Model:
  - Thiết kế test sớm ở mọi tầng; RTM nghiêm ngặt; xác minh/validation rõ ràng.
  - Mỗi tài liệu đặc tả có gói test tương ứng; coverage quản trị bằng RTM; dễ audit/tuân thủ.

---

## BÀI 2: So sánh manual testing và automation testing

### a) So sánh quy trình

- Manual testing (dòng công việc):
  - Phân tích yêu cầu → Test Plan/Strategy → Thiết kế test case & test data → Thiết lập môi trường → Thực thi thủ công → Ghi nhận kết quả/bug → Retest/Regression → Test Summary.

- Automation testing (dòng công việc):
  - Xác định phạm vi tự động hóa → Chọn công cụ/framework → Thiết kế kiến trúc test (POM/Screenplay, fixtures, data, mocking) → Viết script + review → Tích hợp CI/CD, cấu hình song song/ma trận → Báo cáo (Allure/ReportPortal) → Bảo trì/giảm flakiness → Mở rộng phủ (dựa ROI).

- Khác biệt chính:
  - Automation thêm hoạt động về kiến trúc framework, quản lý mã test, hạ tầng runner, song song hóa, báo cáo tự động, chiến lược bảo trì.

### b) So sánh ưu nhược điểm

- Manual testing:
  - Ưu điểm:
    - Linh hoạt; phù hợp exploratory/UX/usability; thiết lập nhanh; không phụ thuộc tool; phù hợp tính năng mới/biến động.
  - Nhược điểm:
    - Lặp lại tốn thời gian; dễ sai sót; khó chạy khối lượng lớn/đa nền tảng; khó tích hợp CI/CD; chi phí dài hạn cao cho regression.

- Automation testing:
  - Ưu điểm:
    - Nhanh, lặp lại, song song; tích hợp CI/CD; phát hiện sớm; mở rộng regression; đo lường ổn định; hỗ trợ cross-browser/device.
  - Nhược điểm:
    - Chi phí đầu tư ban đầu; cần kỹ năng lập trình/devops; bảo trì khi UI thay đổi; rủi ro flakiness; không thay thế tốt UX/khả dụng.

### c) Các tình huống áp dụng

- Nên manual:
  - Exploratory, usability/UX, A/B, test một lần/POC, yêu cầu/UX biến động mạnh, kiểm thử đòi hỏi phán đoán con người, kiểm thử thị giác/âm thanh tinh tế.

- Nên automation:
  - Regression/smoke/sanity lặp lại; API/service layer; kịch bản data-driven lớn; cross-browser/device; hiệu năng/tải; một phần bảo mật (DAST).

- Khuyến nghị kết hợp:
  - Áp dụng Test Pyramid: 60–80% unit, 15–30% API/integration, 5–10% UI.
  - Manual cho exploratory/UX và các kiểm thử khó tự động hóa; tự động hóa regression trọng yếu.

---

## BÀI 3: Tiêu chí lựa chọn tool, framework trong kiểm thử tự động

1) Phù hợp công nghệ và sản phẩm
- Loại ứng dụng:
  - Web: Selenium, Playwright, Cypress
  - Mobile: Appium, Espresso, XCUITest, Detox
  - Desktop: WinAppDriver, Winium
  - API: REST Assured, Karate, Postman/Newman
  - gRPC: Karate, ghz
  - Performance: k6, JMeter
  - Security: OWASP ZAP
- Ngôn ngữ đội ngũ:
  - JS/TS (Playwright/Cypress), Java (Selenium, REST Assured, TestNG), Python (Playwright, pytest, locust/k6 via JS), C# (.NET + Selenium/Playwright).
- Hỗ trợ công nghệ UI:
  - Shadow DOM, iframe, WebComponents, upload file, network mocking, accessibility.

2) Khả năng bảo trì và độ tin cậy
- Locator ổn định (by role/text; auto-wait như Playwright/Cypress); hạn chế flakiness (retry có kiểm soát, auto-wait, network idle).
- Cấu trúc test: POM/Screenplay, fixtures, data builders, DRY.
- Báo cáo/trace: video, screenshot, network trace, step logs, Allure/ReportPortal.

3) Hiệu năng và khả năng mở rộng
- Chạy song song, sharding, ma trận trình duyệt/thiết bị; headless; hỗ trợ Docker.
- Tích hợp device farm (BrowserStack, Sauce Labs, LambdaTest) khi cần.

4) Tích hợp DevOps và hệ sinh thái
- CI/CD (GitHub Actions, GitLab, Jenkins, Azure DevOps); cache dependencies; secrets; artifacts.
- Quản lý cấu hình theo môi trường; test data management; service virtualization/mocking (WireMock, MSW, Hoverfly).
- Tích hợp test management (Jira/Xray, Zephyr, TestRail), liên kết defect; quality gates (coverage, lint).

5) Tính năng non-functional và chuyên biệt
- Performance (k6, JMeter), Accessibility (axe-core, Pa11y), Security (OWASP ZAP), Visual testing (Applitools, Percy).

6) Khả dụng, cộng đồng, chi phí
- Tài liệu, cộng đồng, tốc độ cập nhật; giấy phép (OSS vs thương mại), TCO; đường cong học tập; rủi ro vendor lock-in.

7) Governance và tuân thủ
- Audit logs, traceability, tiêu chuẩn (ISO, SOC2), bảo mật dữ liệu test (PII), lưu trữ artifact.

### Gợi ý lựa chọn theo tình huống
- Web end-to-end hiện đại: Playwright (đa trình duyệt, auto-wait, stable locators) hoặc Cypress (DX tốt, ecosystem mạnh).
- Web legacy/cần đa ngôn ngữ: Selenium + TestNG/JUnit + Allure; dùng Grid/cloud để mở rộng.
- API-first: REST Assured (Java), Karate (DSL hợp nhất API + một phần UI), Postman/Newman (nhanh để bắt đầu), Pact (contract testing).
- Mobile: Appium (đa nền tảng); Espresso/XCUITest (native, ổn định cao nhưng đặc thù).
- Performance: k6 (thân thiện CI, scripting JS), JMeter (plugin phong phú).
- Báo cáo: Allure, ReportPortal; quản lý flakiness: retry có kiểm soát + RCA.

### Khung đánh giá nhanh (decision matrix tham khảo)
- Trọng số ví dụ:
  - Phù hợp công nghệ (25%), Độ tin cậy/flakiness (20%), DevOps tích hợp (15%), Bảo trì/đọc được (15%), Hiệu năng/mở rộng (10%), Chi phí (10%), Cộng đồng/hỗ trợ (5%).
- Quy trình POC 1–2 tuần với 2–3 lựa chọn, đo:
  - Thời gian viết test, thời gian chạy, tỷ lệ flake, độ dễ bảo trì, tính năng bắt buộc.

### Thực hành tốt khi triển khai automation
- Bắt đầu từ smoke/regression giá trị cao; ưu tiên API trước UI; áp dụng Test Pyramid.
- Xây framework tối thiểu: cấu hình, fixtures, data, report, CI pipeline, parallel.
- Kiểm soát flakiness: locator ổn định, waits hợp lý, cô lập dữ liệu, mock network khi phù hợp.
- Mã test theo chuẩn code (lint, review, PR), tái sử dụng, tách lớp abstraction.
- Theo dõi: độ bao phủ (không tuyệt đối hóa), thời gian chạy, MTTR, tỷ lệ flaky, ROI.