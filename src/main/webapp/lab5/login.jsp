<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lab 5 | Bai 1</title>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
          integrity="sha384-k6RqeWeci5ZR/Lv4MR0sA0FfDOMt23cez/3paNdF+e5l5d1z4x2b6c5a7p6j8g9" crossorigin="anonymous">
    <!-- MDB Bootsrap -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css" rel="stylesheet"/>

    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            min-height: 100vh;
            margin: 0;
            background: linear-gradient(to right, #6a11cb 0%, #2575fc 100%); /* Nền gradient hiện đại */
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; /* Font hiện đại */
        }

        .login-box {
            background-color: rgba(255, 255, 255, 0.95); /* Nền trắng hơi trong suốt */
            padding: 45px 50px; /* Tăng padding */
            border-radius: 15px; /* Bo tròn góc nhiều hơn */
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2); /* Đổ bóng rõ hơn */
            width: 100%;
            max-width: 420px; /* Tăng max-width một chút */
            text-align: center;
            animation: fadeIn 0.8s ease-out; /* Hiệu ứng fade in khi tải trang */
        }
    </style>
</head>
<body>
<div class="login-box">
    <c:if test="${!empty sessionScope.user}">
        <p>Chào mừng, ${sessionScope.user.fullName}!</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/lab5/login">
        <!-- Username input -->
        <div data-mdb-input-init class="form-outline mb-4">
            <input type="text" id="uidInput" class="form-control" name="uid" value="${param.uid}"/>
            <label class="form-label" for="uidInput">Username</label>
        </div>

        <!-- Password input -->
        <div data-mdb-input-init class="form-outline mb-4">
            <input type="password" id="pwdInput" class="form-control" name="pwd"/>
            <label class="form-label" for="pwdInput">Password</label>
        </div>

        <!-- Submit button -->
        <button type="submit" class="btn btn-primary btn-block mb-4">Sign in</button>
    </form>

    <c:if test="${error != null}">
        <p class="text-danger">${error}</p>
    </c:if>
</div>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.umd.min.js"></script>
</body>
</html>
