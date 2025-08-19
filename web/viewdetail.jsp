<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết bài đăng</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 80%; margin: auto; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }
        th { background: #f5f5f5; }
    </style>
</head>
<body>
    <c:if test="${not empty post}">
        <h2 style="text-align:center;">Chi tiết bài đăng #${post.id}</h2>
        <table>
            <tr><th>ID</th><td>${post.id}</td></tr>
            <tr><th>Tiêu đề</th><td>${post.title}</td></tr>
            <tr><th>Nội dung</th><td>${post.content}</td></tr>
            <tr><th>Lương</th>
                <td><fmt:formatNumber value="${post.salary}" type="currency" currencySymbol="₫"/></td>
            </tr>
            <tr><th>Địa điểm</th><td>${post.location}</td></tr>
            <tr><th>Kinh nghiệm</th><td>${post.experienceYears} năm</td></tr>
            <tr><th>Ngày hết hạn</th>
                <td><fmt:formatDate value="${post.deadline}" pattern="dd/MM/yyyy"/></td>
            </tr>
            <tr><th>Ngày tạo</th>
                <td><fmt:formatDate value="${post.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
            </tr>
        </table>
    </c:if>

    <c:if test="${empty post}">
        <p style="text-align:center;">Bài đăng không tồn tại hoặc đã bị xóa.</p>
    </c:if>

    <div style="text-align:center; margin-top:20px;">
        <a href="post?view=my-post"><button>Quay lại danh sách</button></a>
    </div>
</body>
</html>

<jsp:include page="footer.jsp" />