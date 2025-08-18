<%-- 
    Document   : register
    Created on : Aug 13, 2025, 9:17:20 PM
    Author     : DELL
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Đăng ký - TopJobVN</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="Assets/css/register.css" rel="stylesheet">
    </head>
    <body>
        <div class="form-container">
            <div class="d-flex justify-content-center mb-4">
                <a class="navbar-brand" href="home">
                    <img src="Assets/image/logo/logo.png" alt="TopJobVN Logo" height="100">
                </a>
            </div>
            <div class="header-section">
                <h4>Chào mừng bạn đến với TopJobVN</h4>
                <p>Cùng xây dựng một hồ sơ nổi bật và nhận được các cơ hội sự nghiệp lý tưởng</p>
            </div>

            <form method="post" action="register" class="row">
                <div class="col-md-6 form-row-custom">
                    <label for="username" class="form-label">Tên đăng nhập</label>
                    <input id="username" class="form-control" name="username" placeholder="Tên đăng nhập" required>
                </div>
                <div class="col-md-6 form-row-custom">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <input id="password" class="form-control" type="password" name="password" placeholder="Mật khẩu" required>
                </div>
                <div class="col-md-6 form-row-custom">
                    <label for="email" class="form-label">Email</label>
                    <input id="email" class="form-control" type="email" name="email" placeholder="Email" required>
                </div>
                <div class="col-md-6 form-row-custom">
                    <label for="fullName" class="form-label">Họ và tên</label>
                    <input id="fullName" class="form-control" name="fullName" placeholder="Họ tên" required>
                </div>
                <div class="col-md-6 form-row-custom">
                    <label for="phone" class="form-label">Số điện thoại</label>
                    <input id="phone" class="form-control" name="phone" placeholder="Số điện thoại" required>
                </div>
                <div class="col-md-6 form-row-custom">
                    <label for="dob" class="form-label">Ngày sinh</label>
                    <input id="dob" class="form-control" name="dob" type="date" required>
                </div>
                <div class="col-md-6 form-row-custom">
                    <label for="gender" class="form-label">Giới tính</label>
                    <select id="gender" class="form-select" name="gender" required>
                        <option value="">-- Chọn giới tính --</option>
                        <option value="Nam">Nam</option>
                        <option value="Nữ">Nữ</option>
                        <option value="Khác">Khác</option>
                    </select>
                </div>
                <div class="col-md-6 form-row-custom">
                    <label for="roleSelect" class="form-label">Vai trò</label>
                    <select id="roleSelect" class="form-select" name="role" required>
                        <option value="">-- Chọn vai trò --</option>
                        <option value="job_seeker">Người tìm việc</option>
                        <option value="recruiter">Nhà tuyển dụng</option>
                    </select>
                </div>
                <div class="col-12 form-row-custom">
                    <label for="address" class="form-label">Địa chỉ</label>
                    <textarea id="address" class="form-control" name="address" placeholder="Địa chỉ" rows="3" required></textarea>
                </div>

                <div id="recruiterFields" class="row" style="display: none;">
                    <div class="col-md-6 form-row-custom">
                        <label for="companyName" class="form-label">Tên công ty</label>
                        <input id="companyName" class="form-control" name="companyName" placeholder="Tên công ty">
                    </div>
                    <div class="col-md-6 form-row-custom">
                        <label for="taxCode" class="form-label">Mã số thuế</label>
                        <input id="taxCode" class="form-control" name="taxCode" placeholder="Mã số thuế">
                    </div>
                    <div class="col-12 form-row-custom">
                        <label for="companyAddress" class="form-label">Địa chỉ công ty</label>
                        <textarea id="companyAddress" class="form-control" name="companyAddress" placeholder="Địa chỉ công ty" rows="3"></textarea>
                    </div>
                    <div class="col-md-6 form-row-custom">
                        <label for="companySize" class="form-label">Quy mô công ty</label>
                        <input id="companySize" class="form-control" name="companySize" placeholder="Quy mô công ty">
                    </div>
                    <div class="col-md-6 form-row-custom">
                        <label for="industry" class="form-label">Ngành nghề</label>
                        <input id="industry" class="form-control" name="industry" placeholder="Ngành nghề">
                    </div>
                    <div class="col-12 form-row-custom">
                        <label for="companyDescription" class="form-label">Mô tả công ty</label>
                        <textarea id="companyDescription" class="form-control" name="companyDescription" placeholder="Mô tả công ty" rows="3"></textarea>
                    </div>
                </div>

                <div id="jobSeekerFields" class="row" style="display: none;">
                    <div class="col-md-6 form-row-custom">
                        <label for="experienceYears" class="form-label">Số năm kinh nghiệm</label>
                        <input id="experienceYears" class="form-control" type="number" name="experienceYears" placeholder="Số năm kinh nghiệm">
                    </div>
                    <div class="col-12 form-row-custom">
                        <label for="skills" class="form-label">Kỹ năng</label>
                        <textarea id="skills" class="form-control" name="skills" placeholder="Kỹ năng của bạn" rows="3"></textarea>
                    </div>
                    <div class="col-12 form-row-custom">
                        <label for="education" class="form-label">Học vấn</label>
                        <textarea id="education" class="form-control" name="education" placeholder="Trình độ học vấn" rows="3"></textarea>
                    </div>
                </div>

                <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-primary w-100 mb-2">Đăng ký</button>
                    <div class="btn-group-custom">
                        <a href="login" class="btn btn-secondary">Đăng nhập</a>
                        <a href="home" class="btn btn-secondary">Trang chủ</a>
                    </div>
                </div>

                <c:if test="${not empty error}">
                    <div class="col-12 mt-3">
                        <div class="alert alert-danger">${error}</div>
                    </div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="col-12 mt-3">
                        <div class="alert alert-success">${message}</div>
                    </div>
                </c:if>
            </form>
        </div>

        <script>
            document.getElementById('roleSelect').addEventListener('change', function () {
                const role = this.value;
                const recruiterFields = document.getElementById('recruiterFields');
                const jobSeekerFields = document.getElementById('jobSeekerFields');

                if (role === 'recruiter') {
                    recruiterFields.style.display = 'flex';
                    jobSeekerFields.style.display = 'none';
                } else if (role === 'job_seeker') {
                    recruiterFields.style.display = 'none';
                    jobSeekerFields.style.display = 'flex';
                } else {
                    recruiterFields.style.display = 'none';
                    jobSeekerFields.style.display = 'none';
                }
            });
        </script>
    </body>
</html>