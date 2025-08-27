<%-- 
    Document   : profile_jobseeker
    Created on : Aug 15, 2025, 5:55:58 PM
    Author     : DELL
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hồ sơ cá nhân | TopJobVN</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link rel="stylesheet" href="Assets/css/profile_jobseeker.css">
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div class="container py-5">
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="fas fa-check-circle me-2"></i>
                    ${sessionScope.successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="successMessage" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="fas fa-exclamation-triangle me-2"></i>
                    ${sessionScope.errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="errorMessage" scope="session" />
            </c:if>
            <div class="row">
                <div class="col-lg-4">
                    <div class="profile-section">
                        <div class="section-body text-center">
                            <form id="avatarForm" method="post" action="avatar-upload" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="uploadAvatar">
                                <input type="file" name="avatarFile" id="avatarFile" class="d-none" accept="image/png, image/jpeg" onchange="document.getElementById('avatarForm').submit();">
                                <img src="${sessionScope.user.profilePicture != null ? sessionScope.user.profilePicture : 'assets/img/elements/user.png'}" alt="Avatar" class="rounded-circle img-fluid" style="width: 150px; cursor:pointer;" onclick="document.getElementById('avatarFile').click();">
                                <small class="text-muted d-block mt-2">Nhấp vào ảnh để thay đổi</small>
                            </form>
                            <h5 class="my-3">${sessionScope.user.fullName}</h5>
                            <p class="text-muted mb-1">${sessionScope.user.desiredJobTitle != null ? sessionScope.user.desiredJobTitle : 'Tìm việc làm'}</p>
<!--                            <p class="text-muted mb-4">${sessionScope.user.address != null ? sessionScope.user.address : 'Việt Nam'}</p>-->
                        </div>
                    </div>

                    <div class="profile-section">
                        <div class="section-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0"><i class="fas fa-address-card me-2"></i>Thông tin liên hệ</h5>
                            <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#editContactModal">
                                <i class="fas fa-edit me-1"></i>Chỉnh sửa
                            </button>
                        </div>
                        <div class="section-body">
                            <div class="mb-3">
                                <i class="fas fa-envelope me-2 " style="color: rgb(8, 122, 6);"></i>
                                <span>${sessionScope.user.email}</span>
                            </div>
                            <div class="mb-3">
                                <i class="fas fa-phone me-2 " style="color: rgb(8, 122, 6);"></i>
                                <span>${sessionScope.user.phone != null ? sessionScope.user.phone : 'Chưa cập nhật'}</span>
                            </div>
                            <div class="mb-3">
                                <i class="fas fa-map-marker-alt me-2 " style="color: rgb(8, 122, 6);"></i>
                                <span>${sessionScope.user.address != null ? sessionScope.user.address : 'Chưa cập nhật'}</span>
                            </div>
                            <c:if test="${not empty sessionScope.user.portfolioUrl}">
                                <div class="mb-3">
                                    <i class="fab fa-linkedin me-2 " style="color: rgb(8, 122, 6);"></i>
                                    <a href="${sessionScope.user.portfolioUrl}" target="_blank" class="text-decoration-none">
                                        Portfolio/LinkedIn
                                    </a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
<div class="modal fade" id="editContactModal" tabindex="-1" aria-labelledby="editContactModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="profilejobseeker">
                <input type="hidden" name="action" value="editContact" />
                <div class="modal-header" style="background: linear-gradient(135deg, #28a745, #20c997); color: white;">
                    <h5 class="modal-title" id="editContactModalLabel">Chỉnh sửa thông tin</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Họ và tên</label>
                        <input type="text" class="form-control" id="fullName" name="fullName" value="${sessionScope.user.fullName}" required />
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" value="${sessionScope.user.email}" required />
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Điện thoại</label>
                        <input type="text" class="form-control" id="phone" name="phone" value="${sessionScope.user.phone}" />
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">Địa chỉ</label>
                        <input type="text" class="form-control" id="address" name="address" value="${sessionScope.user.address}" />
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary" style="background-color: #28a745; border-color: #28a745;">
                        Lưu thay đổi
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>