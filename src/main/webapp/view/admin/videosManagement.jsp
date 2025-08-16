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
            <li><a href="#"><i class="fas fa-video"></i><span>Videos</span></a></li>
            <li><a href="#"><i class="fas fa-users"></i><span>Users</span></a></li>
            <li><a href="#"><i class="fas fa-chart-bar"></i><span>Analytics</span></a></li>
            <li><a href="${pageContext.request.contextPath}/account/logout"><i class="fas fa-sign-out-alt"></i><span>Logout</span></a></li>
        </ul>
    </nav>
</div>

<main class="main-content">
    <div class="tabs">
        <button class="tab active" onclick="showTab('videoEdition')">
            <i class="fas fa-video"></i> Video Edition
        </button>
        <button class="tab" onclick="showTab('videoList')">
            <i class="fas fa-list"></i> Video List
        </button>
    </div>

    <!-- Video Edition Section -->
    <section id="videoEdition" class="section">
        <h3><i class="fas fa-film"></i> Video Management</h3>
        <form id="videoForm">
            <input type="hidden" id="videoId" name="videoId">

            <div class="form-group">
                <label for="youtubeUrl" class="form-label">YouTube URL</label>
                <input type="text" id="youtubeUrl" name="youtubeUrl" class="form-control" placeholder="https://www.youtube.com/watch?v=VIDEO_ID" required onchange="extractVideoId()">
                <input type="hidden" id="youtubeId" name="youtubeId">
                <small class="form-text text-muted">Paste the full YouTube URL. The video ID will be extracted automatically.</small>
            </div>

            <div class="form-group">
                <label for="videoTitle" class="form-label">Video Title</label>
                <input type="text" id="videoTitle" name="videoTitle" class="form-control" required>
            </div>

            <fieldset>
                <legend>Status</legend>
                <div class="radio-group">
                    <div class="radio-option">
                        <input type="radio" id="statusActive" name="status" value="active" checked>
                        <label for="statusActive">Active</label>
                    </div>
                    <div class="radio-option">
                        <input type="radio" id="statusInactive" name="status" value="inactive">
                        <label for="statusInactive">Inactive</label>
                    </div>
                </div>
            </fieldset>

            <div class="form-group">
                <label for="description" class="form-label">Description</label>
                <textarea id="description" name="description" class="form-control" rows="4"></textarea>
            </div>

            <div class="btn-group">
                <button class="btn btn-success" type="button" onclick="createVideo()">
                    <i class="fas fa-plus-circle"></i> Create
                </button>
                <button class="btn btn-primary" type="button" onclick="updateVideo()">
                    <i class="fas fa-save"></i> Update
                </button>
                <button class="btn btn-danger" type="button" onclick="deleteVideo()">
                    <i class="fas fa-trash"></i> Delete
                </button>
                <button class="btn btn-outline" type="reset" onclick="resetVideoForm()">
                    <i class="fas fa-undo"></i> Reset
                </button>
            </div>
        </form>
    </section>

    <!-- Video List Section remains the same -->
    <section id="videoList" class="section hidden">
        <h3><i class="fas fa-list-ol"></i> Video List</h3>
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>Video ID</th>
                    <th>Title</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="video" items="${videoList}">
                    <tr>
                        <td>${video.id}</td>
                        <td>${video.title}</td>
                        <td>
                            <span class="badge ${video.active ? 'badge-success' : 'badge-secondary'}">
                                    ${video.active ? 'Active' : 'Inactive'}
                            </span>
                        </td>
                        <td class="table-actions">
                            <button class="btn btn-primary" onclick="loadVideoForEdit(
                                    '${video.id}',
                                    '${video.title}',
                                    '${video.active}',
                                    '${video.description}',
                                    '${video.link}'
                                    )">
                                <i class="fas fa-edit"></i> Edit
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="pagination">
            <div class="pagination-info">
                Showing ${videoList.size()} of ${totalVideos} videos
            </div>
            <div class="pagination-controls">
                <button class="btn btn-outline" onclick="previousPage()">
                    <i class="fas fa-chevron-left"></i> Previous
                </button>
                <button class="btn btn-outline" onclick="nextPage()">
                    Next <i class="fas fa-chevron-right"></i>
                </button>
            </div>
        </div>
    </section>
</main>

<script>
    // Show/hide tabs - remains the same
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

    // Load video data into form for editing
    function loadVideoForEdit(id, title, active, description, link) {
        document.getElementById('videoId').value = id;
        document.getElementById('videoTitle').value = title;
        document.getElementById('description').value = description;
        
        // Extract video ID from YouTube URL or use the id directly
        const youtubeUrl = link || `https://www.youtube.com/watch?v=${id}`;
        document.getElementById('youtubeUrl').value = youtubeUrl;
        document.getElementById('youtubeId').value = id;

        if (active) {
            document.getElementById('statusActive').checked = true;
        } else {
            document.getElementById('statusInactive').checked = true;
        }

        showTab('videoEdition');
    }

    // Extract YouTube video ID from URL
    function extractVideoId() {
        const url = document.getElementById('youtubeUrl').value;
        const regex = /(?:youtube\.com\/watch\?v=|youtu\.be\/|youtube\.com\/embed\/)([^&\n?#]+)/;
        const match = url.match(regex);
        
        if (match) {
            const videoId = match[1];
            document.getElementById('youtubeId').value = videoId;
            console.log('Extracted video ID:', videoId);
        } else {
            document.getElementById('youtubeId').value = '';
            console.log('Invalid YouTube URL');
        }
    }

    // Reset video form
    function resetVideoForm() {
        document.getElementById('videoForm').reset();
        document.getElementById('videoId').value = '';
        document.getElementById('youtubeId').value = '';
        document.getElementById('statusActive').checked = true;
    }

    // Create new video
    function createVideo() {
        const videoId = document.getElementById('videoId').value;

        if (videoId) {
            alert('Please reset the form to create a new video');
            return;
        }

        // Get form data
        const formData = {
            youtubeUrl: document.getElementById('youtubeUrl').value,
            youtubeId: document.getElementById('youtubeId').value,
            videoTitle: document.getElementById('videoTitle').value,
            status: document.querySelector('input[name="status"]:checked').value,
            description: document.getElementById('description').value
        };

        // Validate required fields
        if (!formData.youtubeUrl || !formData.youtubeId || !formData.videoTitle) {
            alert('Please fill in all required fields and ensure valid YouTube URL');
            return;
        }

        // Here you would typically make an AJAX call to your backend
        console.log('Creating new video:', formData);

        // Simulate success
        alert('Video created successfully!');
        resetVideoForm();
    }

    // Update existing video
    function updateVideo() {
        const videoId = document.getElementById('videoId').value;

        if (!videoId) {
            alert('No video selected to update. Please edit an existing video first.');
            return;
        }

        // Get form data
        const formData = {
            id: videoId,
            youtubeUrl: document.getElementById('youtubeUrl').value,
            youtubeId: document.getElementById('youtubeId').value,
            videoTitle: document.getElementById('videoTitle').value,
            status: document.querySelector('input[name="status"]:checked').value,
            description: document.getElementById('description').value
        };

        // Here you would typically make an AJAX call to your backend
        console.log('Updating video:', formData);

        // Simulate success
        alert('Video updated successfully!');
    }

    // Delete video - remains the same
    function deleteVideo() {
        const videoId = document.getElementById('videoId').value;

        if (!videoId) {
            alert('No video selected to delete');
            return;
        }

        if (confirm('Are you sure you want to delete this video?')) {
            // Here you would typically make an AJAX call to your backend
            console.log('Deleting video with ID:', videoId);

            // Simulate success
            alert('Video deleted successfully!');
            resetVideoForm();
        }
    }

    // Pagination functions - remain the same
    function previousPage() {
        console.log('Previous page');
    }

    function nextPage() {
        console.log('Next page');
    }

    function toggleSidebar() {
        const sidebar = document.getElementById('sidebar');
        sidebar.classList.toggle('collapsed');
    }
</script>
</body>
</html>