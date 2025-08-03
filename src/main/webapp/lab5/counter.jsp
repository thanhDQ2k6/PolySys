<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đếm số khách truy cập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container text-center mt-5">
    <div class="card p-4 mx-auto" style="max-width: 500px;">
        <h1 class="card-title">Chào mừng bạn!</h1>
        <p class="card-text fs-4">
            Đây là trang web đếm số lượng khách truy cập hệ thống.
        </p>
        <hr>
        <p class="card-text fs-5">
            Tổng số lượt truy cập:
            <strong class="text-primary">${applicationScope.visitors}</strong>
        </p>
    </div>
</div>
</body>
</html>
