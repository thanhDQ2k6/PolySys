# UserDAOImpl Test Suite

## Overview
This directory contains comprehensive tests for the **UserDAOImpl** class, which is the most notable and important data access object in the PolySys application. It handles all CRUD operations for User entities.

## Test File
- **UserDAOImplTest.java** - Contains 25 comprehensive test cases

## Test Coverage

The test suite includes the following categories:

### 1. FindById Tests (4 tests)
- Test with null ID
- Test with empty string ID
- Test with non-existing ID
- Test with special characters in ID

### 2. FindByEmail Tests (4 tests)
- Test with null email
- Test with empty string email
- Test with non-existing email
- Test with invalid email format

### 3. ExistsByEmail Tests (3 tests)
- Test with null email
- Test with empty string email
- Test with non-existing email

### 4. Count Tests (2 tests)
- Test that count returns non-negative number
- Test count consistency

### 5. FindAll Tests (2 tests)
- Test that findAll returns non-null list
- Test that findAll count matches count() method

### 6. Create Tests (3 tests)
- Test creating null user
- Test creating user with null ID
- Test creating user with empty ID

### 7. Update Tests (2 tests)
- Test updating null user
- Test updating non-existing user

### 8. Delete Tests (3 tests)
- Test deleting with null ID
- Test deleting with empty ID
- Test deleting non-existing user

### 9. Integration Tests (2 tests)
- Test UserDAO instance initialization
- Test EntityManager initialization

## Running the Tests

To run all tests:
```bash
mvn test
```

To run only UserDAOImpl tests:
```bash
mvn test -Dtest=UserDAOImplTest
```

To run a specific test:
```bash
mvn test -Dtest=UserDAOImplTest#testFindById_NonExistingId
```

## Test Features

- **Easy to Notice**: Clear test names with @DisplayName annotations
- **Simple**: Each test focuses on a single scenario
- **Many Test Cases**: 25 comprehensive test cases covering all methods
- **Well-Organized**: Tests are ordered and grouped by functionality
- **Database-Safe**: Tests gracefully handle missing database configuration
- **Professional**: Uses JUnit 5 best practices

## Requirements

- JUnit 5 (Jupiter) 5.10.2
- Maven Surefire Plugin 3.2.5
- Java 9+
- Database configuration (optional - tests will skip if unavailable)
