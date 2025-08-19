<%-- 
    Document   : edit_recruiter_profile
    Created on : Aug 19, 2025, 10:15:39 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html class="no-js" lang="zxx">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Chỉnh sửa hồ sơ</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/edit_recruiter_profile.css">
    </head>

    <body>
        <jsp:include page="header.jsp" />
        <main>
            <div class="container my-5">
                <h2 class="mb-4">Chỉnh sửa hồ sơ</h2>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>
                <form action="edit-recruiter-profile" method="post">
                    <div class="row">
                        <!-- Personal Info -->
                        <div class="col-md-6">
                            <h4 class="mb-3">Thông tin cá nhân</h4>
                            <div class="form-group mb-3">
                                <label for="fullName">Họ và tên</label>
                                <input type="text" class="form-control" id="fullName" name="fullName"
                                       value="${recruiter.fullName}">
                            </div>
                            <div class="form-group mb-3">
                                <label for="phone">Điện thoại</label>
                                <input type="text" class="form-control" id="phone" name="phone"
                                       value="${recruiter.phone}">
                            </div>
                            <div class="form-group mb-3">
                                <label for="dateOfBirth">Ngày sinh</label>
                                <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth"
                                       value="${recruiter.dateOfBirth}">
                            </div>
                            <div class="form-group mb-3">
                                <label for="gender">Giới tính</label>
                                <select class="form-control" id="gender" name="gender">
                                    <option value="male" ${recruiter.gender == 'male' ? 'selected' : ''}>Nam</option>
                                    <option value="female" ${recruiter.gender == 'female' ? 'selected' : ''}>Nữ</option>
                                    <option value="other" ${recruiter.gender == 'other' ? 'selected' : ''}>Khác</option>
                                </select>
                            </div>
                            <div class="form-group mb-3">
                                <label for="address">Địa chỉ</label>
                                <input type="text" class="form-control" id="address" name="address"
                                       value="${recruiter.address}">
                            </div>
                            <div class="form-group mb-3">
                                <label for="profilePicture">Ảnh đại diện (URL)</label>
                                <input type="text" class="form-control" id="profilePicture" name="profilePicture"
                                       value="${recruiter.profilePicture}">
                                <img id="profilePreview" class="img-preview" alt="Preview ảnh đại diện">
                            </div>
                        </div>

                        <!-- Company Info -->
                        <div class="col-md-6">
                            <h4 class="mb-3">Thông tin công ty</h4>
                            <div class="form-group mb-3">
                                <label for="companyName">Tên công ty</label>
                                <input type="text" class="form-control" id="companyName" name="companyName"
                                       value="${recruiter.companyName}">
                            </div>
                            <div class="form-group mb-3">
                                <label for="companyDescription">Mô tả công ty</label>
                                <textarea class="form-control" id="companyDescription" name="companyDescription"
                                          rows="3">${recruiter.companyDescription}</textarea>
                            </div>
                            <div class="form-group mb-3">
                                <label for="logo">Logo URL</label>
                                <input type="text" class="form-control" id="logo" name="logo" value="${recruiter.logo}">
                                <img id="logoPreview" class="img-preview" alt="Preview logo công ty">
                            </div>
                            <div class="form-group mb-3">
                                <label for="website">Website</label>
                                <input type="text" class="form-control" id="website" name="website"
                                       value="${recruiter.website}">
                            </div>
                            <div class="form-group mb-3">
                                <label for="companyAddress">Địa chỉ công ty</label>
                                <input type="text" class="form-control" id="companyAddress" name="companyAddress"
                                       value="${recruiter.companyAddress}">
                            </div>
                            <div class="form-group mb-3">
                                <label for="companySize">Quy mô</label>
                                <input type="text" class="form-control" id="companySize" name="companySize"
                                       value="${recruiter.companySize}">
                            </div>
                            <div class="form-group mb-3">
                                <label for="industry">Ngành</label>
                                <input type="text" class="form-control" id="industry" name="industry"
                                       value="${recruiter.industry}">
                            </div>
                        </div>
                    </div>
                    <div class="mt-4">
                        <button type="submit" class="btn btn-primary me-2">Lưu thay đổi</button>
                        <a href="recruiter-profile" class="btn btn-secondary">Hủy</a>
                    </div>
                </form>
            </div>
        </main>
        <jsp:include page="footer.jsp" />
        <script>
            function setupImagePreview(inputId, previewId) {
                const input = document.getElementById(inputId);
                const preview = document.getElementById(previewId);

                input.addEventListener("input", function () {
                    const url = this.value.trim();
                    if (url) {
                        preview.src = url;
                        preview.style.display = "block";
                        preview.onerror = () => {
                            preview.style.display = "none";
                        };
                    } else {
                        preview.style.display = "none";
                    }
                });

                // load sẵn nếu có giá trị từ DB
                if (input.value.trim()) {
                    preview.src = input.value.trim();
                    preview.style.display = "block";
                }
            }

            setupImagePreview("profilePicture", "profilePreview");
            setupImagePreview("logo", "logoPreview");
        </script>
    </body>
</html>
