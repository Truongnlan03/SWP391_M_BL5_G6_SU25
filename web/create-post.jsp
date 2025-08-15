<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />

<div class="container mt-5 pt-4">
    <form action="${pageContext.request.contextPath}/create-post" method="post">

        <!-- Thông tin công việc -->
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <i class="fas fa-briefcase"></i> Thông tin công việc
            </div>
            <div class="card-body">
                <div class="mb-3">
                    <label class="form-label">Tiêu đề công việc <span class="text-danger">*</span></label>
                    <input type="text" name="title" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mức lương</label>
                    <input type="text" name="salary" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Địa điểm <span class="text-danger">*</span></label>
                    <input type="text" name="location" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Loại công việc <span class="text-danger">*</span></label>
                    <select name="jobType" class="form-select" required>
                        <option value="">-- Chọn loại --</option>
                        <option>Toàn thời gian</option>
                        <option>Bán thời gian</option>
                        <option>Thực tập</option>
                        <option>Hợp đồng</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Kinh nghiệm yêu cầu</label>
                    <input type="text" name="experience" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Ngày hết hạn</label>
                    <input type="date" name="deadline" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Số lượng tuyển</label>
                    <input type="number" name="quantity" class="form-control" min="1">
                </div>
            </div>
        </div>

        <!-- Mô tả & Yêu cầu -->
        <div class="card mb-4">
            <div class="card-header bg-info text-white">
                <i class="fas fa-file-alt"></i> Mô tả & Yêu cầu
            </div>
            <div class="card-body">
                <div class="mb-3">
                    <label class="form-label">Mô tả công việc <span class="text-danger">*</span></label>
                    <textarea name="jobDescription" class="form-control" rows="4" required></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Yêu cầu</label>
                    <textarea name="requirements" class="form-control" rows="3"></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Quyền lợi</label>
                    <textarea name="benefits" class="form-control" rows="3"></textarea>
                </div>
            </div>
        </div>

        <!-- Liên hệ -->
        <div class="card mb-4">
            <div class="card-header bg-secondary text-white">
                <i class="fas fa-phone"></i> Thông tin liên hệ
            </div>
            <div class="card-body">
                <div class="mb-3">
                    <label class="form-label">Địa chỉ liên hệ</label>
                    <input type="text" name="contactAddress" class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label">Hình thức nộp hồ sơ</label>
                    <input type="text" name="applicationMethod" class="form-control">
                </div>
            </div>
        </div>

        <!-- Submit -->
        <div class="text-end mb-5">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary me-2">Hủy</a>
            <button type="submit" class="btn btn-primary">
                <i class="fas fa-save"></i> Đăng bài
            </button>
        </div>
    </form>
</div>

<jsp:include page="footer.jsp" />
