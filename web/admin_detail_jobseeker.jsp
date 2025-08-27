<%-- 
    Document   : admin_detail_jobseeker
    Created on : Aug 21, 2025, 7:09:13 PM
    Author     : DELL
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thông tin chi tiết người tìm việc</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/admin_dashboard.css">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/admin_detail_jobseeker.css">
    </head>
    <body>
        <div class="dashboard-container">
            <jsp:include page="sidebar.jsp" />

            <div class="main-content">
                <div class="page-header">
                    <h1>Thông tin chi tiết người tìm việc</h1>
                    <div class="header-actions">
                        <a href="AdminController?target=JobSeeker" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i>
                            Quay lại danh sách
                        </a>
    <!--                    <a href="AdminController?target=JobSeeker&service=Update&ID=${JobSeeker.id}" 
                           class="btn btn-primary">
                            <i class="fas fa-edit"></i>
                            Edit
                        </a>-->
                    </div>
                </div>

                <div class="section-card">
                    <div class="profile-header">
                        <img src="${JobSeeker.profilePicture != null ? JobSeeker.profilePicture : 'https://via.placeholder.com/150'}" 
                             alt="Profile Picture" class="profile-picture">
                        <div class="profile-info">
                            <h2>${JobSeeker.fullName}</h2>
                            <p><i class="fas fa-envelope"></i> : ${JobSeeker.email}</p>
                            <p><i class="fas fa-phone"></i> : ${JobSeeker.phone}</p>
                            <p><i class="fas fa-birthday-cake"></i> : ${JobSeeker.dateOfBirth}</p>
                            <p>
                                <c:choose>
                                    <c:when test="${JobSeeker.active}">
                                        <span class="status-badge status-active">Đang hoạt động</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-badge status-inactive">Ngừng hoạt động</span>
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                </div>

                <div class="section-card">
                    <h3 class="section-title">Thông tin cơ bản</h3>
                    <div class="detail-row">
                        <div class="detail-label">Username:</div>
                        <div class="detail-value">${JobSeeker.username}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Giới tính:</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${fn:toLowerCase(JobSeeker.gender) == 'male'}">Nam</c:when>
                                <c:when test="${fn:toLowerCase(JobSeeker.gender) == 'female'}">Nữ</c:when>
                                <c:when test="${fn:toLowerCase(JobSeeker.gender) == 'other'}">Khác</c:when>
                            </c:choose>
                        </div>
                    </div>

                    <div class="detail-row">
                        <div class="detail-label">Địa chỉ:</div>
                        <div class="detail-value">${JobSeeker.address}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Ngôn ngữ biết sử dụng:</div>
                        <div class="detail-value">${JobSeeker.languages}</div>
                    </div>
                </div>

                <div class="section-card">
                    <h3 class="section-title">Thông tin chuyên môn</h3>
                    <div class="detail-row">
                        <div class="detail-label">Số năm kinh nghiệm:</div>
                        <div class="detail-value">${JobSeeker.experienceYears} years</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Trình độ học vấn:</div>
                        <div class="detail-value">${JobSeeker.education}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Vị trí công việc mong muốn:</div>
                        <div class="detail-value">${JobSeeker.desiredJobTitle}</div>
                    </div>
                    <div class="detail-row"> 
                        <div class="detail-label">Mức lương mong muốn:</div>
                        <div class="detail-value">
                            <fmt:formatNumber value="${JobSeeker.desiredSalary}" type="number" pattern="#,###"/> VNĐ
                        </div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Ngành nghề mong muốn:</div>
                        <div class="detail-value">${JobSeeker.jobCategory}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Địa điểm làm việc mong muốn:</div>
                        <div class="detail-value">${JobSeeker.preferredLocation}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Cấp bậc nghề nghiệp:</div>
                        <div class="detail-value">${JobSeeker.careerLevel}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Hình thức làm việc:</div>
                        <div class="detail-value">${JobSeeker.workType}</div>
                    </div>
                </div>

                <div class="section-card">
                    <h3 class="section-title">Kỹ năng</h3>
                    <div class="skills-list">
                        <c:forEach var="skill" items="${JobSeeker.skills.split(',')}">
                            <span class="skill-tag">${skill.trim()}</span>
                        </c:forEach>
                    </div>
                </div>

                <div class="section-card">
                    <h3 class="section-title">Tóm tắt hồ sơ</h3>
                    <p>${JobSeeker.profileSummary}</p>
                </div>

                <div class="section-card">
                    <h3 class="section-title">Thông tin bổ sung</h3>
                    <div class="detail-row">
                        <div class="detail-label">Link portfolio:</div>
                        <div class="detail-value">
                            <a href="${JobSeeker.portfolioUrl}" target="_blank">${JobSeeker.portfolioUrl}</a>
                        </div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">File CV:</div>
                        <div class="detail-value">
                            <c:if test="${JobSeeker.cvFile != null}">
                                <a href="${JobSeeker.cvFile}" class="cv-download" target="_blank">
                                    <i class="fas fa-download"></i>
                                    Tải CV
                                </a>
                            </c:if>
                        </div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Ngày tạo hồ sơ:</div>
                        <div class="detail-value">${JobSeeker.createdAt}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Ngày cập nhật hồ sơ:</div>
                        <div class="detail-value">${JobSeeker.updatedAt}</div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>