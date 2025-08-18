<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách bài đăng</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h2 { text-align: center; margin-bottom: 20px; }
        table { border-collapse: collapse; width: 90%; margin: auto; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: center; }
        th { background: #f8f8f8; font-weight: bold; }
        tr:nth-child(even) { background: #fafafa; }
        tr:hover { background: #f1f1f1; }
        .btn {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn:hover { background-color: #45a049; }
        .no-data {
            text-align: center;
            margin-top: 20px;
            font-style: italic;
            color: #666;
        }
    </style>
</head>
<body>
    <h2>Danh sách bài đăng</h2>
    <c:if test="${empty posts}">
        <p class="no-data">Không có bài đăng nào để hiển thị.</p>
    </c:if>
    <c:if test="${not empty posts}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tiêu đề</th>
                    <th>Địa điểm</th>
                    <th>Xem chi tiết</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${posts}">
                    <tr>
                        <td>${p.id}</td>
                        <td style="text-align:left;">${p.title}</td>
                        <td>${p.location}</td>
                        <td>
                            <a href="viewdetail?id=${p.id}">
                                <button class="btn">Xem chi tiết</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
<jsp:include page="footer.jsp" />