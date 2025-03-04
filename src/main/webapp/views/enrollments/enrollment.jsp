<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.fai.study.onlinecourse.entities.Enrollment, java.util.List" %>
<!DOCTYPE html>
<html lang="vi">
<head>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <jsp:include page="../../includes/header.jsp"/>
</head>
<body>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center">
        <h2>Danh Sách Đăng Ký</h2>
        <a href="<%= request.getContextPath() %>/enrollments?action=add" class="btn btn-success">Thêm Đăng Ký Mới</a>
    </div>

    <%-- Hiển thị thông báo lỗi nếu có --%>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="alert alert-danger mt-3"><%= error %></div>
    <%
        }
    %>

    <div class="table-responsive mt-3">
        <table class="table table-striped table-hover">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Người Dùng</th>
                <th>Khóa Học</th>
                <th>Ngày Đăng Ký</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Enrollment> enrollments = (List<Enrollment>) request.getAttribute("enrollments");
                if (enrollments != null && !enrollments.isEmpty()) {
                    for (Enrollment enrollment : enrollments) {
            %>
            <tr>
                <td><%= enrollment.getId() %></td>
                <td><%= enrollment.getUser().getUsername() %></td>
                <td><%= enrollment.getCourse().getName() %></td>
                <td><%= enrollment.getEnrolledAt() %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="4" class="text-center text-muted">Không có đăng ký nào.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../../includes/footer.jsp"/>
</body>
</html>
