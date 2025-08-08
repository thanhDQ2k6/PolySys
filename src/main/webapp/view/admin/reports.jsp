<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration Panel</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        :root {
            --primary-color: #3498db;
            --primary-dark: #2980b9;
            --sidebar-bg: #2c3e50;
            --sidebar-hover: #34495e;
            --text-light: #ecf0f1;
            --text-dark: #343a40;
            --border-color: #dee2e6;
            --shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            --shadow-hover: 0 4px 12px rgba(0, 0, 0, 0.15);
            --transition: all 0.3s ease;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #f8f9fa;
            color: var(--text-dark);
            line-height: 1.6;
            transition: margin-left 0.3s ease;
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar Styles */
        .sidebar {
            width: 250px;
            background-color: var(--sidebar-bg);
            color: var(--text-light);
            height: 100vh;
            position: fixed;
            transition: width 0.3s ease;
            box-shadow: 3px 0 8px rgba(0, 0, 0, 0.2);
            z-index: 1000;
            display: flex;
            flex-direction: column;
        }

        .sidebar.collapsed {
            width: 70px;
        }

        .sidebar-header {
            padding: 20px;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }

        .sidebar h2 {
            font-size: 1.5rem;
            margin-bottom: 0;
            white-space: nowrap;
            transition: opacity 0.3s;
        }

        .sidebar.collapsed h2 {
            opacity: 0;
            height: 0;
            overflow: hidden;
        }

        .sidebar-nav {
            flex: 1;
            padding: 20px;
            overflow-y: auto;
        }

        .sidebar ul {
            list-style: none;
        }

        .sidebar ul li {
            margin: 15px 0;
        }

        .sidebar ul li a {
            color: var(--text-light);
            text-decoration: none;
            display: flex;
            align-items: center;
            padding: 12px 10px;
            border-radius: 4px;
            transition: background-color 0.3s;
            white-space: nowrap;
        }

        .sidebar ul li a:hover {
            background-color: var(--sidebar-hover);
        }

        .sidebar ul li a i {
            margin-right: 12px;
            font-size: 1.2rem;
            min-width: 24px;
            text-align: center;
        }

        .sidebar.collapsed ul li a span {
            opacity: 0;
            height: 0;
            width: 0;
            overflow: hidden;
            display: inline-block;
        }

        /* Main Content Styles */
        .main-content {
            flex: 1;
            margin-left: 250px;
            transition: margin-left 0.3s ease;
            padding: 30px;
            background-color: #fff;
            min-height: 100vh;
        }

        .sidebar.collapsed ~ .main-content {
            margin-left: 70px;
        }

        /* Tabs */
        .tabs {
            display: flex;
            border-bottom: 2px solid var(--border-color);
            margin-bottom: 30px;
        }

        .tab {
            padding: 12px 24px;
            margin-right: 5px;
            background-color: #e9ecef;
            border-radius: 4px 4px 0 0;
            cursor: pointer;
            transition: var(--transition);
            font-weight: 500;
            border: none;
            outline: none;
        }

        .tab:hover {
            background-color: #ced4da;
        }

        .tab.active {
            background-color: var(--primary-color);
            color: #fff;
            position: relative;
        }

        .tab.active::after {
            content: '';
            position: absolute;
            bottom: -2px;
            left: 0;
            width: 100%;
            height: 2px;
            background-color: var(--primary-color);
        }

        /* Form and Table Sections */
        .section {
            background-color: #fff;
            border: 1px solid var(--border-color);
            border-radius: 8px;
            padding: 25px;
            margin-bottom: 30px;
            box-shadow: var(--shadow);
            transition: var(--transition);
        }

        .section:hover {
            box-shadow: var(--shadow-hover);
        }

        .section h3 {
            margin-bottom: 20px;
            color: var(--primary-dark);
            font-size: 1.4rem;
        }

        /* Form Elements */
        .form-group {
            margin-bottom: 20px;
        }

        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--text-dark);
        }

        .form-control {
            width: 100%;
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            transition: border-color 0.3s;
        }

        .form-control:focus {
            border-color: var(--primary-color);
            outline: none;
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.2);
        }

        textarea.form-control {
            resize: vertical;
            min-height: 120px;
        }

        fieldset {
            border: 1px solid var(--border-color);
            border-radius: 4px;
            padding: 15px;
            margin-bottom: 15px;
        }

        legend {
            font-weight: 500;
            padding: 0 10px;
            color: var(--text-dark);
        }

        .radio-group {
            display: flex;
            gap: 20px;
            margin-top: 10px;
        }

        .radio-option {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        /* Buttons */
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            transition: var(--transition);
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }

        .btn-primary {
            background-color: var(--primary-color);
            color: #fff;
        }

        .btn-primary:hover {
            background-color: var(--primary-dark);
        }

        .btn-outline {
            background-color: transparent;
            border: 1px solid var(--primary-color);
            color: var(--primary-color);
        }

        .btn-outline:hover {
            background-color: rgba(52, 152, 219, 0.1);
        }

        .btn-danger {
            background-color: #e74c3c;
            color: #fff;
        }

        .btn-danger:hover {
            background-color: #c0392b;
        }

        .btn-group {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        .toggle-button {
            width: 100%;
            padding: 12px;
            background-color: var(--primary-color);
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-bottom: 20px;
            transition: var(--transition);
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .toggle-button:hover {
            background-color: var(--primary-dark);
        }

        /* Table Styles */
        .table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .table th,
        .table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid var(--border-color);
        }

        .table th {
            background-color: #f1f3f5;
            font-weight: 600;
            color: var(--text-dark);
        }

        .table tr:hover {
            background-color: #f8f9fa;
        }

        .table-actions {
            display: flex;
            gap: 8px;
        }

        /* Pagination */
        .pagination {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px;
        }

        .pagination-info {
            color: #6c757d;
        }

        .pagination-controls {
            display: flex;
            gap: 10px;
        }

        /* Utility Classes */
        .hidden {
            display: none !important;
        }

        @media (max-width: 768px) {
            .sidebar {
                width: 70px;
            }

            .sidebar h2,
            .sidebar ul li a span {
                opacity: 0;
                height: 0;
                overflow: hidden;
            }

            .main-content {
                margin-left: 70px;
            }
        }
    </style>
</head>
<body>
<div class="sidebar" id="sidebar">
    <div class="sidebar-header">
        <button class="toggle-button" onclick="toggleSidebar()">
            <i class="fas fa-bars"></i>
        </button>
        <h2>Admin Panel</h2>
    </div>
    <nav class="sidebar-nav">
        <ul>
            <li><a href="#"><i class="fas fa-home"></i><span>Dashboard</span></a></li>
            <li><a href="#"><i class="fas fa-video"></i><span>Videos</span></a></li>
            <li><a href="#"><i class="fas fa-users"></i><span>Users</span></a></li>
            <li><a href="#"><i class="fas fa-chart-bar"></i><span>Analytics</span></a></li>
            <li><a href="#"><i class="fas fa-cog"></i><span>Settings</span></a></li>
        </ul>
    </nav>
</div>

<main class="main-content">
    <div class="tabs">
        <button class="tab active" onclick="showTab('favoritesTab')">
            <i class="fas fa-heart"></i> Favorites
        </button>
        <button class="tab" onclick="showTab('favoriteUsersTab')">
            <i class="fas fa-user-friends"></i> Favorite Users
        </button>
        <button class="tab" onclick="showTab('sharedFriendsTab')">
            <i class="fas fa-share-alt"></i> Shared Friends
        </button>
    </div>

    <!-- Favorites Tab -->
    <section id="favoritesTab" class="section">
        <h3><i class="fas fa-heart"></i> Favorites Report</h3>

        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>Video Title</th>
                    <th>Favorites Count</th>
                    <th>Latest Day</th>
                    <th>Oldest Day</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="favorite" items="${favoritesReport}">
                    <tr>
                        <td>${favorite.videoTitle}</td>
                        <td>${favorite.favoritesCount}</td>
                        <td>${favorite.latestDay}</td>
                        <td>${favorite.oldestDay}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="pagination">
            <div class="pagination-info">
                Showing ${favoritesReport.size()} of ${totalFavorites} records
            </div>
            <div class="pagination-controls">
                <button class="btn btn-outline" onclick="previousFavoritesPage()">
                    <i class="fas fa-chevron-left"></i> Previous
                </button>
                <button class="btn btn-outline" onclick="nextFavoritesPage()">
                    Next <i class="fas fa-chevron-right"></i>
                </button>
            </div>
        </div>
    </section>

    <!-- Favorite Users Tab -->
    <section id="favoriteUsersTab" class="section hidden">
        <h3><i class="fas fa-user-friends"></i> Favorite Users Report</h3>

        <div class="form-group">
            <label for="videoSelect" class="form-label">Select Video:</label>
            <select id="videoSelect" class="form-control" onchange="filterFavoriteUsers()">
                <option value="">All Videos</option>
                <c:forEach var="video" items="${videos}">
                    <option value="${video.id}">${video.title}</option>
                </c:forEach>
            </select>
        </div>

        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Favorite Day</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${favoriteUsers}">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.fullName}</td>
                        <td>${user.email}</td>
                        <td>${user.favoriteDay}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="pagination">
            <div class="pagination-info">
                Showing ${favoriteUsers.size()} of ${totalFavoriteUsers} users
            </div>
            <div class="pagination-controls">
                <button class="btn btn-outline" onclick="previousFavoriteUsersPage()">
                    <i class="fas fa-chevron-left"></i> Previous
                </button>
                <button class="btn btn-outline" onclick="nextFavoriteUsersPage()">
                    Next <i class="fas fa-chevron-right"></i>
                </button>
            </div>
        </div>
    </section>

    <!-- Shared Friends Tab -->
    <section id="sharedFriendsTab" class="section hidden">
        <h3><i class="fas fa-share-alt"></i> Shared Friends Report</h3>

        <div class="form-group">
            <label for="sharedVideoSelect" class="form-label">Select Video:</label>
            <select id="sharedVideoSelect" class="form-control" onchange="filterSharedFriends()">
                <option value="">All Videos</option>
                <c:forEach var="video" items="${videos}">
                    <option value="${video.id}">${video.title}</option>
                </c:forEach>
            </select>
        </div>

        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>Sender Name</th>
                    <th>Sender Email</th>
                    <th>Receiver Email</th>
                    <th>Sent Day</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="share" items="${sharedFriends}">
                    <tr>
                        <td>${share.senderName}</td>
                        <td>${share.senderEmail}</td>
                        <td>${share.receiverEmail}</td>
                        <td>${share.sentDay}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="pagination">
            <div class="pagination-info">
                Showing ${sharedFriends.size()} of ${totalSharedFriends} shares
            </div>
            <div class="pagination-controls">
                <button class="btn btn-outline" onclick="previousSharedFriendsPage()">
                    <i class="fas fa-chevron-left"></i> Previous
                </button>
                <button class="btn btn-outline" onclick="nextSharedFriendsPage()">
                    Next <i class="fas fa-chevron-right"></i>
                </button>
            </div>
        </div>
    </section>
</main>

<script>
    // Show/hide tabs
    function showTab(tabName) {
        document.querySelectorAll('.section').forEach(section => {
            section.classList.add('hidden');
        });
        document.getElementById(tabName).classList.remove('hidden');

        document.querySelectorAll('.tab').forEach(tab => {
            tab.classList.remove('active');
        });
        event.currentTarget.classList.add('active');
    }

    // Filter favorite users by video selection
    function filterFavoriteUsers() {
        const videoId = document.getElementById('videoSelect').value;
        console.log('Filtering favorite users by video:', videoId);
        // Here you would typically make an AJAX call to filter the results
    }

    // Filter shared friends by video selection
    function filterSharedFriends() {
        const videoId = document.getElementById('sharedVideoSelect').value;
        console.log('Filtering shared friends by video:', videoId);
        // Here you would typically make an AJAX call to filter the results
    }

    // Pagination functions for Favorites
    function previousFavoritesPage() {
        console.log('Previous favorites page');
        // Implement pagination logic
    }

    function nextFavoritesPage() {
        console.log('Next favorites page');
        // Implement pagination logic
    }

    // Pagination functions for Favorite Users
    function previousFavoriteUsersPage() {
        console.log('Previous favorite users page');
        // Implement pagination logic
    }

    function nextFavoriteUsersPage() {
        console.log('Next favorite users page');
        // Implement pagination logic
    }

    // Pagination functions for Shared Friends
    function previousSharedFriendsPage() {
        console.log('Previous shared friends page');
        // Implement pagination logic
    }

    function nextSharedFriendsPage() {
        console.log('Next shared friends page');
        // Implement pagination logic
    }

    // Toggle sidebar - remains the same
    function toggleSidebar() {
        const sidebar = document.getElementById('sidebar');
        sidebar.classList.toggle('collapsed');
    }
</script>
</body>
</html>