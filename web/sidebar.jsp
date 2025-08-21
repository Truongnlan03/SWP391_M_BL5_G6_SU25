<%-- 
    Document   : sidebar.jsp
    Created on : Aug 20, 2025, 10:09:29 PM
    Author     : DELL
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Sidebar Component -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/TopJobVN/Assets/css/sidebar.css">
<div class="sidebar">
    <div class="d-flex align-items-center gap-2 sidebar-header">
        <div class="user-info">
            Welcome ${sessionScope.user.fullName}
        </div>
    </div>

    <nav class="sidebar-menu">
        <div class="action-buttons">
            <a href="admin_dashboard.jsp" class="menu-item ${pageContext.request.servletPath == '/admin_dashboard.jsp' ? 'active' : ''}">
                <i class="fas fa-tachometer-alt"></i>
                <span>Dashboard</span>
            </a>
            <!--            <a href="AdminController?target=JobSeeker&service=Add" class="menu-item">-->
            <a href="AdminController?target=JobSeeker" class="menu-item">
                <i class="fas fa-user-tie"></i>
                <span>Quản lý người tìm việc</span>
            </a>
            <a href="AdminController?target=Recruiter&service=Add" class="menu-item">
                <i class="fas fa-user-tie"></i>
                <span>Quản lý nhà tuyển dụng</span>
            </a>
            <a href="AdminSalerController?target=blog&service=Add" class="menu-item">
                <i class="fas fa-pen"></i>
                <span>Tạo Blog Post</span>
            </a>
            <a href="AdminSalerController?target=banner&service=Add" class="menu-item">
                <i class="fas fa-image"></i>
                <span>Tạo Banner</span>
            </a>
            <a href="AdminController?target=Manager&service=Add" class="menu-item">
                <i class="fas fa-user-tie"></i>
                <span>Quản lý Manager</span>
            </a>
            <a href="reports.jsp" class="menu-item">
                <i class="fas fa-chart-line"></i>
                <span>Xem báo cáo</span>
            </a>
            <a class="menu-item" href="home">
                <i class="fas fa-home"></i> 
                <span>Home</span>
            </a>
        </div>
        </a>

        <c:if test="${sessionScope.adminRole == 'manager' || sessionScope.adminRole == 'admin'}">
            <a href="AdminController?target=Recruiter" class="menu-item ${param.target == 'Recruiter' ? 'active' : ''}">
                <i class="fas fa-building"></i>
                <span>Quản lý nhà tuyển dụng</span>
            </a>

            <a href="AdminController?target=Staff" class="menu-item ${param.target == 'Staff' ? 'active' : ''}">
                <i class="fas fa-user-tie"></i>
                <span>Quản lý nhân viên</span>
            </a>

            <a href="AdminController?target=Saler" class="menu-item ${param.target == 'Saler' ? 'active' : ''}">
                <i class="fas fa-user-tag"></i>
                <span>Quản lý Saler</span>
            </a>
        </c:if>

        <c:if test="${sessionScope.adminRole == 'saler' || sessionScope.adminRole == 'admin'}">
            <a href="AdminSalerController?target=blog" class="menu-item ${param.target == 'blog' ? 'active' : ''}">
                <i class="fas fa-blog"></i>
                <span>Quản lý Blogs</span>
            </a>

            <a href="AdminSalerController?target=banner" class="menu-item ${param.target == 'banner' ? 'active' : ''}">
                <i class="fas fa-image"></i>
                <span>Quản lý Banners</span>
            </a>

            <a href="AdminSalerController?target=homecomponent" class="menu-item ${param.target == 'homecomponent' ? 'active' : ''}">
                <i class="fas fa-cog"></i>
                <span>Thành phần Homepage</span>
            </a>
        </c:if>

        <c:if test="${sessionScope.adminRole == 'manager' || sessionScope.adminRole == 'admin'}">
            <a href="AdminPromotionController?target=program" class="menu-item ${param.target == 'program' ? 'active' : ''}">
                <i class="fas fa-bullseye"></i>
                <span>Chương trình</span>
            </a>

            <a href="ReportsController?target=report&action=dashboard" class="menu-item ${param.target == 'report' ? 'active' : ''}">
                <i class="fas fa-chart-bar"></i>
                <span>Báo cáo</span>
            </a>
        </c:if>
    </nav>
</div>
