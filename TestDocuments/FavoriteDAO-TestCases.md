# FavoriteDAO Test Cases - Tài liệu

## Tổng quan
Tài liệu này chứa các test cases toàn diện cho class FavoriteDAO, quản lý các video yêu thích của người dùng trong hệ thống PolySys.

## Độ phủ Test
- **Tổng số Test Cases**: 20
- **Các lĩnh vực được bao phủ**: Các thao tác Create, Read, Delete, trường hợp biên và logic nghiệp vụ

## Test Cases

| ID | Test Case Name | Description | Input | Expected Result | Priority |
|----|---------------|-------------|-------|-----------------|----------|
| FC-001 | testCreateValidFavorite | Create favorite with valid user, video, and date | Valid User, Valid Video, Current Date | Favorite created successfully | High |
| FC-002 | testCreateFavoriteWithNullUser | Attempt to create favorite with null user | null User, Valid Video | Exception thrown | High |
| FC-003 | testCreateFavoriteWithNullVideo | Attempt to create favorite with null video | Valid User, null Video | Exception thrown | High |
| FC-004 | testCreateFavoriteWithNullLikeDate | Attempt to create favorite with null likeDate | Valid User, Valid Video, null Date | Exception thrown | High |
| FC-005 | testCreateDuplicateFavorite | Attempt to create duplicate favorite | Existing User-Video combination | EntityExistsException thrown | High |
| FC-006 | testFindByUserIdValid | Find favorites by existing userId | userId="user01" | List of favorites returned | Medium |
| FC-007 | testFindByUserIdNonExistent | Find favorites by non-existent userId | userId="nonexistent" | Empty list returned | Medium |
| FC-008 | testFindByVideoIdValid | Find favorites by existing videoId | videoId="V01" | List of favorites returned | Medium |
| FC-009 | testFindByVideoIdNonExistent | Find favorites by non-existent videoId | videoId="NONEXIST" | Empty list returned | Medium |
| FC-010 | testFindByUserAndVideoValid | Find specific favorite by user and video | userId="user02", videoId="V04" | Optional with favorite | Medium |
| FC-011 | testFindByUserAndVideoNonExistent | Find non-existent user-video combination | userId="user01", videoId="NONEXIST" | Empty Optional | Medium |
| FC-012 | testDeleteValidFavorite | Delete existing favorite | userId="user03", videoId="V05" | Favorite deleted successfully | High |
| FC-013 | testDeleteNonExistentFavorite | Attempt to delete non-existent favorite | userId="user01", videoId="NONEXIST" | EntityNotFoundException thrown | High |
| FC-014 | testCountByVideoId | Count favorites for a video | videoId="V01" | Non-negative count | Low |
| FC-015 | testCountByVideoIdNonExistent | Count favorites for non-existent video | videoId="NONEXIST" | Count = 0 | Low |
| FC-016 | testCreateMultipleFavoritesForSameUser | Create multiple favorites for one user | Same user, different videos | All favorites created | Medium |
| FC-017 | testCreateMultipleFavoritesForSameVideo | Create multiple favorites for one video | Different users, same video | All favorites created | Medium |
| FC-018 | testFindByUserIdOrderByLikeDateDesc | Verify favorites are sorted by date DESC | Create favorites with different dates | Newest first ordering | Low |
| FC-019 | testCreateFavoriteWithPastDate | Create favorite with past date | likeDate in the past | Favorite created successfully | Low |
| FC-020 | testCreateFavoriteWithFutureDate | Create favorite with future date | likeDate in the future | Favorite created successfully | Low |

## Testing Techniques Applied

### 1. Equivalence Partitioning
- **Valid Partition**: Valid user, video, and date combinations
- **Invalid Partition**: Null values, non-existent entities, duplicates

### 2. Boundary Value Analysis
- Testing with minimum data (null)
- Testing with valid data
- Testing with edge cases (past dates, future dates)

### 3. Decision Table Testing
| User Valid | Video Valid | Date Valid | Unique | Result |
|------------|-------------|------------|--------|--------|
| Yes | Yes | Yes | Yes | Success |
| No | Yes | Yes | Yes | Fail |
| Yes | No | Yes | Yes | Fail |
| Yes | Yes | No | Yes | Fail |
| Yes | Yes | Yes | No | Fail |

## Database Schema
```sql
CREATE TABLE favorite (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    VideoId VARCHAR(11) NOT NULL,
    UserId VARCHAR(20) NOT NULL,
    LikeDate DATE NOT NULL,
    UNIQUE KEY unique_favorite (VideoId, UserId),
    FOREIGN KEY (VideoId) REFERENCES video(Id),
    FOREIGN KEY (UserId) REFERENCES user(Id)
);
```

## Prerequisites
- MySQL database running
- polysys database created
- Test data loaded (users: user01-user05, videos: V01-V16)

## Notes
- Tests are designed to be independent and idempotent
- Tests clean up their own test data
- Tests use existing seed data where appropriate
- Unique constraint ensures no duplicate user-video favorites
