<%-- 
    Document   : header
    Created on : Aug 13, 2025, 11:16:03?PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header-area">
    <nav class="navbar navbar-expand-lg navbar-light bg-white fixed-top shadow-sm">
        <div class="container">
            <!-- Logo -->
            <a class="navbar-brand" href="home">
                <img src="Assets/image/logo/logo.png" alt="TopJobVN Logo" height="40">
            </a>

            <!-- Mobile Toggle -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarMain">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Main Navigation -->
            <div class="collapse navbar-collapse" id="navbarMain">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="/TopJobVN/home">Trang chủ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/TopJobVN/post?viewAll=true">Việc làm</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/TopJobVN/advanced-search">
                            Tìm kiếm nâng cao
                        </a>
                    </li>

                    <!-- Applications Menu for logged in users -->
                    <c:if
                        test="${not empty sessionScope.user and (sessionScope.role == 'job-seeker' or sessionScope.role == 'recruiter')}">
                        <li class="nav-item">
                            <a class="nav-link" href="/TopJobVN/applications">
                                Ứng tuyển
                            </a>
                        </li>
                    </c:if>
                    <!-- CV Management for job seekers -->
                    <c:if test="${not empty sessionScope.user and sessionScope.role == 'job-seeker'}">
                        <li class="nav-item">
                            <a class="nav-link" href="/TopJobVN/list_cv">
                                Quản lí CV
                            </a>
                        </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link" href="/TopJobVN/BlogController">
                            Blog
                        </a>
                    </li>
                    <c:if test="${sessionScope.role == 'recruiter'}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="communityDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Đăng tin
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="communityDropdown">
                                <li>
                                    <a class="dropdown-item" href="/TopJobVN/create-post.jsp">
                                        Tạo bài đăng
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="/TopJobVN/post?view=my-post">
                                        Bài đăng đã tạo
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link" href="/TopJobVN/about.jsp">
                            Về chúng tôi
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/TopJobVN/contact.jsp">
                            Liên hệ
                        </a>
                    </li>
                    <c:if test="${sessionScope.role == 'admin'}">
                        <li class="nav-item">
                            <a class="nav-link" href="/TopJobVN/admin_dashboard.jsp">
                                Dashboard
                            </a>
                        </li>
                    </c:if>
                </ul>

                <!-- User Menu -->
                <div class="d-flex align-items-center">
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <c:if test="${sessionScope.role != 'admin'}">
                                <div class="dropdown" data-bs-auto-close="outside">
                                    <button type="button" class="btn btn-light position-relative dropdown-toggle" id="notiDropdown"
                                            data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="fas fa-bell"></i>
                                        <c:if test="${sessionScope.rr gt 0}">
                                            <span class="badge rounded-pill bg-danger" 
                                                  style="position: absolute; bottom: 40px; right: 22px; font-size: 10px; min-width: 16px; height: 16px; display: flex; align-items: center; justify-content: center;">
                                                ${sessionScope.rr}
                                            </span>
                                        </c:if>
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-end notifications"
                                        aria-labelledby="notiDropdown">

                                        <!-- Notification filter tabs -->
                                        <div class="d-flex justify-content-center px-3 pt-2">
                                            <button type="button"
                                                    class="btn btn-sm btn-outline-primary me-1 tab-btn active"
                                                    data-target="all">
                                                Tất cả
                                            </button>
                                            <button type="button"
                                                    class="btn btn-sm btn-outline-primary me-1 tab-btn"
                                                    data-target="unread">
                                                Chưa đọc
                                            </button>
                                        </div>
                                        <hr class="my-2" />

                                        <!-- All notifications -->
                                        <div class="tab-content all">
                                            <c:forEach var="noti" items="${sessionScope.notice}">
                                                <a href="/TopJopVN/notification?service=detail&type=all&id=${noti.id}"
                                                   class="text-decoration-none text-dark">
                                                    <li class="notification-item ${noti.is_read ? '' : 'unread'}">
                                                        <div class="notification-title">${noti.title}</div>
                                                        <div class="notification-content">${noti.content}</div>
                                                        <span class="notification-time">${noti.created_at}</span>
                                                    </li>
                                                </a>
                                            </c:forEach>
                                        </div>

                                        <!-- Unread notifications only -->
                                        <div class="tab-content unread d-none">
                                            <c:forEach var="noti" items="${sessionScope.unread}">
                                                <c:if test="${!noti.is_read}">
                                                    <a href="/TopJopVN/notification?service=detail&type=all&id=${noti.id}"
                                                       class="text-decoration-none text-dark">
                                                        <li class="notification-item unread">
                                                            <div class="notification-title">${noti.title}</div>
                                                            <div class="notification-content">${noti.content}</div>
                                                            <span
                                                                class="notification-time">${noti.created_at}</span>
                                                        </li>
                                                    </a>
                                                </c:if>
                                            </c:forEach>
                                        </div>

                                        <li class="view-all text-center mt-2">
                                            <a href="notification" class="text-primary text-decoration-none">Xem tất cả</a>
                                        </li>
                                    </ul>
                                </div>
                            </c:if>
                            <div>&nbsp;</div>
                            <div class="dropdown">
                                <button class="btn btn-light dropdown-toggle" type="button" id="userDropdown"
                                        data-bs-toggle="dropdown" aria-expanded="false">
                                    ${sessionScope.user.fullName}
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">

                                    <c:if test="${sessionScope.role == 'job-seeker'}">
                                        <li><a class="dropdown-item" href="/TopJobVN/profilejobseeker"> Hồ sơ cá nhân</a></li>
                                        <li><a class="dropdown-item" href="/TopJobVN/list_cv">
                                                CV của tôi</a></li>
                                        <li><a class="dropdown-item" href="/TopJobVN/saved-jobs">
                                                Công việc đã lưu</a></li>
                                        <li><a class="dropdown-item" href="/TopJobVN/advanced-search"> Tìm kiếm việc làm nâng cao</a></li>
                                        </c:if>

                                    <c:if test="${sessionScope.role == 'recruiter'}">
                                        <li><a class="dropdown-item" href="/TopJobVN/recruiter-profile"> Hồ sơ cá nhân</a></li>
                                        <li><a class="dropdown-item" href="/TopJobVN/post?view=my-post"> Bài đăng của tôi</a></li>
                                        <li><a class="dropdown-item" href="/TopJobVN/saved-jobs">
                                                Công việc đã lưu</a></li>
                                        <li><a class="dropdown-item" href="/TopJobVN/recruitment_dashboard.jsp"> Dashboard</a></li>
                                        </c:if>

                                    <c:if test="${sessionScope.role == 'admin'}">
                                        <li><a class="dropdown-item" href="admin"> Hồ sơ cá nhân</a></li>
                                        <li><a class="dropdown-item" href="/TopJobVN/admin_dashboard.jsp"> Dashboard</a></li>
                                        </c:if>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li>
                                        <a class="dropdown-item text-danger" href="/TopJobVN/logout">
                                            Đăng xuất
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <a href="/TopJobVN/register" class="btn btn-outline-primary me-2">
                                Đăng ký
                            </a>
                            <a href="/TopJobVN/login" class="btn btn-primary">
                                Đăng nhập
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </nav>
</header>

<!-- Bootstrap and Font Awesome -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="/TopJobVN/Assets/css/header.css">
<script src="/TopJobVN/Assets/js/header.js"></script>

