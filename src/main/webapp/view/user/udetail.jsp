<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video Detail | Online Entertainment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        :root {
            --brand-primary: #FFC107;
            --brand-secondary: #212529;
            --text-dark: #0f0f0f;
            --text-light: #606060;
            --border-light: #e0e0e0;
        }

        body {
            background-color: #f9f9f9;
            font-family: 'Roboto', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: var(--text-dark);
        }

        /* Reused styles from previous implementation */
        .navbar {
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .navbar-brand {
            font-size: 1.8rem;
            font-weight: 700;
            letter-spacing: -0.5px;
            padding-left: 2rem;
        }

        .nav-item {
            margin: 0 0.8rem;
        }

        .nav-link {
            font-size: 1.1rem;
            font-weight: 500;
            padding: 0.8rem 1.2rem !important;
            border-radius: 6px;
            transition: all 0.2s ease;
        }

        .nav-link:hover {
            background-color: rgba(0,0,0,0.05);
        }

        .nav-link i {
            margin-right: 8px;
            font-size: 1.2rem;
        }

        .footer {
            background-color: var(--brand-secondary);
            color: rgba(255,255,255,0.7);
            padding: 3rem 0;
            margin-top: 3rem;
        }

        /* New styles for video detail page */
        .video-detail-container {
            max-width: 1400px;
            margin: 2rem auto;
            padding: 0 2rem;
        }

        .video-player-container {
            background-color: #000;
            border-radius: 12px;
            overflow: hidden;
            position: relative;
            padding-top: 56.25%; /* 16:9 Aspect Ratio */
            margin-bottom: 1.5rem;
        }

        .video-player-placeholder {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(45deg, #1a1a2e, #16213e, #0f3460);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
        }

        .video-player-placeholder i {
            font-size: 6rem;
            color: rgba(255,255,255,0.2);
        }

        .video-title {
            font-size: 1.5rem;
            font-weight: 500;
            margin-bottom: 1rem;
        }

        .video-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 1rem;
            margin-bottom: 1.5rem;
            border-bottom: 1px solid var(--border-light);
        }

        .video-stats {
            color: var(--text-light);
            font-size: 0.9rem;
        }

        .video-actions {
            display: flex;
            gap: 1rem;
        }

        .btn-like, .btn-share {
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 0.5rem 1rem;
            border-radius: 20px;
            font-weight: 500;
            transition: all 0.2s ease;
        }

        .btn-like {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
        }

        .btn-like:hover {
            background-color: #fff5f5;
            color: #dc3545;
        }

        .btn-like.active {
            background-color: #dc3545;
            color: white;
        }

        .btn-share {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
        }

        .btn-share:hover {
            background-color: #f0f7ff;
            color: #0d6efd;
        }

        .video-description {
            background-color: #f2f2f2;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 2rem;
            line-height: 1.6;
        }

        .video-description h5 {
            margin-bottom: 1rem;
            font-weight: 500;
        }

        .recommended-videos {
            background-color: white;
            border-radius: 12px;
            padding: 1.5rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .recommended-header {
            font-weight: 500;
            margin-bottom: 1.5rem;
            padding-bottom: 0.5rem;
            border-bottom: 2px solid var(--brand-primary);
        }

        .recommended-item {
            display: flex;
            gap: 1rem;
            margin-bottom: 1rem;
            padding: 0.5rem;
            border-radius: 8px;
            transition: background-color 0.2s ease;
        }

        .recommended-item:hover {
            background-color: #f9f9f9;
        }

        .recommended-thumb {
            flex: 0 0 40%;
            border-radius: 6px;
            overflow: hidden;
            position: relative;
        }

        .recommended-thumb img {
            width: 100%;
            height: auto;
            display: block;
        }

        .recommended-duration {
            position: absolute;
            bottom: 5px;
            right: 5px;
            background: rgba(0,0,0,0.7);
            color: white;
            padding: 2px 6px;
            border-radius: 4px;
            font-size: 0.75rem;
        }

        .recommended-content {
            flex: 1;
        }

        .recommended-title {
            font-weight: 500;
            font-size: 0.95rem;
            margin-bottom: 0.3rem;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .recommended-channel {
            color: var(--text-light);
            font-size: 0.85rem;
            margin-bottom: 0.3rem;
        }

        .recommended-views {
            color: var(--text-light);
            font-size: 0.8rem;
        }

        @media (max-width: 992px) {
            .video-player-container {
                margin-bottom: 2rem;
            }

            .recommended-videos {
                margin-top: 2rem;
            }
        }
    </style>
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
                    <a class="nav-link dropdown-toggle text-dark" href="#" id="accountDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-circle"></i>My Account
                    </a>
                    <!-- Vertical dropdown menu -->
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="accountDropdown">
                        <li><a class="dropdown-item" href="#"><i class="bi bi-box-arrow-in-right"></i>Login</a></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-key"></i>Forgot Password</a></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-person-plus"></i>Registration</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-box-arrow-left"></i>Logoff</a></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-lock"></i>Change Password</a></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-pencil-square"></i>Edit Profile</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Video Detail Content -->
<div class="video-detail-container">
    <div class="row">
        <!-- Main Video Column (2/3 width) -->
        <div class="col-lg-8">
            <div class="video-player-container">
                <div class="video-player-placeholder">
                    <i class="bi bi-play-circle"></i>
                </div>
            </div>

            <h1 class="video-title">Introduction to MySQL Database Design: Best Practices for Developers</h1>

            <div class="video-meta">
                <div class="video-stats">
                    <span>125,450 views</span> •
                    <span>Oct 15, 2023</span>
                </div>
                <div class="video-actions">
                    <button class="btn btn-like active">
                        <i class="bi bi-hand-thumbs-up-fill"></i> 2.5K
                    </button>
                    <button class="btn btn-share">
                        <i class="bi bi-share"></i> Share
                    </button>
                </div>
            </div>

            <div class="video-description">
                <h5>Description</h5>
                <p>In this comprehensive tutorial, we explore the fundamentals of MySQL database design. Learn how to create efficient, scalable database structures that power modern applications.</p>

                <p>Topics covered:</p>
                <ul>
                    <li>Understanding relational database concepts</li>
                    <li>Entity-Relationship modeling</li>
                    <li>Normalization techniques</li>
                    <li>Indexing strategies for performance</li>
                    <li>Best practices for schema design</li>
                </ul>

                <p>Whether you're a beginner or looking to refine your database design skills, this video provides practical insights you can apply to your projects immediately.</p>

                <p class="mt-3">
                    <strong>Tags:</strong>
                    <span class="badge bg-light text-dark">MySQL</span>
                    <span class="badge bg-light text-dark">Database Design</span>
                    <span class="badge bg-light text-dark">SQL</span>
                    <span class="badge bg-light text-dark">Backend Development</span>
                </p>
            </div>
        </div>

        <!-- Recommended Videos Column (1/3 width) -->
        <div class="col-lg-4">
            <div class="recommended-videos">
                <h5 class="recommended-header">Recommended Videos</h5>

                <!-- Recommended Video 1 -->
                <div class="recommended-item">
                    <div class="recommended-thumb">
                        <div style="background: linear-gradient(45deg, #1d2b64, #f8cdda); height: 80px;"></div>
                        <span class="recommended-duration">12:45</span>
                    </div>
                    <div class="recommended-content">
                        <div class="recommended-title">Advanced SQL Queries: Joins, Subqueries and Window Functions</div>
                        <div class="recommended-channel">Database Mastery</div>
                        <div class="recommended-views">84K views • 2 weeks ago</div>
                    </div>
                </div>

                <!-- Recommended Video 2 -->
                <div class="recommended-item">
                    <div class="recommended-thumb">
                        <div style="background: linear-gradient(45deg, #614385, #516395); height: 80px;"></div>
                        <span class="recommended-duration">18:30</span>
                    </div>
                    <div class="recommended-content">
                        <div class="recommended-title">Database Indexing Explained: How to Optimize Query Performance</div>
                        <div class="recommended-channel">Dev Insights</div>
                        <div class="recommended-views">142K views • 1 month ago</div>
                    </div>
                </div>

                <!-- Recommended Video 3 -->
                <div class="recommended-item">
                    <div class="recommended-thumb">
                        <div style="background: linear-gradient(45deg, #11998e, #38ef7d); height: 80px;"></div>
                        <span class="recommended-duration">22:15</span>
                    </div>
                    <div class="recommended-content">
                        <div class="recommended-title">MySQL Security Best Practices: Protecting Your Data</div>
                        <div class="recommended-channel">CyberDB</div>
                        <div class="recommended-views">56K views • 3 weeks ago</div>
                    </div>
                </div>

                <!-- Recommended Video 4 -->
                <div class="recommended-item">
                    <div class="recommended-thumb">
                        <div style="background: linear-gradient(45deg, #ff512f, #dd2476); height: 80px;"></div>
                        <span class="recommended-duration">14:20</span>
                    </div>
                    <div class="recommended-content">
                        <div class="recommended-title">Understanding Database Normalization: 1NF, 2NF, 3NF with Examples</div>
                        <div class="recommended-channel">Data Wizards</div>
                        <div class="recommended-views">98K views • 2 months ago</div>
                    </div>
                </div>

                <!-- Recommended Video 5 -->
                <div class="recommended-item">
                    <div class="recommended-thumb">
                        <div style="background: linear-gradient(45deg, #1a2980, #26d0ce); height: 80px;"></div>
                        <span class="recommended-duration">9:45</span>
                    </div>
                    <div class="recommended-content">
                        <div class="recommended-title">Stored Procedures in MySQL: A Complete Guide</div>
                        <div class="recommended-channel">Backend Simplified</div>
                        <div class="recommended-views">67K views • 3 weeks ago</div>
                    </div>
                </div>

                <!-- Recommended Video 6 -->
                <div class="recommended-item">
                    <div class="recommended-thumb">
                        <div style="background: linear-gradient(45deg, #ff9966, #ff5e62); height: 80px;"></div>
                        <span class="recommended-duration">16:10</span>
                    </div>
                    <div class="recommended-content">
                        <div class="recommended-title">Database Transactions and ACID Properties Explained</div>
                        <div class="recommended-channel">Data Foundations</div>
                        <div class="recommended-views">112K views • 1 month ago</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="footer">
    <div class="container">
        <div class="row">
            <div class="col-md-4 mb-4">
                <h5 class="text-light mb-4">Online Entertainment</h5>
                <p>Your premier destination for educational videos and entertainment content. Learn, discover, and enjoy.</p>
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
        button.addEventListener('click', function() {
            const isActive = this.classList.contains('active');
            if (isActive) {
                this.classList.remove('active');
                this.innerHTML = '<i class="bi bi-hand-thumbs-up"></i> Like';
            } else {
                this.classList.add('active');
                this.innerHTML = '<i class="bi bi-hand-thumbs-up-fill"></i> Liked';
            }
        });
    });
</script>
</body>
</html>