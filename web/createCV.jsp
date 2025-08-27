<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Tạo CV - TopJobVN</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/createCV.css">
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div class="container">
            <h2 class="text-center mt-4">Tạo CV mới</h2>
            <div class="cv-builder">
                <form action="create-cv" method="post" enctype="multipart/form-data" class="cv-form" id="cvForm">
                    <div class="mb-2">
                        <label class="form-label">Họ và tên</label>
                        <input type="text" class="form-control" name="full_name" id="fullName" required>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Vị trí ứng tuyển</label>
                        <input type="text" class="form-control" name="job_position" id="jobPosition" required>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Ngày sinh</label>
                        <input type="date" class="form-control" name="birth_date" id="birthDate" required>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Giới tính</label>
                        <select class="form-control" name="gender" id="gender" required>
                            <option value="">-- Chọn giới tính --</option>
                            <option value="Nam">Nam</option>
                            <option value="Nữ">Nữ</option>
                            <option value="Khác">Khác</option>
                        </select>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Số điện thoại</label>
                        <input type="tel" class="form-control" name="phone" id="phone" pattern="[0-9]{9,15}" required>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Email</label>
                        <input type="email" class="form-control" name="email" id="email" required>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Website</label>
                        <input type="url" class="form-control" name="website" id="website" placeholder="https://example.com">
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Địa chỉ</label>
                        <input type="text" class="form-control" name="address" id="address">
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Mục tiêu nghề nghiệp</label>
                        <textarea class="form-control" name="career_goal" id="careerGoal" rows="3"></textarea>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Học vấn</label>
                        <textarea class="form-control" name="education" id="education" rows="3"></textarea>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Kinh nghiệm</label>
                        <textarea class="form-control" name="work_experience" id="workExperience" rows="3"></textarea>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Chứng chỉ</label>
                        <textarea class="form-control" name="certificates" id="certificates" rows="2"></textarea>
                    </div>

                    <div class="mb-2">
                        <label class="form-label">Ảnh đại diện</label>
                        <input type="file" class="form-control" name="image_path" id="imagePath" accept="image/*">
                    </div>

                    <button class="btn btn-primary mt-2" type="submit">Lưu CV</button>
                </form>


                <div class="cv-preview">
                    <div class="cv-header">
                        <img id="previewImage" src="https://via.placeholder.com/120" alt="Avatar">
                        <div>
                            <h2 id="previewName">Họ và tên</h2>
                            <p id="previewPosition">Vị trí ứng tuyển</p>
                            <div class="info-line">Ngày sinh: <span id="previewBirthDate">DD/MM/YYYY</span></div>
                            <div class="info-line">Giới tính: <span id="previewGender">Nam/Nữ</span></div>
                            <div class="info-line">Số điện thoại: <span id="previewPhone">0123456789</span></div>
                            <div class="info-line">Email: <span id="previewEmail">email@example.com</span></div>
                            <div class="info-line">Website: <span id="previewWebsite">example.com</span></div>
                            <div class="info-line">Địa chỉ: <span id="previewAddress">...</span></div>
                        </div>
                    </div>

                    <div class="section-title">MỤC TIÊU NGHỀ NGHIỆP</div>
                    <div class="section-content" id="previewCareerGoal">Mục tiêu nghề nghiệp...</div>

                    <div class="section-title">HỌC VẤN</div>
                    <div class="section-content" id="previewEducation">Thông tin học vấn...</div>

                    <div class="section-title">KINH NGHIỆM LÀM VIỆC</div>
                    <div class="section-content" id="previewExperience">Thông tin kinh nghiệm...</div>
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
                    }
                    reader.readAsDataURL(file);
                }
            });
        </script>

        <%@ include file="footer.jsp" %>
    </body>
</html>
