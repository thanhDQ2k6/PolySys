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

        /* Badge Styles */
        .badge {
            display: inline-block;
            padding: 4px 8px;
            font-size: 0.8rem;
            font-weight: 500;
            border-radius: 4px;
            text-align: center;
            white-space: nowrap;
        }

        .badge-primary {
            background-color: var(--primary-color);
            color: #fff;
        }

        .badge-danger {
            background-color: #e74c3c;
            color: #fff;
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
        <button class="tab active" onclick="showTab('userEdition')">
            <i class="fas fa-user-edit"></i> User Edition
        </button>
        <button class="tab" onclick="showTab('userList')">
            <i class="fas fa-users"></i> User List
        </button>
    </div>

    <!-- User Edition Section -->
    <section id="userEdition" class="section">
        <h3><i class="fas fa-user-cog"></i> User Management</h3>
        
        <!-- Success/Error Messages -->
        <c:if test="${not empty success}">
            <div class="alert alert-success" style="background-color: #d4edda; border: 1px solid #c3e6cb; color: #155724; padding: 12px; border-radius: 4px; margin-bottom: 20px;">
                <i class="fas fa-check-circle"></i> ${success}
            </div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger" style="background-color: #f8d7da; border: 1px solid #f5c6cb; color: #721c24; padding: 12px; border-radius: 4px; margin-bottom: 20px;">
                <i class="fas fa-exclamation-circle"></i> ${error}
            </div>
        </c:if>
        <form id="userForm" method="post" action="${pageContext.request.contextPath}/admin/user">
            <input type="hidden" id="userId" name="userId">
            <input type="hidden" id="action" name="action" value="create">

            <div class="form-group">
                <label for="fullName" class="form-label">Full Name</label>
                <input type="text" id="fullName" name="fullName" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="username" class="form-label">Username</label>
                <input type="text" id="username" name="username" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="email" class="form-label">Email</label>
                <input type="email" id="email" name="email" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="password" class="form-label">Password</label>
                <input type="password" id="password" name="password" class="form-control" required>
                <small class="form-text text-muted">Leave blank to keep current password when editing</small>
            </div>

            <fieldset>
                <legend>Role</legend>
                <div class="radio-group">
                    <div class="radio-option">
                        <input type="radio" id="roleAdmin" name="admin" value="true">
                        <label for="roleAdmin">Admin</label>
                    </div>
                    <div class="radio-option">
                        <input type="radio" id="roleUser" name="admin" value="false" checked>
                        <label for="roleUser">Regular User</label>
                    </div>
                </div>
            </fieldset>

            <div class="btn-group">
                <button class="btn btn-primary" type="submit" onclick="updateUser(event)">
                    <i class="fas fa-save"></i> <span id="submitText">Create</span>
                </button>
                <button class="btn btn-danger" type="button" onclick="deleteUser()">
                    <i class="fas fa-trash"></i> Delete
                </button>
                <button class="btn btn-outline" type="button" onclick="resetUserForm()">
                    <i class="fas fa-user-plus"></i> New User
                </button>
            </div>
        </form>
    </section>

    <!-- User List Section -->
    <section id="userList" class="section hidden">
        <h3><i class="fas fa-users"></i> User List</h3>
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>Full Name</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.fullName}</td>
                        <td>${user.id}</td>
                        <td>${user.email}</td>
                        <td>
                            <span class="badge ${user.admin ? 'badge-danger' : 'badge-primary'}">
                                    ${user.admin ? 'Admin' : 'User'}
                            </span>
                        </td>
                        <td class="table-actions">
                            <button class="btn btn-primary"
                                    onclick="loadUserForEdit('${user.id}', '${user.fullName}', '${user.email}', ${user.admin})">
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
                Showing ${userList.size()} of ${totalUsers} users
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

    // Load user data into form for editing
    function loadUserForEdit(id, fullName, email, isAdmin) {
        document.getElementById('userId').value = id;
        document.getElementById('fullName').value = fullName;
        document.getElementById('username').value = id; // username is the id
        document.getElementById('email').value = email;
        document.getElementById('password').value = '';
        document.getElementById('password').required = false;
        document.getElementById('action').value = 'update';
        document.getElementById('submitText').textContent = 'Update';

        if (isAdmin) {
            document.getElementById('roleAdmin').checked = true;
        } else {
            document.getElementById('roleUser').checked = true;
        }

        showTab('userEdition');
    }

    // Reset user form
    function resetUserForm() {
        document.getElementById('userForm').reset();
        document.getElementById('userId').value = '';
        document.getElementById('password').required = true;
        document.getElementById('action').value = 'create';
        document.getElementById('submitText').textContent = 'Create';
        document.getElementById('username').disabled = false;
    }

    // Update user
    function updateUser(event) {
        event.preventDefault();
        
        const userId = document.getElementById('userId').value;
        const isNewUser = userId === '';
        
        // For existing users, disable username field as it cannot be changed
        if (!isNewUser) {
            document.getElementById('username').disabled = true;
        }

        // Submit the form
        document.getElementById('userForm').submit();
    }

    // Delete user
    function deleteUser() {
        const userId = document.getElementById('userId').value;

        if (!userId) {
            alert('No user selected to delete');
            return;
        }

        if (confirm('Are you sure you want to delete this user?')) {
            // Create a form to submit delete action
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '${pageContext.request.contextPath}/admin/user';
            
            const actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'delete';
            
            const userIdInput = document.createElement('input');
            userIdInput.type = 'hidden';
            userIdInput.name = 'userId';
            userIdInput.value = userId;
            
            form.appendChild(actionInput);
            form.appendChild(userIdInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    // Pagination functions
    function previousPage() {
        console.log('Previous page');
        // Implement pagination logic
    }

    function nextPage() {
        console.log('Next page');
        // Implement pagination logic
    }

    function toggleSidebar() {
        const sidebar = document.getElementById('sidebar');
        sidebar.classList.toggle('collapsed');
    }
</script>
</body>
</html>