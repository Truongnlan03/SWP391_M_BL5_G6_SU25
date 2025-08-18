<%-- 
    Document   : reset_password
    Created on : Aug 17, 2025, 8:16:34 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đặt lại mật khẩu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="Assets/css/reset_password.css">
    </head>
    <body>
        <div class="card">
            <div class="d-flex justify-content-center mb-4">
                <a class="navbar-brand">
                    <img src="Assets/image/logo/logo.png" alt="TopJobVN Logo" height="80">
                </a>
            </div>
            <h4 class="text-center mb-4">Đặt lại mật khẩu</h4>

            <c:if test="${not empty message}">
                <div class="alert alert-success" role="alert">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">${error}</div>
            </c:if>

            <c:choose>
                <c:when test="${not empty token}">
                    <!-- Form to set a new password -->
                    <form action="reset-password" method="post">
                        <input type="hidden" name="token" value="${token}">
                        <div class="mb-3">
                            <input type="password" name="newPassword" class="form-control" placeholder="Mật khẩu mới" required>
                        </div>
                        <div class="mb-3">
                            <input type="password" name="confirmPassword" class="form-control" placeholder="Xác nhận mật khẩu" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 mb-2">Đặt lại mật khẩu</button>                    <a href="login.jsp" class="btn btn-secondary w-100">Hủy</a>
                    </form>
                </c:when>
                <c:otherwise>
                    <!-- Form to request a password reset link -->
                    <form action="reset-password" method="post">
                        <div class="mb-3">
                            <input type="email" name="email" class="form-control" placeholder="Nhập email của bạn" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 mb-2">Gửi liên kết đặt lại</button>
                        <a href="login.jsp" class="btn btn-secondary w-100">Quay về đăng nhập</a>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>