<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .edit-profile-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            padding: 30px;
            transition: all 0.3s ease;
        }

        .edit-profile-container:hover {
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
        }

        .edit-profile-header {
            text-align: center;
            margin-bottom: 25px;
            color: #2c3e50;
        }

        .edit-profile-header h2 {
            font-weight: 700;
            margin-bottom: 5px;
            font-size: 1.8rem;
        }

        .edit-profile-header p {
            color: #7f8c8d;
            font-size: 1.1rem;
        }

        .form-label {
            font-weight: 600;
            color: #34495e;
            margin-bottom: 8px;
        }

        .form-control {
            padding: 14px 18px;
            border-radius: 10px;
            border: 2px solid #e0e6ed;
            transition: all 0.3s;
            font-size: 1rem;
        }

        .form-control:focus {
            border-color: #3498db;
            box-shadow: 0 0 0 0.25rem rgba(52, 152, 219, 0.25);
        }

        .form-check {
            margin-bottom: 20px;
        }

        .form-check-input:checked {
            background-color: #3498db;
            border-color: #3498db;
        }

        .form-check-label {
            font-weight: 600;
            color: #34495e;
            cursor: pointer;
        }

        .btn-save {
            background: linear-gradient(135deg, #3498db, #8e44ad);
            border: none;
            padding: 12px 30px;
            font-weight: 600;
            font-size: 1.1rem;
            border-radius: 10px;
            transition: all 0.3s ease;
            width: 100%;
            box-shadow: 0 4px 15px rgba(52, 152, 219, 0.3);
        }

        .btn-save:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(52, 152, 219, 0.4);
        }

        .btn-save:active {
            transform: translateY(1px);
        }

        /* CSS cho div thông báo lỗi */
        .error-message {
            background-color: #ffebee; /* Màu hồng nhạt */
            border: 1px solid #ef9a9a; /* Viền đỏ nhẹ */
            color: #b71c1c; /* Màu chữ đỏ đậm */
            padding: 10px 15px; /* Đệm bên trong */
            border-radius: 5px; /* Bo tròn góc */
            margin-bottom: 15px; /* Khoảng cách với các phần tử khác */
            margin-top: 45px;
            font-size: 0.9em;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="edit-profile-container">
    <div class="edit-profile-header">
        <h2>Edit Profile</h2>
    </div>

    <form id="editProfileForm" method="POST" action="${pageContext.request.contextPath}/account/editprofile">
        <div class="mb-4">
            <label for="fullname" class="form-label">Full Name</label>
            <input
                    name="fullname"
                    type="text"
                    class="form-control"
                    id="fullname"
                    placeholder="Enter your full name"
                    required
            >
        </div>

<%--        <div class="mb-4">--%>
<%--            <label for="username" class="form-label">Username</label>--%>
<%--            <input--%>
<%--                    name="username"--%>
<%--                    type="text"--%>
<%--                    class="form-control"--%>
<%--                    id="username"--%>
<%--                    placeholder="Enter your username"--%>
<%--                    required--%>
<%--            >--%>
<%--        </div>--%>

        <div class="mb-4">
            <label for="email" class="form-label">Email</label>
            <input
                    name="email"
                    type="email"
                    class="form-control"
                    id="email"
                    placeholder="Enter your email"
                    required
            >
        </div>

        <div class="mb-4">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="changePassword" onchange="togglePasswordSection()">
                <label class="form-check-label" for="changePassword">
                    Change Password
                </label>
            </div>
        </div>

        <div id="passwordSection" style="display: none;">
            <div class="mb-4">
                <label for="currentPassword" class="form-label">Current Password</label>
                <input
                        name="currentPassword"
                        type="password"
                        class="form-control"
                        id="currentPassword"
                        placeholder="Enter your current password"
                >
            </div>

            <div class="mb-4">
                <label for="newPassword" class="form-label">New Password</label>
                <input
                        name="newPassword"
                        type="password"
                        class="form-control"
                        id="newPassword"
                        placeholder="Enter your new password"
                        minlength="6"
                >
            </div>

            <div class="mb-4">
                <label for="confirmPassword" class="form-label">Confirm New Password</label>
                <input
                        name="confirmPassword"
                        type="password"
                        class="form-control"
                        id="confirmPassword"
                        placeholder="Confirm your new password"
                        minlength="6"
                >
            </div>
        </div>

        <button type="submit" class="btn btn-save">
            <i class="fas fa-save me-2"></i>Save Changes
        </button>

        <c:if test="${not empty error}">
            <div class="error-message">
                    ${error}
            </div>
        </c:if>
    </form>
</div>

<script>
    function togglePasswordSection() {
        const passwordSection = document.getElementById('passwordSection');
        const changePasswordCheckbox = document.getElementById('changePassword');
        const currentPassword = document.getElementById('currentPassword');
        const newPassword = document.getElementById('newPassword');
        const confirmPassword = document.getElementById('confirmPassword');
        
        if (changePasswordCheckbox.checked) {
            passwordSection.style.display = 'block';
            currentPassword.required = true;
            newPassword.required = true;
            confirmPassword.required = true;
        } else {
            passwordSection.style.display = 'none';
            currentPassword.required = false;
            newPassword.required = false;
            confirmPassword.required = false;
            // Clear password fields when hiding
            currentPassword.value = '';
            newPassword.value = '';
            confirmPassword.value = '';
        }
    }

    document.getElementById('editProfileForm').addEventListener('submit', function (e) {
        const changePasswordCheckbox = document.getElementById('changePassword');
        
        if (changePasswordCheckbox.checked) {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (newPassword !== confirmPassword) {
                e.preventDefault();
                alert('New password and confirmation password do not match!');
                return false;
            }
            
            if (newPassword.length < 6) {
                e.preventDefault();
                alert('New password must be at least 6 characters long!');
                return false;
            }
        }
        
        alert('Profile update request submitted!');
    });
</script>
</body>
</html>