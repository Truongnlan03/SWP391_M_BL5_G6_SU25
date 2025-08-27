<%-- 
    Document   : edit-post
    Created on : Aug 25, 2025, 1:08:42 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chỉnh sửa tin tuyển dụng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/edit-post.css">
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container mt-4">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/post">Danh sách
                            tin</a></li>
                    <li class="breadcrumb-item"><a
                            href="${pageContext.request.contextPath}/post/view?id=${post.id}">${post.title}</a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">Chỉnh sửa tin</li>
                </ol>
            </nav>
            <div class="form-container">
                <div class="card">
                    <div class="card-body">
                        <h2 class="card-title mb-4">Chỉnh sửa tin tuyển dụng</h2>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/post/update" method="POST"
                              id="editPostForm" enctype="multipart/form-data" onsubmit="tinymce.triggerSave();">
                            <input type="hidden" name="id" value="${post.id}">
                            <input type="hidden" name="parentId" value="${post.parentId}">
                            <input type="hidden" name="postType" value="${post.postType}">

                            <div class="mb-4">
                                <h4>Thông tin cơ bản</h4>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="title" class="form-label">Tiêu đề tin tuyển dụng</label>
                                        <input type="text" class="form-control" id="title" name="title" required
                                               maxlength="200" value="${post.title}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="companyName" class="form-label">Tên công ty</label>
                                        <input type="text" class="form-control" id="companyName"
                                               name="companyName" required value="${post.companyName}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="companyLogo" class="form-label">Logo công ty</label>
                                        <input type="file" class="form-control" id="companyLogo"
                                               name="companyLogo" accept="image/*" onchange="previewLogo(event)">
                                        <div id="logoPreview" class="mt-2"
                                             style="display: ${not empty post.companyLogo ? 'block' : 'none'};">
                                            <img id="previewImage" src="/TopJobVN/${post.companyLogo}" alt="Logo Preview"
                                                 style="max-width: 200px; max-height: 200px;">
                                        </div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="salary" class="form-label">Mức lương</label>
                                        <input type="text" class="form-control" id="salary" name="salary"
                                               required value="${post.salary}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="location" class="form-label">Địa điểm</label>
                                        <input type="text" class="form-control" id="location" name="location"
                                               required value="${post.location}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="jobType" class="form-label">Loại hình công việc</label>
                                        <select class="form-select" id="jobType" name="jobType" required>
                                            <option value="Full-time" ${post.jobType=='Full-time' ? 'selected'
                                                                        : '' }>Full-time</option>
                                            <option value="Part-time" ${post.jobType=='Part-time' ? 'selected'
                                                                        : '' }>Part-time</option>
                                            <option value="Contract" ${post.jobType=='Contract' ? 'selected'
                                                                       : '' }>Contract</option>
                                            <option value="Internship" ${post.jobType=='Internship' ? 'selected'
                                                                         : '' }>Internship</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="experience" class="form-label">Kinh nghiệm</label>
                                        <input type="text" class="form-control" id="experience"
                                               name="experience" required value="${post.experience}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="deadline" class="form-label">Hạn nộp hồ sơ</label>
                                        <input type="date" class="form-control" id="deadline" name="deadline"
                                               required
                                               value="<fmt:formatDate value='${post.deadline}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="workingTime" class="form-label">Thời gian làm việc</label>
                                        <input type="text" class="form-control" id="workingTime"
                                               name="workingTime" required value="${post.workingTime}">
                                    </div>
                                </div>
                            </div>

                            <div class="mb-4">
                                <h4>Chi tiết công việc</h4>
                                <div class="mb-3">
                                    <label for="jobDescription" class="form-label">Mô tả công việc</label>
                                    <textarea class="form-control" id="default" name="jobDescription"
                                              required>${post.jobDescription}</textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="requirements" class="form-label">Yêu cầu ứng viên</label>
                                    <textarea class="form-control" id="requirements" name="requirements"
                                              required>${post.requirements}</textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="benefits" class="form-label">Quyền lợi</label>
                                    <textarea class="form-control" id="benefits" name="benefits"
                                              required>${post.benefits}</textarea>
                                </div>
                            </div>

                            <div class="mb-4">
                                <h4>Thông tin liên hệ</h4>
                                <div class="mb-3">
                                    <label for="contactAddress" class="form-label">Địa chỉ làm việc</label>
                                    <input type="text" class="form-control" id="contactAddress"
                                           name="contactAddress" required value="${post.contactAddress}">
                                </div>
                                <div class="mb-3">
                                    <label for="applicationMethod" class="form-label">Cách thức ứng
                                        tuyển</label>
                                    <textarea class="form-control" id="applicationMethod"
                                              name="applicationMethod" required>${post.applicationMethod}</textarea>
                                </div>
                            </div>

                            <div class="mb-4">
                                <h4>Thông tin bổ sung</h4>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="rank" class="form-label">Cấp bậc</label>
                                        <input type="text" class="form-control" id="rank" name="rank"
                                               maxlength="100" value="${post.rank}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="industry" class="form-label">Ngành nghề</label>
                                        <input type="text" class="form-control" id="industry" name="industry"
                                               maxlength="100" value="${post.industry}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="contactPerson" class="form-label">Người liên hệ</label>
                                        <input type="text" class="form-control" id="contactPerson"
                                               name="contactPerson" maxlength="100" value="${post.contactPerson}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="companySize" class="form-label">Quy mô công ty</label>
                                        <input type="text" class="form-control" id="companySize"
                                               name="companySize" maxlength="100" value="${post.companySize}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="companyWebsite" class="form-label">Website công ty</label>
                                        <input type="text" class="form-control" id="companyWebsite"
                                               name="companyWebsite" maxlength="255"
                                               value="${post.companyWebsite}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="keywords" class="form-label">Từ khóa</label>
                                        <input type="text" class="form-control" id="keywords" name="keywords"
                                               maxlength="255"
                                               placeholder="Nhập các từ khóa, cách nhau bởi dấu phẩy"
                                               value="${post.keywords}">
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="companyDescription" class="form-label">Mô tả công ty</label>
                                    <textarea class="form-control" id="companyDescription"
                                              name="companyDescription" rows="3"
                                              maxlength="1000">${post.companyDescription}</textarea>
                                </div>
                            </div>

                            <div class="mb-4">
                                <h4>Trạng thái</h4>
                                <div class="mb-3">
                                    <label for="status" class="form-label">Trạng thái tin</label>
                                    <select class="form-select" id="status" name="status" required>
                                        <option value="active" ${post.status=='active' ? 'selected' : '' }>Đang
                                            tuyển</option>
                                        <option value="draft" ${post.status=='draft' ? 'selected' : '' }>Bản
                                            nháp</option>
                                        <option value="closed" ${post.status=='closed' ? 'selected' : '' }>Đã
                                            đóng</option>
                                    </select>
                                </div>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/post/view?id=${post.id}"
                                   class="btn btn-secondary">
                                    <i class="fas fa-arrow-left"></i> Quay lại
                                </a>

                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Cập nhật tin
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/tinymce/tinymce.min.js"></script>
        <script src="${pageContext.request.contextPath}/Assets/js/tinymceConfig.js"></script>
        <script src="${pageContext.request.contextPath}/Assets/js/edit-post.js"></script>
    </body>
</html>