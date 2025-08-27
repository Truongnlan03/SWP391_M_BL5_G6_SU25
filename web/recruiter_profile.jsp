<%-- 
    Document   : recruiter_profile
    Created on : Aug 19, 2025, 9:11:00 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Hồ sơ</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="manifest" href="site.webmanifest">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/recruiter_profile.css">
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <main>
            <div class="container mt-5 mb-5">
                <div class="row">
                    <div class="col-lg-4">
                        <div class="card">
                            <div class="card-body text-center">
                                <img src="${recruiter.profilePicture}" alt="avatar"
                                     class="rounded-circle img-fluid shadow-sm" style="width: 150px;">
                                <h5 class="my-3">${recruiter.fullName}</h5>
                                <p class="text-muted mb-1">${recruiter.companyName}</p>
                                <p class="text-muted mb-4">${recruiter.address}</p>
                                <div class="d-flex justify-content-center mb-2">
                                    <a href="edit-recruiter-profile" class="btn btn-primary">Chỉnh sửa</a>
                                </div>
                            </div>
                        </div>

                        <div class="card mt-4">
                            <div class="card-body">
                                <h5 class="card-title">Thông tin công ty</h5>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item"><strong>Tên công ty:</strong> ${recruiter.companyName}</li>
                                    <li class="list-group-item"><strong>Ngành nghề:</strong> ${recruiter.industry}</li>
                                    <li class="list-group-item"><strong>Quy mô:</strong> ${recruiter.companySize}</li>
                                    <li class="list-group-item"><strong>Website:</strong> <a href="${recruiter.website}" target="_blank">${recruiter.website}</a></li>
                                    <li class="list-group-item"><strong>Địa chỉ:</strong> ${recruiter.companyAddress}</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-8">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Thông tin</h5>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">Họ và Tên</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">${recruiter.fullName}</p>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">Email</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">${recruiter.email}</p>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">Điện thoại</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">${recruiter.phone}</p>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">Ngày sinh</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">${recruiter.dateOfBirth}</p>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">Địa chỉ</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">${recruiter.address}</p>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <p class="mb-0"><strong>Mô tả công ty</strong></p>
                                        <p class="text-muted mb-0">${recruiter.companyDescription}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="footer.jsp" /> 
    </body>
</html>
