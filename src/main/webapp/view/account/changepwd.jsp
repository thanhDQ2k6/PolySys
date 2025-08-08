<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
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

        .change-password-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            padding: 30px;
            transition: all 0.3s ease;
        }

        .change-password-container:hover {
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
        }

        .change-password-header {
            text-align: center;
            margin-bottom: 25px;
            color: #2c3e50;
        }

        .change-password-header h2 {
            font-weight: 700;
            margin-bottom: 5px;
            font-size: 1.8rem;
        }

        .change-password-header p {
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

        .btn-change {
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

        .btn-change:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(52, 152, 219, 0.4);
        }

        .btn-change:active {
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
<div class="change-password-container">
    <div class="change-password-header">
        <h2>Change Password</h2>
    </div>

    <!-- Khối mã này sẽ hiển thị thông báo lỗi từ Servlet -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
                ${error}
        </div>
    </c:if>

    <form id="changePasswordForm" method="POST" action="${pageContext.request.contextPath}/account/changepwd">

        <div class="mb-4">
            <label for="currentPassword" class="form-label">Current Password</label>
            <input
                    name="pwd"
                    type="password"
                    class="form-control"
                    id="currentPassword"
                    placeholder="Enter your current password"
                    required
            >
        </div>

        <div class="mb-4">
            <label for="newPassword" class="form-label">New Password</label>
            <input
                    name="new1"
                    type="password"
                    class="form-control"
                    id="newPassword"
                    placeholder="Enter your new password"
                    required
            >
        </div>

        <div class="mb-4">
            <label for="confirmNewPassword" class="form-label">Confirm New Password</label>
            <input
                    name="new2"
                    type="password"
                    class="form-control"
                    id="confirmNewPassword"
                    placeholder="Confirm your new password"
                    required
            >
        </div>

        <button type="submit" class="btn btn-change">
            <i class="fas fa-lock me-2"></i>Change Password
        </button>

        <c:if test="${not empty error}">
            <div class="error-message">
                    ${error}
            </div>
        </c:if>
    </form>
</div>

<script>
    document.getElementById('changePasswordForm').addEventListener('submit', function (e) {
        alert('Password change request submitted!');
    });
</script>
</body>
</html>