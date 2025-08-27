<%-- 
    Document   : recruitment_dashboard
    Created on : Aug 26, 2025, 11:59:45 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Recruitment Dashboard - Quản lý tuyển dụng</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/dashboardrecruitment.css"/>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="container">
            <div class="page-header">
                <h2><i class="fas fa-users-cog"></i> Recruitment Dashboard</h2>
                <p>Quản lý quy trình tuyển dụng và theo dõi tiến độ ứng viên</p>
            </div>
            <div class="table-container">
                <div class="stats-grid">
                    <div class="stat-card">
                        <div class="stat-number">${inProgress.size()}</div>
                        <div class="stat-label">Đang xử lý</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-number">${completed.size()}</div>
                        <div class="stat-label">Hoàn thành</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-number">${rejected.size()}</div>
                        <div class="stat-label">Từ chối</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-number">${hired.size()}</div>
                        <div class="stat-label">Đã tuyển</div>
                    </div>
                </div>

                <div class="quick-actions">
                    <h3><i class="fas fa-bolt"></i> Thao tác nhanh</h3>
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/recruitment/screening"
                           class="btn btn-primary btn-small">
                            <i class="fas fa-search"></i> Sàng lọc hồ sơ
                        </a>
                        <a href="${pageContext.request.contextPath}/recruitment/phone-interview"
                           class="btn btn-success btn-small">
                            <i class="fas fa-phone"></i> Phỏng vấn điện thoại
                        </a>
                        <a href="${pageContext.request.contextPath}/recruitment/skills-test"
                           class="btn btn-secondary btn-small">
                            <i class="fas fa-clipboard-check"></i> Test kỹ năng
                        </a>
                        <a href="${pageContext.request.contextPath}/recruitment/final-interview"
                           class="btn btn-primary btn-small">
                            <i class="fas fa-user-tie"></i> Phỏng vấn cuối
                        </a>
                        <a href="${pageContext.request.contextPath}/recruitment/decision"
                           class="btn btn-success btn-small">
                            <i class="fas fa-gavel"></i> Quyết định
                        </a>
                        <a href="${pageContext.request.contextPath}/recruitment/offer"
                           class="btn btn-secondary btn-small">
                            <i class="fas fa-file-contract"></i> Gửi offer
                        </a>
                    </div>
                </div>

                <div class="dashboard-grid">
                    <!-- In Progress -->
                    <div class="stage-card">
                        <h3><i class="fas fa-clock"></i> Đang xử lý (${inProgress.size()})</h3>
                        <c:choose>
                            <c:when test="${empty inProgress}">
                                <p style="color: #999; text-align: center;">Không có quy trình nào đang xử lý
                                </p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="process" items="${inProgress}" varStatus="status">
                                    <c:if test="${status.index < 5}">
                                        <div class="process-item">
                                            <h4>Application #${process.applicationId}</h4>
                                            <p><strong>Stage:</strong> ${process.currentStage}</p>
                                            <p><strong>Updated:</strong> ${process.updatedAt}</p>
                                            <span class="status-badge status-in-progress">In Progress</span>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${inProgress.size() > 5}">
                                    <p style="text-align: center; color: #00b894; font-weight: bold;">
                                        +${inProgress.size() - 5} thêm...
                                    </p>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="stage-card">
                        <h3><i class="fas fa-check-circle"></i> Hoàn thành (${completed.size()})</h3>
                        <c:choose>
                            <c:when test="${empty completed}">
                                <p style="color: #999; text-align: center;">Chưa có quy trình nào hoàn thành</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="process" items="${completed}" varStatus="status">
                                    <c:if test="${status.index < 5}">
                                        <div class="process-item">
                                            <h4>Application #${process.applicationId}</h4>
                                            <p><strong>Stage:</strong> ${process.currentStage}</p>
                                            <p><strong>Completed:</strong> ${process.updatedAt}</p>
                                            <span class="status-badge status-completed">Completed</span>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${completed.size() > 5}">
                                    <p style="text-align: center; color: #00b894; font-weight: bold;">
                                        +${completed.size() - 5} thêm...
                                    </p>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="stage-card">
                        <h3><i class="fas fa-times-circle"></i> Từ chối (${rejected.size()})</h3>
                        <c:choose>
                            <c:when test="${empty rejected}">
                                <p style="color: #999; text-align: center;">Chưa có ứng viên nào bị từ chối</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="process" items="${rejected}" varStatus="status">
                                    <c:if test="${status.index < 5}">
                                        <div class="process-item">
                                            <h4>Application #${process.applicationId}</h4>
                                            <p><strong>Stage:</strong> ${process.currentStage}</p>
                                            <p><strong>Rejected:</strong> ${process.updatedAt}</p>
                                            <span class="status-badge status-rejected">Rejected</span>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${rejected.size() > 5}">
                                    <p style="text-align: center; color: #00b894; font-weight: bold;">
                                        +${rejected.size() - 5} thêm...
                                    </p>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="stage-card">
                        <h3><i class="fas fa-user-check"></i> Đã tuyển (${hired.size()})</h3>
                        <c:choose>
                            <c:when test="${empty hired}">
                                <p style="color: #999; text-align: center;">Chưa có ứng viên nào được tuyển</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="process" items="${hired}" varStatus="status">
                                    <c:if test="${status.index < 5}">
                                        <div class="process-item">
                                            <h4>Application #${process.applicationId}</h4>
                                            <p><strong>Stage:</strong> ${process.currentStage}</p>
                                            <p><strong>Hired:</strong> ${process.updatedAt}</p>
                                            <span class="status-badge status-hired">Hired</span>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${hired.size() > 5}">
                                    <p style="text-align: center; color: #00b894; font-weight: bold;">
                                        +${hired.size() - 5} thêm...
                                    </p>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="action-buttons">
                    <a href="home.jsp" class="btn btn-secondary">
                        <i class="fas fa-home"></i> Trang chủ
                    </a>
                    <a href="applications.jsp" class="btn btn-primary">
                        <i class="fas fa-list"></i> Xem tất cả đơn ứng tuyển
                    </a>
                </div>
            </div>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
</html>