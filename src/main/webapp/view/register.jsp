<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Form</title>
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

        .register-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            padding: 30px;
            transition: all 0.3s ease;
        }

        .register-container:hover {
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
        }

        .register-header {
            text-align: center;
            margin-bottom: 25px;
            color: #2c3e50;
        }

        .register-header h2 {
            font-weight: 700;
            margin-bottom: 5px;
            font-size: 1.8rem;
        }

        .register-header p {
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

        .btn-register {
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

        .btn-register:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(52, 152, 219, 0.4);
        }

        .btn-register:active {
            transform: translateY(1px);
        }

        .validation-message {
            font-size: 0.9rem;
            color: #e74c3c;
            margin-top: 8px;
            height: 20px;
        }
    </style>
</head>
<body>
<div class="register-container">
    <div class="register-header">
        <h2>Register</h2>
    </div>

    <form id="registerForm">
        <div class="mb-4">
            <label for="fullname" class="form-label">Full Name</label>
            <input
                    type="text"
                    class="form-control"
                    id="fullname"
                    placeholder="Enter your full name"
                    required
            >
        </div>

        <div class="mb-4">
            <label for="username" class="form-label">Username</label>
            <input
                    type="text"
                    class="form-control"
                    id="username"
                    placeholder="Enter your username"
                    required
            >
        </div>

        <div class="mb-4">
            <label for="email" class="form-label">Email</label>
            <input
                    type="email"
                    class="form-control"
                    id="email"
                    placeholder="Enter your email"
                    required
            >
        </div>

        <div class="mb-4">
            <label for="password" class="form-label">Password</label>
            <input
                    type="password"
                    class="form-control"
                    id="password"
                    placeholder="Enter your password"
                    required
            >
        </div>

        <button type="submit" class="btn btn-register">
            <i class="fas fa-user-plus me-2"></i>Register
        </button>
    </form>
</div>

<script>
    document.getElementById('registerForm').addEventListener('submit', function (e) {
        e.preventDefault();
        // Add your registration logic here
        alert('Registration submitted!');
    });
</script>
</body>
</html>