<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div class="container mt-5 pt-4">
    <h2 class="mb-4">Danh sách bài đăng</h2>

    <c:if test="${empty postList}">
        <div class="alert alert-warning">Không có bài đăng nào.</div>
    </c:if>

    <c:forEach var="post" items="${postList}">
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <i class="fas fa-briefcase"></i> ${post.title}
            </div>
            <div class="card-body">
                <!-- Thông tin công việc -->
                <p><strong>Mức lương:</strong> ${post.salaryMin} - ${post.salaryMax}</p>
                <p><strong>Địa điểm:</strong> ${post.location}</p>
                <p><strong>Loại công việc:</strong> ${post.jobType}</p>
                <p><strong>Kinh nghiệm:</strong> ${post.experience}</p>
                <p><strong>Ngày hết hạn:</strong> ${post.deadline}</p>
                <p><strong>Số lượng tuyển:</strong> ${post.quantity}</p>

                <!-- Mô tả & Yêu cầu -->
                <p><strong>Mô tả công việc:</strong><br>${post.description}</p>
                <p><strong>Yêu cầu:</strong><br>${post.requirements}</p>
                <p><strong>Quyền lợi:</strong><br>${post.benefits}</p>

                <!-- Liên hệ -->
                <p><strong>Địa chỉ liên hệ:</strong> ${post.contactAddress}</p>
                <p><strong>Hình thức nộp hồ sơ:</strong> ${post.applicationMethod}</p>
            </div>
        </div>
    </c:forEach>
</div>

<jsp:include page="footer.jsp" />