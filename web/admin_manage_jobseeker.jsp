<%-- 
    Document   : admin_manage_jobseeker
    Created on : Aug 20, 2025, 11:49:45 PM
    Author     : DELL
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý người tìm việc</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/admin_dashboard.css">
        <style>
            .action-buttonss {
                display: flex;
                gap: 8px;
                justify-content: center;
            }
            .action-btn {
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 30px;
                height: 30px;
                border-radius: 50%;
                color: #fff;
                font-size: 14px;
                transition: background-color 0.3s ease, transform 0.2s ease;
                text-decoration: none;
                cursor: pointer;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .action-btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            }
            .view-btn {
                background-color: #28a745;
            }
            .view-btn:hover {
                background-color: #218838;
            }
            .edit-btn {
                background-color: #007bff;
            }
            .edit-btn:hover {
                background-color: #0069d9;
            }
            .ban-btn {
                background-color: #dc3545;
            }
            .ban-btn:hover {
                background-color: #c82333;
            }
        </style>
    </head>
    <body>
        <div class="dashboard-container">
            <jsp:include page="sidebar.jsp" />

            <div class="main-content">
                <!-- Page Header -->
                <div class="page-header">
                    <h1>Quản lý người tìm việc</h1>
                    <div class="header-actions">
                        <a href="AdminController?target=JobSeeker&service=Add" class="btn btn-primary">
                            <i class="fas fa-user-plus"></i>
                            Thêm người tìm việc mới 
                        </a>
                    </div>
                </div>

                <div class="search-filter-section">
                    <form action="AdminController" method="get">
                        <input type="hidden" name="target" value="JobSeeker">
                        <input type="hidden" name="service" value="list">

                        <div class="search-filter-row">
                            <div class="search-box">
                                <input type="text" name="fullName" placeholder="Tên..." 
                                       value="${param.fullName}">
<!--                                <i class="fas fa-search"></i>-->
                            </div>

                            <input type="text" name="email" class="form-control" 
                                   placeholder="Email" value="${param.email}" style="width: 200px;">

                            <input type="text" name="phone" class="form-control" 
                                   placeholder="Điện thoại" value="${param.phone}" style="width: 150px;">

<!--                            <select name="gender" class="form-control" style="width: 120px;">
                                <option value="">Giới tính</option>
                                <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Nam</option>
                                <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Nữ</option>
                                <option value="Other" ${param.gender == 'Other' ? 'selected' : ''}>Khác</option>
                            </select>

                            <select name="isActive" class="form-control" style="width: 120px;">
                                <option value="">Trạng thái</option>
                                <option value="true" ${param.isActive == 'true' ? 'selected' : ''}>Hoạt động</option>
                                <option value="false" ${param.isActive == 'false' ? 'selected' : ''}>Không hoạt động</option>
                            </select>-->

                            <button type="submit" name="submit" value="Search" class="btn btn-primary">
                                <i class="fas fa-search"></i>
                                Tìm kiếm
                            </button>

                            <a href="AdminController?target=JobSeeker" class="btn btn-secondary">
                                <i class="fas fa-redo"></i>
                                Đặt lại
                            </a>
                        </div>
                    </form>
                </div>
                <!-- Data Table -->
                <div class="table-container">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Họ và Tên</th>
                                <th>Email</th>
                                <th>Điện thoại</th>
                                <th>Giới tính</th>
                                <th>Kinh nghiệm</th>
                                <th>Trình độ</th>
                                <th>Trạng thái</th>
                                <th>Hoạt động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="jobSeeker" items="${vec}">
                                <tr>
                                    <td>${jobSeeker.id}</td>
                                    <td>${jobSeeker.fullName}</td>
                                    <td>${jobSeeker.email}</td>
                                    <td>${jobSeeker.phone}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${fn:toLowerCase(jobSeeker.gender) == 'male'}">Nam</c:when>
                                            <c:when test="${fn:toLowerCase(jobSeeker.gender) == 'female'}">Nữ</c:when>
                                            <c:when test="${fn:toLowerCase(jobSeeker.gender) == 'other'}">Khác</c:when>
                                        </c:choose>
                                    </td>
                                    <td>${jobSeeker.experienceYears} năm</td>
                                    <td>${jobSeeker.education}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${jobSeeker.active}">
                                                <span class="status-badge status-active">Hoạt động</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-badge status-inactive">Không hoạt động</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="action-buttonss">
                                            <a href="AdminController?target=JobSeeker&service=Detail&ID=${jobSeeker.id}"
                                               class="action-btn view-btn" title="View Details">
                                                <i class="fas fa-eye"></i>
                                            </a>
<!--                                            <a href="AdminController?target=JobSeeker&service=Update&ID=${jobSeeker.id}"
                                           class="action-btn edit-btn" title="Edit Job Seeker">
                                            <i class="fas fa-edit"></i>
                                        </a>-->
                                            <a href="AdminController?target=JobSeeker&service=Ban&ID=${jobSeeker.id}&status=${jobSeeker.active}"
                                               class="action-btn ban-btn"
                                               onclick="return confirm('Bạn có chắc muốn thay đổi trạng thái không?')" title="Change Status">
                                                <i class="fas fa-ban"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty vec}">
                                <tr>
                                    <td colspan="9" class="text-center">Không tìm thấy người tìm việc.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>