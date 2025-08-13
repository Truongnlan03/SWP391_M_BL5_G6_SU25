<%-- 
    Document   : login
    Created on : Aug 13, 2025, 3:40:57 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập - TopJobVN</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="Assets/css/login.css" rel="stylesheet">
        <style>
            .btn-group-custom {
                display: flex;
                gap: 10px;
            }

            .btn-group-custom .btn {
                width: 50%;
            }
        </style>
    </head>
    <body>
        <div class="card">
            <div class="welcome-text">
                <h4>Chào mừng bạn đã quay trở lại</h4>
                <p>Cùng xây dựng một hồ sơ nổi bật và nhận được các cơ hội sự nghiệp lý tưởng</p>
            </div>
            <form action="login" method="post">
                <div class="mb-4">
                    <label class="form-label">Tên đăng nhập</label>
                    <input type="text" name="username" class="form-control" placeholder="Tên đăng nhập" required>
                </div>
                <div class="mb-4">
                    <label class="form-label">Mật khẩu</label>
                    <input type="password" name="password" class="form-control" placeholder="Mật khẩu" required>
                </div>

                <div class="radio-group mb-4">
                    <div>
                        <input type="radio" id="job-seeker" name="role" value="job-seeker" required>
                        <label for="job-seeker">Ứng viên tìm việc</label>
                    </div>
                    <div>
                        <input type="radio" id="recruiter" name="role" value="recruiter">
                        <label for="recruiter">Nhà tuyển dụng</label>
                    </div>
                    <div>
                        <input type="radio" id="admin" name="role" value="admin">
                        <label for="admin">Admin</label>
                    </div>
                </div>

                <button type="submit" class="btn btn-green w-100 mb-3">Đăng nhập</button>
                <div class="btn-group-custom">
                    <a href="register" class="btn btn-secondary-custom">Đăng ký</a>
                    <a href="home" class="btn btn-secondary-custom">Trang chủ</a>
                </div>

                <div class="text-center mt-3">
                    <a href="reset_password.jsp">Quên mật khẩu?</a>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger mt-3" role="alert">
                        <c:choose>
                            <c:when test="${error == 'Vui lòng điền đầy đủ thông tin đăng nhập'}">
                                Vui lòng nhập đầy đủ thông tin đăng nhập.
                            </c:when>
                            <c:when test="${error == 'Tên người dùng, mật khẩu hoặc trạng thái tài khoản không hợp lệ'}">
                                Thông tin đăng nhập không hợp lệ hoặc tài khoản chưa được kích hoạt.
                            </c:when>
                            <c:otherwise>
                                ${error}
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </form>
        </div>
        <script>
            document.querySelector('form').addEventListener('submit', function (e) {
                const userType = document.querySelector('input[name="role"]:checked').value;
                sessionStorage.setItem('userType', userType);
            });
        </script>
    </body>
</html>