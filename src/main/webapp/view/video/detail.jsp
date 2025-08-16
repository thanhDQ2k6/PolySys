<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
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
            aspect-ratio: 16/9;
            height: auto;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            justify-content: center;
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
        <a class="navbar-brand text-danger me-5" href="${pageContext.request.contextPath}/video">
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
                    <a class="nav-link text-dark" href="${pageContext.request.contextPath}/video/favorite">
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
                        <c:if test="${sessionScope.user == null}">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/login"><i class="bi bi-box-arrow-in-right"></i>Login</a></li>
                            <%--<li><a class="dropdown-item" href="#"><i class="bi bi-key"></i>Forgot Password</a></li>--%>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/account/signup"><i class="bi bi-person-plus"></i>Registration</a></li>
                        </c:if>

                        <c:if test="${sessionScope.user != null}">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/login?action=logout"><i class="bi bi-box-arrow-left"></i>Logoff</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/account/changepwd"><i class="bi bi-lock"></i>Change Password</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/account/editprofile"><i class="bi bi-pencil-square"></i>Edit Profile</a></li>
                        </c:if>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Video Detail Content -->
<div class="video-detail-container d-flex flex-column align-items-center justify-content-center">
    <!-- Video Player -->
    <div class="video-player-container w-100" style="max-width: 800px;">
        <video id="mainVideo" class="w-100" style="border-radius:12px; background:#000;" controls poster="">
            <source id="videoSource" src="" type="video/mp4">
            Your browser does not support the video tag.
        </video>
    </div>
    <!-- Video Info -->
    <h1 class="video-title mt-4" id="videoTitle">Đang tải...</h1>
    <div class="video-meta mb-3" style="width:100%;max-width:800px;display:flex;justify-content:space-between;align-items:center;">
        <div class="video-actions">
            <button class="btn btn-like" id="likeBtn"><i class="bi bi-hand-thumbs-up"></i></button>
            <button class="btn btn-share" id="shareBtn"><i class="bi bi-share"></i></button>
        </div>
    </div>
    <div class="video-description w-100" style="max-width:800px;">
        <h5>Description</h5>
        <p id="videoDesc">Đang tải mô tả...</p>
    </div>
</div>

<!-- Footer -->
<footer class="footer">
    <div class="container text-center py-4">
        <p class="mb-0">&copy; 2025 Online Entertainment. All rights reserved.</p>
    </div>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // --- Lấy id video từ URL ---
    function getQueryParam(name) {
        const url = new URL(window.location.href);
        return url.searchParams.get(name);
    }
    const videoId = getQueryParam('id');

    // --- Kiểm tra video đã like chưa ---
    let liked = false;
    async function checkLiked() {
        try {
            const res = await fetch("${pageContext.request.contextPath}/video/api/liked");
            if (!res.ok) return false;
            const likedIds = await res.json();
            return likedIds.includes(videoId);
        } catch (e) {
            return false;
        }
    }

    // --- Fetch video detail từ API ---
    async function fetchVideoDetail() {
        if (!videoId) {
            document.getElementById('videoTitle').textContent = 'Không tìm thấy video!';
            return;
        }
        try {
            const res = await fetch(window.location.origin + '${pageContext.request.contextPath}/video/api/detail?id=' + videoId);
            if (!res.ok) throw new Error('Không tìm thấy video!');
            const video = await res.json();
            // --- Fix: Defensive check for video object and YouTube link ---
            if (!video || !video.link) {
                document.getElementById('videoTitle').textContent = 'Không tìm thấy video!';
                document.querySelector('.video-player-container').innerHTML = '';
                document.getElementById('videoDesc').textContent = '';
                return;
            }
            const videoPlayerContainer = document.querySelector('.video-player-container');
            videoPlayerContainer.innerHTML = '';
            // --- YouTube embed: support both youtube.com and youtu.be links ---
            let youtubeId = '';
            if (video.link.includes('youtube.com')) {
                const match = video.link.match(/[?&]v=([^&]+)/);
                if (match) youtubeId = match[1];
            } else if (video.link.includes('youtu.be')) {
                const match = video.link.match(/youtu\.be\/([^?&]+)/);
                if (match) youtubeId = match[1];
            }
            if (youtubeId) {
                videoPlayerContainer.innerHTML = '<iframe width="100%" height="100%" style="aspect-ratio:16/9;border-radius:12px;" src="https://www.youtube.com/embed/' + youtubeId + '" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>';
            } else if (video.link.match(/^https?:\/\/.+\.(mp4|webm|ogg)$/i)) {
                // If it's a direct video file
                videoPlayerContainer.innerHTML = '<video id="mainVideo" class="w-100" style="border-radius:12px; background:#000;" controls poster="' + (video.posterUrl || '') + '"><source id="videoSource" src="' + video.link + '" type="video/mp4">Your browser does not support the video tag.</video>';
            } else {
                videoPlayerContainer.innerHTML = '<div class="video-player-placeholder">Không phát được video này!</div>';
            }
            document.getElementById('videoTitle').textContent = video.title || 'Không tìm thấy video!';
            document.getElementById('videoDesc').textContent = video.description || '';
            // Add share button redirect logic
            const shareBtn = document.getElementById('shareBtn');
            if (shareBtn && video.id) {
                shareBtn.onclick = function() {
                    window.location.href = window.location.origin +
                        '/PolySys_war/video/share?id=' + video.id;
                };
            }
        } catch (e) {
            document.getElementById('videoTitle').textContent = 'Không tìm thấy video!';
            document.querySelector('.video-player-container').innerHTML = '';
            document.getElementById('videoDesc').textContent = '';
        }
    }

    // --- Like button logic ---
    async function updateLikeBtn() {
        liked = await checkLiked();
        const btn = document.getElementById('likeBtn');
        if (liked) {
            btn.classList.add('active');
            btn.innerHTML = '<i class="bi bi-heart-fill"></i>';
        } else {
            btn.classList.remove('active');
            btn.innerHTML = '<i class="bi bi-heart"></i>';
        }
    }
    document.getElementById('likeBtn').addEventListener('click', async function(e) {
        e.preventDefault();
        try {
            const res = await fetch(`${pageContext.request.contextPath}/video/api/like?videoId=" + videoId`, { method: 'POST' });
            if (!res.ok) throw new Error('Lỗi like video');
            const data = await res.json();
            liked = data.liked;
            updateLikeBtn();
        } catch (err) {
            alert('Bạn cần đăng nhập để like video!');
        }
    });

    // --- Khởi động ---
    fetchVideoDetail();
    updateLikeBtn();
</script>
</body>
</html>