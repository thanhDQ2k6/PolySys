# Video Browsing UI Test Cases Documentation

## Overview
This document contains comprehensive UI test cases for Video Browsing features using Selenium WebDriver and TestNG.

## Test Coverage
- **Total Test Cases**: 15
- **Coverage Areas**: Video list, detail view, search, navigation, responsive design

## Test Cases

| ID | Test Case Name | Description | Input | Expected Result | Priority |
|----|---------------|-------------|-------|-----------------|----------|
| VB-001 | testVideoListPageLoads | Verify video list page loads | Navigate to /video/list | Page loads successfully | High |
| VB-002 | testVideoListDisplaysVideos | Verify videos are displayed | Load video list | Video cards visible | High |
| VB-003 | testVideoDetailLink | Click on video to view details | Click video link | Navigate to detail page | High |
| VB-004 | testVideoSearch | Search for videos by keyword | Search "Russian" | Filtered results shown | Medium |
| VB-005 | testVideoPagination | Navigate through video pages | Click "Next" button | Navigate to next page | Medium |
| VB-006 | testFavoriteButton | Add/remove video from favorites | Click favorite button | Favorite status updated | High |
| VB-007 | testShareButton | Share video functionality | Click share button | Share modal/page shown | Medium |
| VB-008 | testVideoPlayerPresence | Verify video player on detail page | Navigate to video detail | YouTube iframe present | High |
| VB-009 | testVideoStatistics | Verify statistics display | View video detail | View count, likes visible | Low |
| VB-010 | testCategoryFilter | Filter videos by category | Select category | Filtered videos shown | Low |
| VB-011 | testResponsiveLayout | Verify responsive design | Resize to 375x667 | Layout adapts to mobile | Medium |
| VB-012 | testVideoGridLayout | Verify video grid display | Load video list | Videos in grid layout | Medium |
| VB-013 | testNavigationMenu | Verify navigation menu | Load page | Nav menu with links present | Low |
| VB-014 | testFooterPresence | Verify footer exists | Load page | Footer element present | Low |
| VB-015 | testSortingFunctionality | Sort videos by criteria | Select sort option | Videos re-ordered | Low |

## Testing Techniques Applied

### 1. Functional Testing
- Feature availability
- User interactions
- Data display

### 2. Usability Testing
- Navigation flow
- Layout and design
- Responsive behavior

### 3. Integration Testing
- Video player integration (YouTube)
- Database integration (video data)
- Session integration (favorites)

## Test Flow

### Video List Flow
```
Login → Navigate to /video/list → Videos displayed → Click video → Detail page
```

### Search Flow
```
Video list → Enter search term → Submit → Filtered results
```

### Favorite Flow
```
Video list → Click favorite button → Status updated → Visible in favorites
```

## Selenium Techniques Used

### Element Location Strategies
```java
By.className("video-card")        // By class name
By.name("search")                  // By name attribute
By.cssSelector("a[href*='detail']") // By CSS selector
By.linkText("Next")               // By link text
By.tagName("iframe")              // By tag name
```

### Explicit Waits
```java
wait.until(ExpectedConditions.urlContains("video"));
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("player")));
```

## Screen Resolutions Tested
- Desktop: 1920x1080
- Tablet: 768x1024
- Mobile: 375x667

## Prerequisites
- Application server running at http://localhost:8080/PolySys
- User logged in (uses helper method)
- Videos available in database
- Chrome/Chromium browser

## Notes
- Tests login before browsing (authenticated feature)
- Tests are flexible to different page structures
- Tests print messages when features not found (may not be implemented)
- Responsive tests verify layout adaptation
