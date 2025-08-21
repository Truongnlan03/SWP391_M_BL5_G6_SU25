<%-- 
    Document   : admin_add_jobseeker
    Created on : Aug 20, 2025, 11:45:00 PM
    Author     : DELL
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thêm người tìm việc mới</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/admin_dashboard.css">
        <style>
            .form-row {
                display: flex;
                justify-content: space-between;
                gap: 20px;
            }
            .form-group.half-width {
                flex: 1;
            }
        </style>
    </head>

    <body>
        <div class="dashboard-container">
            <jsp:include page="sidebar.jsp" />

            <div class="main-content">
                <div class="page-header">
                    <h1>Thêm người tìm việc mới</h1>
                    <div class="header-actions">
                        <a href="AdminController?target=JobSeeker" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i>
                            Quay lại danh sách
                        </a>
                    </div>
                </div>

                <div class="form-container">
                    <form action="AdminController" method="POST">
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="username">Username:</label>
                                <input type="text" id="username" name="username" class="form-control">
                            </div>
                            <div class="form-group half-width">
                                <label for="password">Mật khẩu:</label>
                                <input type="password" id="password" name="password" class="form-control">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="email">Email:</label>
                                <input type="email" id="email" name="email" class="form-control">
                            </div>
                            <div class="form-group half-width">
                                <label for="fullName">Họ và Tên:</label>
                                <input type="text" id="fullName" name="fullName" class="form-control">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="phone">Điện thoại:</label>
                                <input type="text" id="phone" name="phone" class="form-control">
                            </div>
                            <div class="form-group half-width">
                                <label for="dateOfBirth">Ngày sinh:</label>
                                <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="gender">Giới tính</label>
                                <select id="gender" name="gender" class="form-control">
                                    <option value="Male">Nam</option>
                                    <option value="Female">Nữ</option>
                                    <option value="Other">Khác</option>
                                </select>
                            </div>
                            <div class="form-group half-width">
                                <label for="address">Địa chỉ:</label>
                                <input type="text" id="address" name="address" class="form-control">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="profilePicture">Ảnh đại diện:</label>
                                <input type="text" id="profilePicture" name="profilePicture" class="form-control">
                            </div>
                            <div class="form-group half-width">
                                <label for="cvFile">File CV:</label>
                                <input type="text" id="cvFile" name="cvFile" class="form-control">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="skills">Kỹ năng:</label>
                                <textarea id="skills" name="skills" class="form-control"></textarea>
                            </div>
                            <div class="form-group half-width">
                                <label for="experienceYears">Số năm kinh nghiệm:</label>
                                <input type="text" id="experienceYears" name="experienceYears" class="form-control">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="education">Trình độ học vấn:</label>
                                <input type="text" id="education" name="education" class="form-control">
                            </div>
                            <div class="form-group half-width">
                                <label for="desiredJobTitle">Vị trí công việc mong muốn:</label>
                                <input type="text" id="desiredJobTitle" name="desiredJobTitle" class="form-control">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="desiredSalary">Mức lương mong muốn:</label>
                                <input type="text" id="desiredSalary" name="desiredSalary" class="form-control">
                            </div>
                            <div class="form-group half-width">
                                <label for="jobCategory">Ngành nghề mong muốn:</label>
                                <input type="text" id="jobCategory" name="jobCategory" class="form-control">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="preferredLocation">Địa điểm làm việc mong muốn:</label>
                                <input type="text" id="preferredLocation" name="preferredLocation" class="form-control">
                            </div>
                            <div class="form-group half-width">
                                <label for="careerLevel">Cấp bậc nghề nghiệp (Entry-level, Mid-level, Senior...):</label>
                                <input type="text" id="careerLevel" name="careerLevel" class="form-control">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="workType">Hình thức làm việc (Full-time, Part-time, Remote, Hybrid...):</label>
                                <input type="text" id="workType" name="workType" class="form-control">
                            </div>
                            <div class="form-group half-width">
                                <label for="profileSummary">Tóm tắt hồ sơ cá nhân:</label>
                                <textarea id="profileSummary" name="profileSummary" class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="portfolioUrl">Link portfolio (dự án cá nhân, sản phẩm đã làm)</label>
                                <input type="text" id="portfolioUrl" name="portfolioUrl" class="form-control">
                            </div>
                            <div class="form-group half-width">
                                <label for="languages">Ngôn ngữ biết sử dụng:</label>
                                <input type="text" id="languages" name="languages" class="form-control">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group half-width">
                                <label for="createdAt">Ngày tạo hồ sơ:</label>
                                <input type="date" id="createdAt" name="createdAt" class="form-control">
                            </div>
                            <div class="form-group half-width">
                                <label for="updatedAt">Ngày cập nhật hồ sơ:</label>
                                <input type="date" id="updatedAt" name="updatedAt" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="isActive">Trạng thái:</label>
                            <div class="radio-group">
                                <label><input type="radio" name="isActive" value="true" checked> Hoạt động</label>
                                <label><input type="radio" name="isActive" value="false"> Không hoạt động</label>
                            </div>
                        </div>

                        <div class="text-center mt-3">
                            <button type="submit" name="submit" value="Add JobSeeker" class="btn btn-primary">
                                <i class="fas fa-user-plus"></i> Thêm
                            </button>
                            <button type="reset" class="btn btn-secondary">
                                <i class="fas fa-redo"></i> Đặt lại
                            </button>
                            <input type="hidden" name="service" value="Add">
                            <input type="hidden" name="target" value="JobSeeker">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>