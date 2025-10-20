# PolySys System Test Plan

## 1. Introduction

### 1.1 Purpose
This test plan document outlines the comprehensive testing strategy for the PolySys video management system. It defines the scope, approach, resources, and schedule of testing activities.

### 1.2 Project Overview
**Project Name**: PolySys - Video Management System
**Version**: 1.0-SNAPSHOT
**Technology Stack**: 
- Backend: Java 11, Hibernate JPA, MySQL
- Frontend: JSP, JSTL, JavaScript
- Testing: TestNG, Selenium WebDriver
- Build: Maven
- CI/CD: GitHub Actions

### 1.3 Document Scope
This document covers:
- Unit and Integration testing for Data Access Layer (DAO)
- User Interface (UI) testing using Selenium
- Test case documentation and execution procedures
- CI/CD integration and automated testing

## 2. Test Strategy

### 2.1 Testing Levels

#### 2.1.1 Unit Testing
- **Scope**: Individual DAO methods
- **Framework**: TestNG
- **Coverage**: UserDAO, VideoDAO, FavoriteDAO, ShareDAO
- **Total Tests**: 133 test cases

#### 2.1.2 Integration Testing
- **Scope**: Database integration, entity relationships
- **Framework**: TestNG with JPA/Hibernate
- **Coverage**: CRUD operations, custom queries, transactions

#### 2.1.3 UI Testing
- **Scope**: User interfaces and workflows
- **Framework**: Selenium WebDriver + TestNG
- **Coverage**: Login, Video Browsing, Admin Management
- **Total Tests**: 40 test cases

### 2.2 Testing Types

#### 2.2.1 Functional Testing
- Verify all features work as specified
- Test CRUD operations for all entities
- Validate business logic and workflows

#### 2.2.2 Black-Box Testing Techniques
- **Equivalence Partitioning**: Valid/invalid data classes
- **Boundary Value Analysis**: Min/max field lengths
- **Decision Table Testing**: Combination of constraints

#### 2.2.3 Negative Testing
- Test with null values
- Test with invalid data (too long, wrong format)
- Test duplicate key violations
- Test constraint violations

#### 2.2.4 Security Testing
- Password masking in UI
- Session management
- Authentication and authorization
- SQL injection prevention (via JPA)

#### 2.2.5 Usability Testing
- Responsive design (desktop, tablet, mobile)
- Navigation flow
- Error message clarity

## 3. Test Coverage

### 3.1 Backend DAO Tests

| Component | Test Cases | Coverage |
|-----------|-----------|----------|
| UserDAO | 40 | Create (23), Read (5), Update (5), Delete (3), Custom (4) |
| VideoDAO | 50 | Create (28), Read (4), Update (5), Delete (3), Custom (10) |
| FavoriteDAO | 20 | Create (5), Read (6), Delete (2), Count (2), Advanced (5) |
| ShareDAO | 23 | Create (8), Read (5), Delete (2), Count (2), Advanced (6) |
| **Total** | **133** | **Complete CRUD + Business Logic** |

### 3.2 UI Tests

| Component | Test Cases | Coverage |
|-----------|-----------|----------|
| Login UI | 10 | Page load, authentication, validation, session |
| Video Browsing UI | 15 | Display, search, pagination, interactions |
| Video Management UI | 15 | Admin CRUD, form validation, navigation |
| **Total** | **40** | **Complete User Workflows** |

### 3.3 Field Validation Coverage

#### User Entity
- **id**: VARCHAR(20), NOT NULL, UNIQUE
  - Tests: null, empty, 1 char, 20 chars, 21 chars, duplicate
- **password**: VARCHAR(50), NOT NULL
  - Tests: null, empty, 50 chars, 51 chars
- **fullName**: VARCHAR(50), NOT NULL
  - Tests: null, empty, 50 chars, 51 chars
- **email**: VARCHAR(50), NOT NULL, UNIQUE
  - Tests: null, invalid format, valid, 50 chars, 51 chars, duplicate
- **admin**: BOOLEAN, NOT NULL
  - Tests: true, false

#### Video Entity
- **id**: VARCHAR(11), NOT NULL, UNIQUE
  - Tests: null, empty, <11, 11 chars, >11, duplicate
- **title**: VARCHAR(255), NOT NULL
  - Tests: null, empty, 1 char, 255 chars, 256 chars
- **posterUrl**: VARCHAR(255), NOT NULL
  - Tests: null, empty, 255 chars, 256 chars
- **description**: LONGTEXT, nullable
  - Tests: null, empty, very long (10000 chars)
- **active**: BOOLEAN, NOT NULL
  - Tests: true, false
- **views**: INT, NOT NULL
  - Tests: 0, negative, large numbers
- **link**: VARCHAR(255), NOT NULL
  - Tests: null, empty, 255 chars, 256 chars

#### Favorite Entity
- **user**: Foreign Key, NOT NULL
- **video**: Foreign Key, NOT NULL
- **likeDate**: DATE, NOT NULL
- **Unique Constraint**: (user, video)
  - Tests: null fields, duplicate combination, past/future dates

#### Share Entity
- **user**: Foreign Key, NOT NULL
- **video**: Foreign Key, NOT NULL
- **emails**: VARCHAR(50), NOT NULL
  - Tests: null, empty, 50 chars, 51 chars, multiple emails
- **shareDate**: DATE, NOT NULL
  - Tests: null, past/future dates

## 4. Test Environment

### 4.1 Hardware Requirements
- **Development**: Standard development machine
- **CI/CD**: GitHub Actions Ubuntu runner

### 4.2 Software Requirements
- **JDK**: 11 or higher
- **MySQL**: 8.0 or higher
- **Maven**: 3.6+
- **Chrome/Chromium**: Latest version
- **ChromeDriver**: Compatible with browser version

### 4.3 Test Data
- **Users**: 5 test users (user01-user05, admin)
- **Videos**: 21 test videos (V01-V21)
- **Favorites**: Sample favorite data
- **Shares**: Sample share data

### 4.4 Database Setup
```sql
Database: polysys
Character Set: utf8mb4
Collation: utf8mb4_unicode_ci
```

## 5. Test Execution

### 5.1 Test Execution Strategy
1. **Local Development**:
   - Run individual test classes during development
   - Run full test suite before commits
   
2. **Continuous Integration**:
   - Automated test execution on pull requests
   - MySQL service container for database tests
   - Headless browser for UI tests

### 5.2 Test Execution Commands

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserDAOTest
mvn test -Dtest=VideoDAOTest
mvn test -Dtest=FavoriteDAOTest
mvn test -Dtest=ShareDAOTest
mvn test -Dtest=LoginUITest
mvn test -Dtest=VideoBrowsingUITest
mvn test -Dtest=VideoManagementUITest

# Run only DAO tests
mvn test -Dtest=*DAOTest

# Run only UI tests
mvn test -Dtest=*UITest

# Using shell script (recommended)
./run-tests.sh          # All tests
./run-tests.sh backend  # Only backend tests
./run-tests.sh ui       # Only UI tests
```

### 5.3 CI/CD Pipeline
- **Trigger**: Pull request to main branch
- **Steps**:
  1. Checkout code
  2. Setup JDK 11
  3. Start MySQL service
  4. Load database schema and seed data
  5. Build application (skip tests)
  6. Run DAO tests
  7. Upload test reports

## 6. Test Deliverables

### 6.1 Test Documentation
- ✅ TEST-README.md - Overall test documentation
- ✅ UserDAO-TestCases.md - User DAO test cases
- ✅ VideoDAO-TestCases.md - Video DAO test cases
- ✅ FavoriteDAO-TestCases.md - Favorite DAO test cases
- ✅ ShareDAO-TestCases.md - Share DAO test cases
- ✅ LoginUI-TestCases.md - Login UI test cases
- ✅ VideoBrowsing-TestCases.md - Video browsing UI test cases
- ✅ TEST-RESULTS.md - Test execution results

### 6.2 Test Code
- ✅ src/test/java/dao/UserDAOTest.java
- ✅ src/test/java/dao/VideoDAOTest.java
- ✅ src/test/java/dao/FavoriteDAOTest.java
- ✅ src/test/java/dao/ShareDAOTest.java
- ✅ src/test/java/ui/LoginUITest.java
- ✅ src/test/java/ui/VideoBrowsingUITest.java
- ✅ src/test/java/ui/VideoManagementUITest.java

### 6.3 Test Reports
- Maven Surefire Reports (HTML/XML/TXT)
- TestNG Reports (HTML)
- CI/CD Artifacts (uploaded to GitHub Actions)

## 7. Entry and Exit Criteria

### 7.1 Entry Criteria
- ✅ Database schema created
- ✅ Test data loaded
- ✅ Application builds successfully
- ✅ Test environment configured
- ✅ All test code compiles

### 7.2 Exit Criteria
- ✅ All test cases executed
- ✅ Pass rate ≥ 85% for backend tests
- ✅ Pass rate ≥ 75% for UI tests
- ✅ All critical defects resolved
- ✅ Test reports generated

## 8. Defect Management

### 8.1 Defect Severity Levels
- **Critical**: System crash, data loss, security breach
- **High**: Major feature not working
- **Medium**: Feature works with issues
- **Low**: Cosmetic issues, minor bugs

### 8.2 Expected Test Failures
Some tests are designed to fail as they test constraint violations:
- Tests with field length > max (expected: Exception)
- Tests with NULL on NOT NULL fields (expected: Exception)
- Tests with duplicate unique keys (expected: EntityExistsException)

These are **expected failures** that validate system constraints.

## 9. Risk Management

### 9.1 Testing Risks

| Risk | Impact | Probability | Mitigation |
|------|--------|-------------|------------|
| Database not available | High | Low | Auto-setup in CI/CD |
| UI element changes | Medium | Medium | Flexible element locators |
| Browser compatibility | Medium | Low | Use standard ChromeDriver |
| Test data conflicts | Low | Medium | Independent test data |
| CI/CD timeout | Medium | Low | Optimize test execution |

## 10. Test Schedule

### 10.1 Test Phases
1. **Unit Testing**: Ongoing during development
2. **Integration Testing**: After DAO implementation
3. **UI Testing**: After frontend implementation
4. **Regression Testing**: Before each release
5. **CI/CD Testing**: On every pull request

## 11. Responsibilities

### 11.1 Test Team Roles
- **Test Lead**: Overall test strategy and coordination
- **Backend Tester**: DAO tests development and execution
- **Frontend Tester**: UI tests development and execution
- **DevOps**: CI/CD pipeline setup and maintenance

## 12. Tools and Technologies

### 12.1 Testing Frameworks
- **TestNG 7.8.0**: Test execution framework
- **Selenium 4.36.0**: Web UI automation
- **JUnit 5.10.2**: Alternative testing framework
- **Maven Surefire 3.2.5**: Test runner plugin

### 12.2 Development Tools
- **Maven**: Build automation
- **Git**: Version control
- **GitHub Actions**: CI/CD
- **IntelliJ IDEA / Eclipse**: IDE

## 13. Conclusion

This comprehensive test plan ensures thorough testing coverage of the PolySys system. With 173 total test cases covering backend and frontend functionality, the system is well-tested for reliability, security, and usability.

### 13.1 Test Coverage Summary
- ✅ **Backend**: 133 tests covering all DAO operations
- ✅ **Frontend**: 40 tests covering user workflows
- ✅ **Techniques**: Black-box, boundary, equivalence, decision table
- ✅ **Automation**: TestNG + Selenium + CI/CD
- ✅ **Documentation**: Comprehensive test case documentation

---

**Document Version**: 1.0
**Last Updated**: 2025-10-20
**Status**: Active
