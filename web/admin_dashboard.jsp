<%-- 
    Document   : admin_dashboard
    Created on : Aug 20, 2025, 10:05:24 PM
    Author     : DELL
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="/TopJobVN/Assets/css/admin_dashboard.css">
    </head>
    <body>
        <div class="dashboard-container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <div class="page-header">
                    <h1>Dashboard Overview</h1>
                </div>
                <div class="dashboard-grid">
                    <div class="stat-card">
                        <div class="stat-card-header">
                            <div>
                                <div class="stat-value">1,245</div>
                                <div class="stat-label">Total Job Seekers</div>
                            </div>
                            <i class="fas fa-users"></i>
                        </div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-card-header">
                            <div>
                                <div class="stat-value">387</div>
                                <div class="stat-label">Active Recruiters</div>
                            </div>
                            <i class="fas fa-building"></i>
                        </div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-card-header">
                            <div>
                                <div class="stat-value">892</div>
                                <div class="stat-label">Job Postings</div>
                            </div>
                            <i class="fas fa-briefcase"></i>
                        </div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-card-header">
                            <div>
                                <div class="stat-value">$12,450</div>
                                <div class="stat-label">Monthly Revenue</div>
                            </div>
                            <i class="fas fa-dollar-sign"></i>
                        </div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-card-header">
                            <div>
                                <div class="stat-value">23</div>
                                <div class="stat-label">Active Managers</div>
                            </div>
                            <i class="fas fa-user-tie"></i>
                        </div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-card-header">
                            <div>
                                <div class="stat-value">156</div>
                                <div class="stat-label">Published Blogs</div>
                            </div>
                            <i class="fas fa-blog"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
