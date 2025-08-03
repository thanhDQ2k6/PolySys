<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video Gallery | Online Entertainment</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">

    <c:url var="css" value="vidgrid.css" />
    <link rel="stylesheet" href="${css}">
</head>
<body>
<!-- Professional Navbar -->
<nav class="navbar navbar-expand-lg navbar-light sticky-top" style="background-color: #FFC107;">
    <div class="container-fluid">
        <!-- Brand with generous left padding -->
        <a class="navbar-brand text-danger me-5" href="#">
            <i class="bi bi-play-btn-fill me-2"></i>Online Entertainment
        </a>

        <!-- Toggler button for mobile -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navbar items with proper spacing -->
        <div class="collapse navbar-collapse" id="mainNavbar">
            <ul class="navbar-nav me-auto">
                <!-- Spacer to push items to the right -->
                <li class="nav-item me-auto"></li>

                <!-- My Favorites -->
                <li class="nav-item">
                    <a class="nav-link text-dark" href="#">
                        <i class="bi bi-heart-fill"></i>My Favorites
                    </a>
                </li>

                <!-- My Account Dropdown -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-dark" href="#" id="accountDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-circle"></i>My Account
                    </a>
                    <!-- Vertical dropdown menu -->
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="accountDropdown">
                        <li><a class="dropdown-item" href="#"><i class="bi bi-box-arrow-in-right"></i>Login</a></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-key"></i>Forgot Password</a></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-person-plus"></i>Registration</a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-box-arrow-left"></i>Logoff</a></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-lock"></i>Change Password</a></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-pencil-square"></i>Edit Profile</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Video Gallery Content -->
<div class="gallery-container">
    <div class="gallery-header">
        <div>
            <h2 class="gallery-title">Featured Videos</h2>
        </div>
        <div class="search-container">
            <div class="input-group" style="width: 300px;">
                <input type="text" class="form-control" placeholder="Search videos...">
                <button class="btn btn-primary" type="button">
                    <i class="bi bi-search"></i>
                </button>
            </div>
        </div>
    </div>

    <!-- Video Grid with 4 columns -->
    <div class="video-grid">
        <!-- Video Card 1 -->
        <div class="video-card">
            <div class="video-thumb">
                <i class="bi bi-play-btn"></i>
                <span class="video-duration">8:24</span>
            </div>
            <div class="video-content">
                <h5 class="video-title">Introduction to MySQL Database Design</h5>
                <div class="video-actions">
                    <button class="btn btn-like">
                        <i class="bi bi-heart"></i> Like
                    </button>
                    <button class="btn btn-share">
                        <i class="bi bi-share"></i> Share
                    </button>
                </div>
            </div>
        </div>

        <!-- Video Card 2 -->
        <div class="video-card">
            <div class="video-thumb">
                <i class="bi bi-play-btn"></i>
                <span class="video-duration">12:45</span>
            </div>
            <div class="video-content">
                <h5 class="video-title">Advanced SQL Queries Explained</h5>
                <div class="video-actions">
                    <button class="btn btn-like">
                        <i class="bi bi-heart"></i> Like
                    </button>
                    <button class="btn btn-share">
                        <i class="bi bi-share"></i> Share
                    </button>
                </div>
            </div>
        </div>

        <!-- Video Card 3 -->
        <div class="video-card">
            <div class="video-thumb">
                <i class="bi bi-play-btn"></i>
                <span class="video-duration">15:32</span>
            </div>
            <div class="video-content">
                <h5 class="video-title">Database Optimization Techniques</h5>
                <div class="video-actions">
                    <button class="btn btn-like">
                        <i class="bi bi-heart"></i> Like
                    </button>
                    <button class="btn btn-share">
                        <i class="bi bi-share"></i> Share
                    </button>
                </div>
            </div>
        </div>

        <!-- Video Card 4 -->
        <div class="video-card">
            <div class="video-thumb">
                <i class="bi bi-play-btn"></i>
                <span class="video-duration">10:18</span>
            </div>
            <div class="video-content">
                <h5 class="video-title">Security Best Practices for Databases</h5>
                <div class="video-actions">
                    <button class="btn btn-like">
                        <i class="bi bi-heart"></i> Like
                    </button>
                    <button class="btn btn-share">
                        <i class="bi bi-share"></i> Share
                    </button>
                </div>
            </div>
        </div>

        <!-- Video Card 5 -->
        <div class="video-card">
            <div class="video-thumb">
                <i class="bi bi-play-btn"></i>
                <span class="video-duration">18:07</span>
            </div>
            <div class="video-content">
                <h5 class="video-title">Cloud Database Management with AWS</h5>
                <div class="video-actions">
                    <button class="btn btn-like active">
                        <i class="bi bi-heart-fill"></i> Liked
                    </button>
                    <button class="btn btn-share">
                        <i class="bi bi-share"></i> Share
                    </button>
                </div>
            </div>
        </div>

        <!-- Video Card 6 -->
        <div class="video-card">
            <div class="video-thumb">
                <i class="bi bi-play-btn"></i>
                <span class="video-duration">14:22</span>
            </div>
            <div class="video-content">
                <h5 class="video-title">Data Migration Strategies</h5>
                <div class="video-actions">
                    <button class="btn btn-like">
                        <i class="bi bi-heart"></i> Like
                    </button>
                    <button class="btn btn-share">
                        <i class="bi bi-share"></i> Share
                    </button>
                </div>
            </div>
        </div>

        <!-- Video Card 7 -->
        <div class="video-card">
            <div class="video-thumb">
                <i class="bi bi-play-btn"></i>
                <span class="video-duration">9:41</span>
            </div>
            <div class="video-content">
                <h5 class="video-title">Working with JSON in MySQL</h5>
                <div class="video-actions">
                    <button class="btn btn-like">
                        <i class="bi bi-heart"></i> Like
                    </button>
                    <button class="btn btn-share">
                        <i class="bi bi-share"></i> Share
                    </button>
                </div>
            </div>
        </div>

        <!-- Video Card 8 -->
        <div class="video-card">
            <div class="video-thumb">
                <i class="bi bi-play-btn"></i>
                <span class="video-duration">11:29</span>
            </div>
            <div class="video-content">
                <h5 class="video-title">Full-Text Search Implementation</h5>
                <div class="video-actions">
                    <button class="btn btn-like active">
                        <i class="bi bi-heart-fill"></i> Liked
                    </button>
                    <button class="btn btn-share">
                        <i class="bi bi-share"></i> Share
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Pagination Navigation -->
    <div class="pagination-nav">
        <div class="pagination-buttons">
            <button class="btn btn-nav">
                <i class="bi bi-chevron-double-left"></i>
            </button>
            <button class="btn btn-nav">
                <i class="bi bi-chevron-left"></i>
            </button>
            <button class="btn btn-nav active">1</button>
            <button class="btn btn-nav">2</button>
            <button class="btn btn-nav">3</button>
            <button class="btn btn-nav">
                <i class="bi bi-chevron-right"></i>
            </button>
            <button class="btn btn-nav">
                <i class="bi bi-chevron-double-right"></i>
            </button>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="footer">
    <div class="container">
        <div class="row">
            <div class="col-md-4 mb-4">
                <h5 class="text-light mb-4">Online Entertainment</h5>
                <p>Your premier destination for educational videos and entertainment content. Learn, discover, and
                    enjoy.</p>
            </div>
            <div class="col-md-2 mb-4">
                <h5 class="text-light mb-4">Quick Links</h5>
                <ul class="list-unstyled">
                    <li class="mb-2"><a href="#" class="text-light text-decoration-none">Home</a></li>
                    <li class="mb-2"><a href="#" class="text-light text-decoration-none">Videos</a></li>
                    <li class="mb-2"><a href="#" class="text-light text-decoration-none">Categories</a></li>
                    <li class="mb-2"><a href="#" class="text-light text-decoration-none">About Us</a></li>
                </ul>
            </div>
            <div class="col-md-2 mb-4">
                <h5 class="text-light mb-4">Account</h5>
                <ul class="list-unstyled">
                    <li class="mb-2"><a href="#" class="text-light text-decoration-none">Profile</a></li>
                    <li class="mb-2"><a href="#" class="text-light text-decoration-none">Favorites</a></li>
                    <li class="mb-2"><a href="#" class="text-light text-decoration-none">History</a></li>
                    <li class="mb-2"><a href="#" class="text-light text-decoration-none">Settings</a></li>
                </ul>
            </div>
            <div class="col-md-4 mb-4">
                <h5 class="text-light mb-4">Subscribe</h5>
                <p>Get notified about new content and features</p>
                <div class="input-group">
                    <input type="email" class="form-control" placeholder="Your email address">
                    <button class="btn btn-warning" type="button">Subscribe</button>
                </div>
            </div>
        </div>
        <hr class="border-light">
        <div class="row">
            <div class="col-md-6 mb-3 mb-md-0">
                <p class="mb-0">&copy; 2023 Online Entertainment. All rights reserved.</p>
            </div>
            <div class="col-md-6 text-md-end">
                <a href="#" class="text-light me-3"><i class="bi bi-facebook"></i></a>
                <a href="#" class="text-light me-3"><i class="bi bi-twitter"></i></a>
                <a href="#" class="text-light me-3"><i class="bi bi-instagram"></i></a>
                <a href="#" class="text-light"><i class="bi bi-youtube"></i></a>
            </div>
        </div>
    </div>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Add interactivity to like buttons
    document.querySelectorAll('.btn-like').forEach(button => {
        button.addEventListener('click', function () {
            const isActive = this.classList.contains('active');
            if (isActive) {
                this.classList.remove('active');
                this.innerHTML = '<i class="bi bi-heart"></i> Like';
            } else {
                this.classList.add('active');
                this.innerHTML = '<i class="bi bi-heart-fill"></i> Liked';
            }
        });
    });
</script>
</body>
</html>