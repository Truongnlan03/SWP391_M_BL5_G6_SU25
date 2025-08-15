<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div class="container mt-5 pt-4">
    <h2 class="mb-4">Danh sách bài đăng</h2>

    <c:if test="${empty postList}">
        <div class="alert alert-warning">Không có bài đăng nào.</div>
    </c:if>

    <div class="row">
        <c:forEach var="post" items="${postList}">
            <div class="col-md-4 mb-4">
                <div class="card h-100 shadow-sm border">
                    <div class="card-body d-flex flex-column">
                        <!-- Logo công ty -->
                        <div class="mb-3 text-center">
                            <img src="${post.companyLogo}" alt="Logo Công ty" class="img-fluid" style="max-height: 50px;">
                        </div>

                        <!-- Tiêu đề -->
                        <h5 class="card-title mb-1">
                            <a href="postdetail?id=${post.id}" class="text-decoration-none text-dark fw-bold">
                                ${post.title}
                            </a>
                        </h5>

                        <!-- Tên công ty -->
                        <p class="text-muted mb-2">${post.companyName}</p>

                        <!-- Lương & Địa điểm -->
                        <div class="mb-2">
                            <span class="badge bg-light text-dark border">
                                ${post.salaryMin} - ${post.salaryMax} triệu
                            </span>
                            <span class="badge bg-light text-dark border">
                                ${post.location}
                            </span>
                        </div>

                        <!-- Tags khác (job type, nổi bật...) -->
                        <div class="mb-3">
                            <span class="badge bg-secondary">${post.jobType}</span>
                            <c:if test="${post.isFeatured}">
                                <span class="badge bg-warning text-dark">Nổi bật</span>
                            </c:if>
                        </div>

                        <!-- Nút xem chi tiết -->
                        <div class="mt-auto">
                            <a href="postdetail?id=${post.id}" class="btn btn-outline-primary w-100">
                                Xem chi tiết
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="footer.jsp" />
