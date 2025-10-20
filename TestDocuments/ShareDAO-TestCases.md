# ShareDAO Test Cases - Tài liệu

## Tổng quan
Tài liệu này chứa các test cases toàn diện cho class ShareDAO, quản lý chức năng chia sẻ video trong hệ thống PolySys.

## Độ phủ Test
- **Tổng số Test Cases**: 23
- **Các lĩnh vực được bao phủ**: Các thao tác Create, Read, Delete, xác thực và trường hợp biên

## Test Cases

| ID | Test Case Name | Description | Input | Expected Result | Priority |
|----|---------------|-------------|-------|-----------------|----------|
| SC-001 | testCreateValidShare | Create share with valid data | Valid User, Video, Emails, Date | Share created successfully | High |
| SC-002 | testCreateShareWithNullUser | Attempt to create share with null user | null User, Valid Video | Exception thrown | High |
| SC-003 | testCreateShareWithNullVideo | Attempt to create share with null video | Valid User, null Video | Exception thrown | High |
| SC-004 | testCreateShareWithNullEmails | Attempt to create share with null emails | Valid data, null Emails | Exception thrown | High |
| SC-005 | testCreateShareWithEmptyEmails | Create share with empty emails string | Valid data, emails="" | May succeed or fail | Medium |
| SC-006 | testCreateShareWith50CharEmails | Create share with 50 character emails | Valid data, emails=50 chars | Share created successfully | Medium |
| SC-007 | testCreateShareWithEmailsTooLong | Attempt to create share with emails > 50 chars | Valid data, emails=51 chars | Exception thrown | High |
| SC-008 | testCreateShareWithNullShareDate | Attempt to create share with null date | Valid data, null Date | Exception thrown | High |
| SC-009 | testFindByUserIdValid | Find shares by existing userId | userId="user01" | List of shares returned | Medium |
| SC-010 | testFindByUserIdNonExistent | Find shares by non-existent userId | userId="nonexistent" | Empty list returned | Medium |
| SC-011 | testFindByVideoIdValid | Find shares by existing videoId | videoId="V01" | List of shares returned | Medium |
| SC-012 | testFindByVideoIdNonExistent | Find shares by non-existent videoId | videoId="NONEXIST" | Empty list returned | Medium |
| SC-013 | testFindByUserAndVideo | Find specific share by user and video | userId="user02", videoId="V07" | Optional with share | Medium |
| SC-014 | testDeleteValidShare | Delete existing share | userId="user03", videoId="V08" | Share deleted successfully | High |
| SC-015 | testDeleteNonExistentShare | Attempt to delete non-existent share | userId="user01", videoId="NONEXIST" | EntityNotFoundException thrown | High |
| SC-016 | testCountByVideoId | Count shares for a video | videoId="V01" | Non-negative count | Low |
| SC-017 | testCountByVideoIdNonExistent | Count shares for non-existent video | videoId="NONEXIST" | Count = 0 | Low |
| SC-018 | testCreateMultipleSharesForSameUser | Create multiple shares for one user | Same user, different videos | All shares created | Medium |
| SC-019 | testCreateMultipleSharesForSameVideo | Create multiple shares for one video | Different users, same video | All shares created | Medium |
| SC-020 | testFindByUserIdOrderByShareDateDesc | Verify shares are sorted by date DESC | Create shares with different dates | Newest first ordering | Low |
| SC-021 | testCreateShareWithMultipleEmails | Create share with multiple email addresses | emails="a@x.com,b@y.com,c@z.com" | Share created successfully | Medium |
| SC-022 | testCreateShareWithPastDate | Create share with past date | shareDate in the past | Share created successfully | Low |
| SC-023 | testCreateShareWithFutureDate | Create share with future date | shareDate in the future | Share created successfully | Low |

## Testing Techniques Applied

### 1. Equivalence Partitioning
- **Valid Partition**: Valid user, video, emails (1-50 chars), and date
- **Invalid Partition**: Null values, empty strings, emails > 50 chars

### 2. Boundary Value Analysis
- **Emails field boundaries**:
  - Minimum: "" (empty string)
  - Valid: 1-50 characters
  - Maximum: 50 characters
  - Beyond: 51+ characters (invalid)

### 3. Decision Table Testing
| User Valid | Video Valid | Emails Valid | Date Valid | Result |
|------------|-------------|--------------|------------|--------|
| Yes | Yes | Yes | Yes | Success |
| No | Yes | Yes | Yes | Fail |
| Yes | No | Yes | Yes | Fail |
| Yes | Yes | No | Yes | Fail |
| Yes | Yes | Yes | No | Fail |

## Database Schema
```sql
CREATE TABLE share (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    VideoId VARCHAR(11) NOT NULL,
    UserId VARCHAR(20) NOT NULL,
    Emails VARCHAR(50) NOT NULL,
    ShareDate DATE NOT NULL,
    FOREIGN KEY (VideoId) REFERENCES video(Id),
    FOREIGN KEY (UserId) REFERENCES user(Id)
);
```

## Field Constraints
- **Emails**: VARCHAR(50), NOT NULL
  - Can contain single or multiple email addresses separated by commas
  - Maximum length: 50 characters
- **ShareDate**: DATE, NOT NULL
  - Can be past, present, or future date
- **User and Video**: Must exist in respective tables (foreign key constraints)

## Prerequisites
- MySQL database running
- polysys database created
- Test data loaded (users: user01-user05, videos: V01-V16)

## Notes
- Tests are designed to be independent and idempotent
- Tests clean up their own test data
- Tests use existing seed data where appropriate
- Unlike Favorite, Share does NOT have unique constraint (user can share same video multiple times)
- Emails field stores recipient email addresses, not user's email
