<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Danh sách CV của tôi</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background: #f4f6f8;
            }
            .cv-card {
                border: 1px solid #ddd;
                border-radius: 6px;
                background: #fff;
                padding: 15px;
                height: 100%;
                font-family: Arial, sans-serif;
                transition: box-shadow 0.3s ease;
            }
            .cv-card:hover {
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }
            .cv-header {
                display: flex;
                gap: 15px;
                border-bottom: 1px solid #ccc;
                padding-bottom: 10px;
            }
            .cv-header img {
                width: 100px;
                height: 100px;
                object-fit: cover;
                background: #f0f0f0;
            }
            .cv-header h4 {
                margin: 0;
                font-size: 18px;
                font-weight: bold;
            }
            .cv-header .position {
                font-size: 14px;
                color: #555;
            }
            .info-line {
                font-size: 13px;
                margin: 1px 0;
            }
            .section-title {
                font-weight: bold;
                font-size: 14px;
                margin-top: 10px;
                border-bottom: 1px solid #000;
                padding-bottom: 2px;
            }
            .section-content {
                font-size: 13px;
                color: #555;
                margin-top: 3px;
                white-space: pre-line;
            }
            .cv-actions {
                margin-top: 10px;
                display: flex;
                justify-content: flex-end;
                gap: 5px;
            }
            .cv-card {
                border: 1px solid #ddd;
                border-radius: 6px;
                background: #fff;
                padding: 15px;
                height: 450px; /* Chiều cao cố định cho khung CV */
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                font-family: Arial, sans-serif;
                transition: box-shadow 0.3s ease;
            }

            .cv-card:hover {
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }

            .section-content {
                font-size: 13px;
                color: #555;
                margin-top: 3px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }

        </style>
    </head>
    <body>
        <%@ include file="../header.jsp" %>

        <div class="container mt-4">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h2>CV đã tạo</h2>
                <div>
                    <a href="create-cv" class="btn btn-success me-2">+ Tạo CV</a>
                    <!-- Nút Upload -->
                    <a href="#" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#uploadCVModal">📤 Upload CV</a>

                    <!-- Modal Upload CV -->
                    <div class="modal fade" id="uploadCVModal" tabindex="-1" aria-labelledby="uploadCVLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="upload-cv" method="post" enctype="multipart/form-data">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="uploadCVLabel">Tải lên CV</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="cvFile" class="form-label">Chọn CV (PDF hoặc DOC/DOCX)</label>
                                            <input type="file" class="form-control" id="cvFile" name="cvFile" 
                                                   accept=".pdf,.doc,.docx" required>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                        <button type="submit" class="btn btn-primary">Tải lên</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- Bootstrap JS (nếu chưa có) -->
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                </div>
            </div>


            <!-- Form Search + Sort -->
            <form method="get" action="list-cv" class="row g-2 mb-4">
                <div class="col-md-4">
                    <input type="text" name="keyword" value="${keyword}" placeholder="Tìm theo vị trí ứng tuyển" class="form-control">
                </div>
                <div class="col-md-3">
                    <select name="sort" class="form-select">
                        <option value="desc" ${sort == 'desc' ? 'selected' : ''}>Mới nhất</option>
                        <option value="asc" ${sort == 'asc' ? 'selected' : ''}>Cũ nhất</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">Lọc</button>
                </div>
            </form>

            <c:choose>
                <c:when test="${not empty cvList}">
                    <div class="row g-4">
                        <c:forEach var="cv" items="${cvList}">
                            <div class="col-md-6 col-lg-4">
                                <div class="cv-card">
                                    <!-- Header -->
                                    <div class="cv-header">
                                        <img src="${empty cv.pdfFilePath ? 'https://via.placeholder.com/100' : cv.pdfFilePath}" alt="Avatar">
                                        <div>
                                            <h4>${empty cv.fullName ? 'Chưa cập nhật' : cv.fullName}</h4>
                                            <div class="position">${empty cv.jobPosition ? 'Chưa cập nhật' : cv.jobPosition}</div>
                                            <div class="info-line">Ngày sinh: ${empty cv.birthDate ? 'Chưa cập nhật' : cv.birthDate}</div>
                                            <div class="info-line">Giới tính: ${empty cv.gender ? 'Chưa cập nhật' : cv.gender}</div>
                                            <div class="info-line">SĐT: ${empty cv.phone ? 'Chưa cập nhật' : cv.phone}</div>
                                            <div class="info-line">Email: ${empty cv.email ? 'Chưa cập nhật' : cv.email}</div>
                                            <div class="info-line">Website: ${empty cv.website ? 'Chưa cập nhật' : cv.website}</div>
                                            <div class="info-line">Địa chỉ: ${empty cv.address ? 'Chưa cập nhật' : cv.address}</div>
                                        </div>
                                    </div>

                                    <!-- Sections -->
                                    <div class="section-title">MỤC TIÊU NGHỀ NGHIỆP</div>
                                    <div class="section-content">${empty cv.careerGoal ? 'Chưa cập nhật' : cv.careerGoal}</div>

                                    <div class="section-title">HỌC VẤN</div>
                                    <div class="section-content">${empty cv.education ? 'Chưa cập nhật' : cv.education}</div>

                                    <div class="section-title">KINH NGHIỆM LÀM VIỆC</div>
                                    <div class="section-content">${empty cv.workExperience ? 'Chưa cập nhật' : cv.workExperience}</div>

                                    <!-- Actions -->
                                    <div class="cv-actions">
                                        <a href="view-cv?id=${cv.id}" class="btn btn-info btn-sm">Xem</a>
                                        <a href="edit-cv?id=${cv.id}" class="btn btn-warning btn-sm">Sửa</a>
                                        <a href="delete-cv?id=${cv.id}" class="btn btn-danger btn-sm"
                                           onclick="return confirm('Bạn có chắc muốn xóa CV này?')">Xóa</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Pagination -->
                    <nav class="mt-4">
                        <ul class="pagination justify-content-center">
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link"
                                       href="list-cv?page=${i}&keyword=${keyword}&sort=${sort}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info text-center">
                        Bạn chưa tạo CV nào.
                        <a href="create-cv" class="btn btn-primary btn-sm">Tạo CV mới</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <%@ include file="../footer.jsp" %>
    </body>
</html>
