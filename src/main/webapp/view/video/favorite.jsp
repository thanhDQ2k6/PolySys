<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Favorited Gallery | Online Entertainment</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">

    <style>
        :root {
            --brand-primary: #FFC107;
            --brand-secondary: #212529;
            --card-light: #ffffff;
            --card-dark: #f8f9fa;
        }

        body {
            background-color: #f0f2f5;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .navbar {
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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
            background-color: rgba(0, 0, 0, 0.05);
        }

        .nav-link i {
            margin-right: 8px;
            font-size: 1.2rem;
        }

        .gallery-container {
            max-width: 1400px;
            margin: 3rem auto;
            padding: 0 2rem;
            flex: 1;
        }

        .gallery-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            padding: 0 1.5rem;
        }

        .gallery-title {
            font-weight: 700;
            color: var(--brand-secondary);
            position: relative;
            padding-bottom: 0.5rem;
            padding-left: 1.5rem; /* Thick left padding */
            font-size: 2rem;
        }

        .gallery-title:after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 1.5rem; /* Align with title padding */
            width: 60px;
            height: 4px;
            background: var(--brand-primary);
            border-radius: 2px;
        }

        .video-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr); /* 4 columns */
            gap: 2rem;
            margin-bottom: 3rem;
        }

        .video-card {
            border-radius: 12px;
            overflow: hidden;
            border: none;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            display: flex;
            flex-direction: column;
            height: 100%;
            background: white;
        }

        .video-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
        }

        .video-thumb {
            height: 120px;
            background-color: #e9ecef;
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
            overflow: hidden;
        }

        .video-thumb img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            position: absolute;
            /*top: 0;*/
            /*left: 0;*/
        }

        .video-thumb i {
            font-size: 4rem;
            color: rgba(0, 0, 0, 0.1);
        }

        .video-thumb::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(to bottom, transparent, rgba(0, 0, 0, 0.3));
        }

        .video-duration {
            position: absolute;
            bottom: 10px;
            right: 10px;
            background: rgba(0, 0, 0, 0.7);
            color: white;
            padding: 3px 8px;
            border-radius: 4px;
            font-size: 0.8rem;
        }

        .video-content {
            padding: 1.5rem;
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .video-title {
            font-weight: 600;
            margin-bottom: 1.5rem;
            flex: 1;
            font-size: 1.1rem;
            line-height: 1.4;
        }

        .video-actions {
            display: flex;
            gap: 0.8rem;
            margin-top: auto;
        }

        .btn-like, .btn-share {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            transition: all 0.2s ease;
            padding: 0.5rem;
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

        .pagination-nav {
            display: flex;
            justify-content: center;
            margin: 3rem 0 2rem;
        }

        .pagination-buttons {
            display: flex;
            gap: 0.5rem;
        }

        .btn-nav {
            width: 45px;
            height: 45px;
            border-radius: 50% !important;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: white;
            border: 1px solid #dee2e6;
            transition: all 0.2s ease;
        }

        .btn-nav:hover {
            background-color: var(--brand-primary);
            border-color: var(--brand-primary);
            color: white;
        }

        .search-container {
            padding-right: 1.5rem; /* Thick right padding */
        }

        .footer {
            background-color: var(--brand-secondary);
            color: rgba(255, 255, 255, 0.7);
            padding: 3rem 0;
            margin-top: auto;
        }

        /* Responsive adjustments */
        @media (max-width: 1200px) {
            .video-grid {
                grid-template-columns: repeat(3, 1fr);
            }
        }

        @media (max-width: 992px) {
            .video-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 768px) {
            .gallery-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 1rem;
            }

            .search-container {
                padding-left: 1.5rem;
                padding-right: 0;
                width: 100%;
            }

            .video-grid {
                grid-template-columns: 1fr;
            }

            .gallery-title {
                padding-left: 0;
            }

            .gallery-title:after {
                left: 0;
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
                    <a class="nav-link dropdown-toggle text-dark" href="#" id="accountDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-circle"></i>My Account
                    </a>
                    <!-- Vertical dropdown menu -->
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="accountDropdown">
                        <c:if test="${sessionScope.user == null}">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/login"><i
                                    class="bi bi-box-arrow-in-right"></i>Login</a></li>
                            <%--<li><a class="dropdown-item" href="#"><i class="bi bi-key"></i>Forgot Password</a></li>--%>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/account/signup"><i
                                    class="bi bi-person-plus"></i>Registration</a></li>
                        </c:if>

                        <c:if test="${sessionScope.user != null}">
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/login?action=logout"><i
                                    class="bi bi-box-arrow-left"></i>Logoff</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/account/changepwd"><i
                                    class="bi bi-lock"></i>Change Password</a></li>
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/account/editprofile"><i
                                    class="bi bi-pencil-square"></i>Edit Profile</a></li>
                        </c:if>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Favorited Gallery Content -->
<div class="gallery-container">
    <div class="gallery-header">
        <div>
            <h2 class="gallery-title">Favorited Videos</h2>
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

    <!-- Video Grid with 4 columns (render bằng JS) -->
    <div id="videoGrid" class="video-grid"></div>
    <!-- Pagination Navigation (render bằng JS) -->
    <div class="pagination-nav">
        <div id="paginationButtons" class="pagination-buttons"></div>
    </div>
</div>

<!-- Footer -->
<footer class="footer">
    <div class="container text-center py-4">
        <p class="mb-0">&copy; 2025 Online Entertainment. All rights reserved.</p>
    </div>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"></script>

<script>
    const ITEMS_PER_PAGE = 12;
    let currentPage = 1;
    let videos = [];
    let likedIds = [];

    async function fetchLikedIds() {
        try {
            const res = await fetch("${pageContext.request.contextPath}/video/api/liked");
            if (!res.ok) throw new Error("Lỗi tải danh sách like");
            likedIds = await res.json();
        } catch (e) {
            likedIds = [];
        }
    }

    async function fetchVideos() {
        try {
            const res = await fetch("${pageContext.request.contextPath}/video/api/list");
            if (!res.ok) throw new Error("Lỗi tải video");
            videos = await res.json();
            if (!Array.isArray(videos)) {
                if (videos.data && Array.isArray(videos.data)) {
                    videos = videos.data;
                } else {
                    videos = [];
                }
            }
            await fetchLikedIds();
            // Chỉ giữ lại video đã like
            videos = videos.filter(v => likedIds.includes(v.id));
            renderPage(1);
            renderPagination();
        } catch (e) {
            document.getElementById('videoGrid').innerHTML = '<div class="alert alert-danger">Không thể tải video!</div>';
        }
    }

    function renderPage(page) {
        currentPage = page;
        const start = (page - 1) * ITEMS_PER_PAGE;
        const end = start + ITEMS_PER_PAGE;
        const pageVideos = videos.slice(start, end);
        let html = '';
        if (pageVideos.length === 0) {
            html = '<div class="alert alert-danger">Bạn chưa thích video nào!</div>';
            document.getElementById('videoGrid').innerHTML = html;
            return;
        }
        pageVideos.forEach(function (video) {
            html += '<div class="video-card" data-id="' + video.id + '">';
            html += '<img class="video-thumb" src="' + video.posterUrl + '" alt="' + video.title + '" style="width:100%;height:120px;object-fit:cover;border-radius:8px;display:block;">';
            html += '<div class="video-content">';
            html += '<h5 class="video-title">' + video.title + '</h5>';
            html += '<div class="video-actions">';
            html += '<button class="btn btn-like active"><i class="bi bi-heart-fill"></i></button>';
            html += '<button class="btn btn-share"><i class="bi bi-share"></i></button>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
        });
        document.getElementById('videoGrid').innerHTML = html;
        document.querySelectorAll('.video-card').forEach(card => {
            card.addEventListener('click', function (e) {
                if (!e.target.classList.contains('btn-like') && !e.target.classList.contains('btn-share')) {
                    window.location.href = "${pageContext.request.contextPath}/video/detail?id=" + this.dataset.id;
                }
            });
        });
        document.querySelectorAll('.video-card .btn-like').forEach(btn => {
            btn.addEventListener('click', async function (e) {
                e.stopPropagation();
                const card = this.closest('.video-card');
                const videoId = card.dataset.id;
                try {
                    const res = await fetch(`${pageContext.request.contextPath}/video/api/like?videoId=` + videoId, {method: 'POST'});
                    if (!res.ok) throw new Error('Lỗi like video');
                    const data = await res.json();
                    if (data.liked) {
                        this.classList.add('active');
                        this.querySelector('i').classList.remove('bi-heart');
                        this.querySelector('i').classList.add('bi-heart-fill');
                        this.innerHTML = '<i class="bi bi-heart-fill"></i> Liked';
                    } else {
                        this.classList.remove('active');
                        this.querySelector('i').classList.remove('bi-heart-fill');
                        this.querySelector('i').classList.add('bi-heart');
                        this.innerHTML = '<i class="bi bi-heart"></i> Like';
                    }
                } catch (err) {
                    alert('Bạn cần đăng nhập để like video!');
                }
            });
        });
        // Share button event
        document.querySelectorAll('.video-card .btn-share').forEach(btn => {
            btn.addEventListener('click', function (e) {
                e.stopPropagation();
                const card = this.closest('.video-card');
                const videoId = card.dataset.id;
                const video = videos.find(v => v.id === videoId);
                if (video && video.link) {
                    window.open(video.link, '_blank');
                }
            });
        });
    }

    function renderPagination() {
        const totalPages = Math.ceil(videos.length / ITEMS_PER_PAGE);
        let html = '';
        html += '<button type="button" class="btn btn-nav" onclick="renderPage(1)"><i class="bi bi-chevron-double-left"></i></button>';
        html += '<button type="button" class="btn btn-nav" onclick="renderPage(' + Math.max(1, currentPage - 1) + ')"><i class="bi bi-chevron-left"></i></button>';
        for (let i = 1; i <= totalPages; i++) {
            html += '<button type="button" class="btn btn-nav" onclick="renderPage(' + i + ')">' + i + '</button>';
        }
        html += '<button type="button" class="btn btn-nav" onclick="renderPage(' + Math.min(totalPages, currentPage + 1) + ')"><i class="bi bi-chevron-right"></i></button>';
        html += '<button type="button" class="btn btn-nav" onclick="renderPage(' + totalPages + ')"><i class="bi bi-chevron-double-right"></i></button>';
        document.getElementById('paginationButtons').innerHTML = html;
    }

    fetchVideos();
</script>
</body>
</html>
