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
            <li><a href="${pageContext.request.contextPath}/video/list"><i class="fas fa-home"></i><span>Dashboard</span></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/video"><i class="fas fa-video"></i><span>Videos</span></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/user"><i class="fas fa-users"></i><span>Users</span></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/reports"><i class="fas fa-chart-bar"></i><span>Reports</span></a></li>
            <li><a href="${pageContext.request.contextPath}/account/logout"><i class="fas fa-sign-out-alt"></i><span>Logout</span></a></li>
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
                    <th>Total Favorites</th>
                    <th>Latest Favorite Date</th>
                    <th>Oldest Favorite Date</th>
                </tr>
                </thead>
                <tbody id="favoritesTableBody">
                    <!-- Data will be loaded dynamically -->
                    <tr>
                        <td colspan="4" class="text-center">
                            <i class="fas fa-spinner fa-spin"></i> Loading favorites data...
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="pagination">
            <div class="pagination-info">
                <span id="favoritesCount">Loading...</span>
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
                <!-- Options will be loaded dynamically -->
            </select>
        </div>

        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Favorite Date</th>
                    <th>Video Title</th>
                </tr>
                </thead>
                <tbody id="favoriteUsersTableBody">
                    <tr>
                        <td colspan="5" class="text-center">
                            <i class="fas fa-spinner fa-spin"></i> Loading users data...
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="pagination">
            <div class="pagination-info">
                <span id="favoriteUsersCount">Loading...</span>
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
                <!-- Options will be loaded dynamically -->
            </select>
        </div>

        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>Sender Name</th>
                    <th>Sender Email</th>
                    <th>Receiver Email</th>
                    <th>Share Date</th>
                    <th>Video Title</th>
                </tr>
                </thead>
                <tbody id="sharedFriendsTableBody">
                    <tr>
                        <td colspan="5" class="text-center">
                            <i class="fas fa-spinner fa-spin"></i> Loading shares data...
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="pagination">
            <div class="pagination-info">
                <span id="sharedFriendsCount">Loading...</span>
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
    // Sample data for demo purposes
    const sampleVideos = [
        { id: 'dQw4w9WgXcQ', title: 'Never Gonna Give You Up' },
        { id: 'L_jWHffIx5E', title: 'Smells Like Teen Spirit' },
        { id: 'fJ9rUzIMcZQ', title: 'Bohemian Rhapsody' },
        { id: 'kffacxfA7G4', title: 'Baby One More Time' },
        { id: 'hTWKbfoikeg', title: 'Somebody That I Used to Know' }
    ];

    const sampleFavorites = [
        { videoTitle: 'Never Gonna Give You Up', count: 15, latest: '2024-01-15', oldest: '2023-12-01' },
        { videoTitle: 'Smells Like Teen Spirit', count: 8, latest: '2024-01-10', oldest: '2023-11-15' },
        { videoTitle: 'Bohemian Rhapsody', count: 12, latest: '2024-01-12', oldest: '2023-10-20' },
        { videoTitle: 'Baby One More Time', count: 6, latest: '2024-01-08', oldest: '2023-12-10' },
        { videoTitle: 'Somebody That I Used to Know', count: 9, latest: '2024-01-14', oldest: '2023-11-28' }
    ];

    const sampleFavoriteUsers = [
        { username: 'john_doe', fullName: 'John Doe', email: 'john@example.com', date: '2024-01-15', videoTitle: 'Never Gonna Give You Up' },
        { username: 'jane_smith', fullName: 'Jane Smith', email: 'jane@example.com', date: '2024-01-14', videoTitle: 'Bohemian Rhapsody' },
        { username: 'mike_wilson', fullName: 'Mike Wilson', email: 'mike@example.com', date: '2024-01-13', videoTitle: 'Smells Like Teen Spirit' },
        { username: 'sarah_johnson', fullName: 'Sarah Johnson', email: 'sarah@example.com', date: '2024-01-12', videoTitle: 'Never Gonna Give You Up' },
        { username: 'david_brown', fullName: 'David Brown', email: 'david@example.com', date: '2024-01-11', videoTitle: 'Baby One More Time' }
    ];

    const sampleShares = [
        { senderName: 'John Doe', senderEmail: 'john@example.com', receiverEmail: 'friend1@example.com', date: '2024-01-15', videoTitle: 'Never Gonna Give You Up' },
        { senderName: 'Jane Smith', senderEmail: 'jane@example.com', receiverEmail: 'friend2@example.com', date: '2024-01-14', videoTitle: 'Bohemian Rhapsody' },
        { senderName: 'Mike Wilson', senderEmail: 'mike@example.com', receiverEmail: 'friend3@example.com', date: '2024-01-13', videoTitle: 'Smells Like Teen Spirit' },
        { senderName: 'Sarah Johnson', senderEmail: 'sarah@example.com', receiverEmail: 'friend4@example.com', date: '2024-01-12', videoTitle: 'Never Gonna Give You Up' },
        { senderName: 'David Brown', senderEmail: 'david@example.com', receiverEmail: 'friend5@example.com', date: '2024-01-11', videoTitle: 'Baby One More Time' }
    ];

    // Initialize page
    document.addEventListener('DOMContentLoaded', function() {
        loadInitialData();
    });

    function loadInitialData() {
        // Load video options for dropdowns
        loadVideoOptions();
        
        // Load initial data for active tab
        setTimeout(() => {
            loadFavoritesData();
            loadFavoriteUsersData();
            loadSharedFriendsData();
        }, 1000); // Simulate loading delay
    }

    function loadVideoOptions() {
        const videoSelect = document.getElementById('videoSelect');
        const sharedVideoSelect = document.getElementById('sharedVideoSelect');
        
        // Clear existing options except "All Videos"
        videoSelect.innerHTML = '<option value="">All Videos</option>';
        sharedVideoSelect.innerHTML = '<option value="">All Videos</option>';
        
        // Add video options
        sampleVideos.forEach(video => {
            const option1 = document.createElement('option');
            option1.value = video.id;
            option1.textContent = video.title;
            videoSelect.appendChild(option1);
            
            const option2 = document.createElement('option');
            option2.value = video.id;
            option2.textContent = video.title;
            sharedVideoSelect.appendChild(option2);
        });
    }

    function loadFavoritesData() {
        const tbody = document.getElementById('favoritesTableBody');
        const count = document.getElementById('favoritesCount');
        
        // Clear loading message
        tbody.innerHTML = '';
        
        // Add data rows
        sampleFavorites.forEach(fav => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${fav.videoTitle}</td>
                <td>${fav.count}</td>
                <td>${fav.latest}</td>
                <td>${fav.oldest}</td>
            `;
            tbody.appendChild(row);
        });
        
        count.textContent = `Showing ${sampleFavorites.length} records`;
    }

    function loadFavoriteUsersData(videoId = '') {
        const tbody = document.getElementById('favoriteUsersTableBody');
        const count = document.getElementById('favoriteUsersCount');
        
        // Filter data based on video selection
        let filteredData = sampleFavoriteUsers;
        if (videoId) {
            const selectedVideo = sampleVideos.find(v => v.id === videoId);
            if (selectedVideo) {
                filteredData = sampleFavoriteUsers.filter(u => u.videoTitle === selectedVideo.title);
            }
        }
        
        // Clear existing data
        tbody.innerHTML = '';
        
        // Add filtered data rows
        filteredData.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.username}</td>
                <td>${user.fullName}</td>
                <td>${user.email}</td>
                <td>${user.date}</td>
                <td>${user.videoTitle}</td>
            `;
            tbody.appendChild(row);
        });
        
        count.textContent = `Showing ${filteredData.length} users`;
    }

    function loadSharedFriendsData(videoId = '') {
        const tbody = document.getElementById('sharedFriendsTableBody');
        const count = document.getElementById('sharedFriendsCount');
        
        // Filter data based on video selection
        let filteredData = sampleShares;
        if (videoId) {
            const selectedVideo = sampleVideos.find(v => v.id === videoId);
            if (selectedVideo) {
                filteredData = sampleShares.filter(s => s.videoTitle === selectedVideo.title);
            }
        }
        
        // Clear existing data
        tbody.innerHTML = '';
        
        // Add filtered data rows
        filteredData.forEach(share => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${share.senderName}</td>
                <td>${share.senderEmail}</td>
                <td>${share.receiverEmail}</td>
                <td>${share.date}</td>
                <td>${share.videoTitle}</td>
            `;
            tbody.appendChild(row);
        });
        
        count.textContent = `Showing ${filteredData.length} shares`;
    }

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
        loadFavoriteUsersData(videoId);
    }

    // Filter shared friends by video selection
    function filterSharedFriends() {
        const videoId = document.getElementById('sharedVideoSelect').value;
        loadSharedFriendsData(videoId);
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

    // Toggle sidebar
    function toggleSidebar() {
        const sidebar = document.getElementById('sidebar');
        sidebar.classList.toggle('collapsed');
    }
</script>
</body>
</html>