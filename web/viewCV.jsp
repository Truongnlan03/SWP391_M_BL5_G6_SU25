<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Xem CV - ${cv.fullName}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background: #f4f6f8;
            }
            .cv-preview {
                max-width: 800px;
                margin: 30px auto;
                background: #fff;
                border: 1px solid #ddd;
                padding: 20px;
                font-family: Arial, sans-serif;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
            }
            .cv-header {
                display: flex;
                gap: 20px;
                border-bottom: 2px solid #ccc;
                padding-bottom: 15px;
            }
            .cv-header img {
                width: 120px;
                height: 120px;
                object-fit: cover;
                border-radius: 5px;
                background: #eee;
            }
            .cv-header h2 {
                margin: 0;
                font-size: 22px;
                font-weight: bold;
            }
            .info-line {
                font-size: 14px;
                margin: 2px 0;
            }
            .section-title {
                font-weight: bold;
                font-size: 15px;
                margin-top: 20px;
                border-bottom: 1px solid #000;
                padding-bottom: 3px;
            }
            .section-content {
                font-size: 14px;
                color: #555;
                margin-top: 5px;
                white-space: pre-line;
            }
        </style>
    </head>
    <body>
        <%@ include file="header.jsp" %>

        <div class="container">
            <h2 class="text-center mt-4">Chi tiết CV</h2>

            <div class="cv-preview">
                <!-- Header -->
                <div class="cv-header">
                    <img src="${empty cv.pdfFilePath ? 'https://via.placeholder.com/120' : cv.pdfFilePath}" alt="Avatar">
                    <div>
                        <h2>${empty cv.fullName ? 'Chưa cập nhật' : cv.fullName}</h2>
                        <p>${empty cv.jobPosition ? 'Chưa cập nhật' : cv.jobPosition}</p>
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

                <div class="section-title">CHỨNG CHỈ</div>
                <div class="section-content">${empty cv.certificates ? 'Chưa cập nhật' : cv.certificates}</div>
            </div>

            <!-- Nút hành động -->
            <div class="mt-3 text-center">
                <a href="list-cv" class="btn btn-secondary">Quay lại</a>
                <a href="edit-cv?id=${cv.id}" class="btn btn-warning">Sửa</a>
                <a href="delete-cv?id=${cv.id}" 
                   class="btn btn-danger"
                   onclick="return confirm('Bạn có chắc chắn muốn xóa CV này?');">
                    Xóa
                </a>
                <a href="export-cv?id=${cv.id}" class="btn btn-success">Xuất PDF</a>
            </div>
        </div>

        <%@ include file="footer.jsp" %>
    </body>
</html>
