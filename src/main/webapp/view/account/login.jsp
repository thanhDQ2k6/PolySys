<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
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

        .login-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            padding: 30px;
            transition: all 0.3s ease;
        }

        .login-container:hover {
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
        }

        .login-header {
            text-align: center;
            margin-bottom: 25px;
            color: #2c3e50;
        }

        .login-header h2 {
            font-weight: 700;
            margin-bottom: 5px;
            font-size: 1.8rem;
        }

        .login-header p {
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

        .btn-login {
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

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(52, 152, 219, 0.4);
        }

        .btn-login:active {
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
<div class="login-container">
    <div class="login-header">
        <h2>Login</h2>
    </div>

    <form id="loginForm" method="post" action="${pageContext.request.contextPath}/login">
        <div class="mb-4">
            <label for="username" class="form-label">Username</label>
            <input
                    name="username"
                    type="text"
                    class="form-control"
                    id="username"
                    placeholder="Enter your username"
                    required
            >
        </div>

        <div class="mb-4">
            <label for="password" class="form-label">Password</label>
            <input
                    name="password"
                    type="password"
                    class="form-control"
                    id="password"
                    placeholder="Enter your password"
                    required
            >
        </div>

        <div class="mb-4 form-check">
            <input
                    type="checkbox"
                    class="form-check-input"
                    id="rememberMe"
            >
            <label class="form-check-label" for="rememberMe">Remember me?</label>
        </div>

        <button type="submit" class="btn btn-login">
            <i class="fas fa-sign-in-alt me-2"></i>Login
        </button>

        <c:if test="${not empty error}">
            <div class="error-message">
                    ${error}
            </div>
        </c:if>
    </form>
</div>
</body>
</html>