<%-- 
    Document   : notification
    Created on : Aug 26, 2025, 6:14:22 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thông báo</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <%@ include file="header.jsp" %>
        <div class="container-fluid">
            <div class="row">
                <div class="p-3 border-bottom bg-white">
                    <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
                        <form action="notification" method="post" class="d-flex align-items-center gap-2">
                            <label for="type" class="form-label m-0 text-success fw-bold">Thông báo:</label>
                            <select name="type" id="filter" class="form-select form-select-sm w-auto border-success text-success" onchange="this.form.submit()">
                                <option value="all" <c:if test="${param.type == 'all'}">selected</c:if>>Tất cả</option>
                                <option value="unread" <c:if test="${param.type == 'unread'}">selected</c:if>>Chưa đọc</option>
                                </select>
                            </form>

                            <div class="d-flex gap-2">
                                <form action="notification" method="post">
                                    <button type="submit" name="service" value="deleteAll" class="btn btn-outline-success btn-sm">
                                        Xóa tất cả
                                    </button>
                                </form>
                                <form action="notification" method="post">
                                    <button type="submit" name="service" value="deleteReaded" class="btn btn-outline-success btn-sm">
                                        Xóa tất cả thông báo đã đọc
                                    </button>
                                </form>
                            </div>

                        </div>
                    </div>

                    <div class="col-md-4 border-end bg-white" style="height: 100vh; overflow-y: auto;">
                        <ul class="list-group list-group-flush">
                        <c:forEach var="noti" items="${notice}">
                            <form action="notification" method="post">
                                <button type="submit" name="service" value="detail" style="all: unset; width: 100%;">
                                    <li class="list-group-item noti-item ${noti.is_read ? 'read' : 'unread'}">
                                        <strong>${noti.title}</strong><br>
                                        <div class="notification-content">${noti.content}</div>
                                        <small>${noti.created_at}</small>
                                        <div class="d-flex justify-content-end gap-2">
                                            <a href="notification?service=markAsUnread&id=${noti.id}" class="btn btn-sm btn-outline-success">
                                                Đánh dấu là chưa đọc
                                            </a>
                                            <a href="notification?service=deleteSpecific&id=${noti.id}" class="btn btn-sm btn-outline-success">
                                                Xóa
                                            </a>
                                        </div>
                                    </li>
                                </button>
                                <input type="hidden" name="id" value="${noti.id}">
                                <input type="hidden" name="type" value="${param.type}">
                            </form>
                            <hr class="my-2" />
                        </c:forEach>
                    </ul>
                </div>

                <div class="col-md-8 p-4 bg-white">
                    <c:if test="${not empty specific}">
                        <h4 class="text-success">${specific.title}</h4>
                        <p>${specific.content}</p>
                        <small class="text-muted">Created at: ${specific.created_at}</small>
                        <a href="${specific.redirect_url}" class="btn btn-secondary">
                            Liên kết
                        </a>
                    </c:if>

                    <c:if test="${empty specific}">
                        <div class="text-center text-muted">
                            <h5>Chọn thông báo để xem chi tiết</h5>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
    <%@ include file="footer.jsp" %>
</html>
