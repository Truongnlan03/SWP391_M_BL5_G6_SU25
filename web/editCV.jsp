
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Chỉnh sửa CV - TopJobVN</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/editCV.css">
    </head>
    <body>
        <%@ include file="header.jsp" %>

        <div class="container">
            <h2 class="text-center mt-4">Chỉnh sửa CV</h2>
            <div class="cv-builder">
                <form action="edit-cv" method="post" enctype="multipart/form-data" class="cv-form" id="cvForm">
                    <!-- Hidden để gửi ID và ảnh cũ -->
                    <input type="hidden" name="id" value="${cv.id}">
                    <input type="hidden" name="old_image_path" value="${cv.pdfFilePath}">

                    <div class="mb-2">
                        <label class="form-label">Họ và tên</label>
                        <input type="text" class="form-control" name="full_name" id="fullName" 
                               value="${cv.fullName}" required>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Vị trí ứng tuyển</label>
                        <input type="text" class="form-control" name="job_position" id="jobPosition" 
                               value="${cv.jobPosition}" required>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Ngày sinh</label>
                        <input type="date" class="form-control" name="birth_date" id="birthDate" 
                               value="${cv.birthDate}" required>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Giới tính</label>
                        <select class="form-control" name="gender" id="gender" required>
                            <option value="">-- Chọn giới tính --</option>
                            <option value="Nam" ${cv.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                            <option value="Nữ" ${cv.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                            <option value="Khác" ${cv.gender == 'Khác' ? 'selected' : ''}>Khác</option>
                        </select>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Số điện thoại</label>
                        <input type="tel" class="form-control" name="phone" id="phone" 
                               value="${cv.phone}" pattern="[0-9]{9,15}" required>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Email</label>
                        <input type="email" class="form-control" name="email" id="email" 
                               value="${cv.email}" required>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Website</label>
                        <input type="url" class="form-control" name="website" id="website" 
                               value="${cv.website}" placeholder="https://example.com">
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Địa chỉ</label>
                        <input type="text" class="form-control" name="address" id="address" 
                               value="${cv.address}">
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Mục tiêu nghề nghiệp</label>
                        <textarea class="form-control" name="career_goal" id="careerGoal" rows="3">${cv.careerGoal}</textarea>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Học vấn</label>
                        <textarea class="form-control" name="education" id="education" rows="3">${cv.education}</textarea>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Kinh nghiệm</label>
                        <textarea class="form-control" name="work_experience" id="workExperience" rows="3">${cv.workExperience}</textarea>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Chứng chỉ</label>
                        <textarea class="form-control" name="certificates" id="certificates" rows="2">${cv.certificates}</textarea>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Ảnh đại diện</label>
                        <input type="file" class="form-control" name="image_path" id="imagePath" accept="image/*">
                        <div class="mt-2">
                            <img src="${empty cv.pdfFilePath ? 'https://via.placeholder.com/120' : cv.pdfFilePath}" 
                                 id="previewImage" style="width:120px; height:120px; object-fit:cover; border:1px solid #ccc;">
                        </div>
                    </div>

                    <button class="btn btn-primary mt-2" type="submit">Cập nhật CV</button>
                </form>

                <!-- CV Preview -->
                <div class="cv-preview">
                    <div class="cv-header">
                        <img id="previewImageRight" src="${empty cv.pdfFilePath ? 'https://via.placeholder.com/120' : cv.pdfFilePath}" alt="Avatar">
                        <div>
                            <h2 id="previewName">${cv.fullName}</h2>
                            <p id="previewPosition">${cv.jobPosition}</p>
                            <div class="info-line">Ngày sinh: <span id="previewBirthDate">${cv.birthDate}</span></div>
                            <div class="info-line">Giới tính: <span id="previewGender">${cv.gender}</span></div>
                            <div class="info-line">Số điện thoại: <span id="previewPhone">${cv.phone}</span></div>
                            <div class="info-line">Email: <span id="previewEmail">${cv.email}</span></div>
                            <div class="info-line">Website: <span id="previewWebsite">${cv.website}</span></div>
                            <div class="info-line">Địa chỉ: <span id="previewAddress">${cv.address}</span></div>
                        </div>
                    </div>

                    <div class="section-title">MỤC TIÊU NGHỀ NGHIỆP</div>
                    <div class="section-content" id="previewCareerGoal">${cv.careerGoal}</div>

                    <div class="section-title">HỌC VẤN</div>
                    <div class="section-content" id="previewEducation">${cv.education}</div>

                    <div class="section-title">KINH NGHIỆM LÀM VIỆC</div>
                    <div class="section-content" id="previewExperience">${cv.workExperience}</div>
                </div>
            </div>
        </div>

        <script>
            const fieldMap = {
                fullName: 'previewName',
                jobPosition: 'previewPosition',
                birthDate: 'previewBirthDate',
                gender: 'previewGender',
                phone: 'previewPhone',
                email: 'previewEmail',
                website: 'previewWebsite',
                address: 'previewAddress',
                careerGoal: 'previewCareerGoal',
                education: 'previewEducation',
                workExperience: 'previewExperience'
            };

            Object.keys(fieldMap).forEach(id => {
                const el = document.getElementById(id);
                if (el) {
                    el.addEventListener('input', () => {
                        document.getElementById(fieldMap[id]).textContent = el.value || '...';
                    });
                }
            });

            document.getElementById('imagePath').addEventListener('change', function (e) {
                const file = e.target.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (evt) {
                        document.getElementById('previewImage').src = evt.target.result;
                        document.getElementById('previewImageRight').src = evt.target.result;
                    }
                    reader.readAsDataURL(file);
                }
            });
        </script>

        <%@ include file="footer.jsp" %>
    </body>
</html>
