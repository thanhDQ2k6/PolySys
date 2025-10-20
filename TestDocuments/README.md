# TestDocuments Folder

This folder contains all test documentation for the PolySys video management system.

## Quick Start

**Start here:** Read `MasterDocument.md` for comprehensive overview and instructions.

## Document Structure

### Essential Documents

1. **MasterDocument.md** - Master test documentation
   - Complete overview of the test suite
   - Instructions for running tests
   - Test case summaries
   - Requirements and setup

2. **TestTypes.md** - Complete list of all test types
   - Unit testing
   - Integration testing
   - UI testing
   - Black-box testing techniques
   - Security and usability testing

3. **TestPlan.md** - Detailed test plan
   - Test strategy
   - Test coverage details
   - Test environment setup
   - CI/CD integration
   - Entry/exit criteria

4. **TestCases.md** - Test execution results
   - Compilation status
   - Expected results
   - Test coverage summary
   - Black-box testing techniques applied

### Detailed Test Case Documentation

5. **UserDAO-TestCases.md** - 40 test cases for UserDAO
6. **VideoDAO-TestCases.md** - 50 test cases for VideoDAO
7. **FavoriteDAO-TestCases.md** - 20 test cases for FavoriteDAO
8. **ShareDAO-TestCases.md** - 23 test cases for ShareDAO
9. **LoginUI-TestCases.md** - 10 UI test cases for Login
10. **VideoBrowsing-TestCases.md** - 15 UI test cases for Video Browsing

### Execution Script

11. **run-tests.sh** - Automated test execution script
    - Run all tests or specific test suites
    - Supports backend and UI tests separately

## Running Tests

```bash
# From project root, run all tests
./TestDocuments/run-tests.sh

# Run specific test suite
./TestDocuments/run-tests.sh backend
./TestDocuments/run-tests.sh ui
./TestDocuments/run-tests.sh user
./TestDocuments/run-tests.sh video
```

## Test Statistics

- **Total Tests**: 173 (133 backend + 40 UI)
- **Test Techniques**: Equivalence Partitioning, Boundary Value Analysis, Decision Table Testing
- **Automation**: 100% automated with TestNG + Selenium
- **CI/CD**: Integrated with GitHub Actions

## Document Organization

All test documents are now organized in this single folder for:
- ✅ Easy navigation
- ✅ Clear structure
- ✅ Centralized documentation
- ✅ Better maintenance
- ✅ No scattered files in project root

---

**Last Updated**: 2025-10-20  
**Status**: Active
