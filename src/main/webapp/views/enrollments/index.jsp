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

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="alert alert-danger mt-3"><%= error %></div>
    <%
        }
    %>

    <div class="table-responsive mt-3">
        <table class="table table-striped table-hover table-bordered">
            <thead class="table-dark">
            <tr class="text-center">
                <th>ID</th>
                <th>Người Dùng</th>
                <th>Khóa Học</th>
                <th>Ngày Đăng Ký</th>
                <th>Hành Động</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Enrollment> enrollments = (List<Enrollment>) request.getAttribute("enrollments");
                if (enrollments != null && !enrollments.isEmpty()) {
                    for (Enrollment enrollment : enrollments) {
            %>
            <tr>
                <td class="text-center"><%= enrollment.getId() %></td>
                <td><%= enrollment.getUser().getUsername() %></td>
                <td><%= enrollment.getCourse().getName() %></td>
                <td class="text-center"><%= enrollment.getEnrolledAt() %></td>
                <td class="text-center">
                    <a href="enrollments?action=edit&id=<%= enrollment.getId() %>" class="btn btn-primary btn-sm">✏ Sửa</a>
                    <form action="enrollments" method="post" class="d-inline">
                        <input type="hidden" name="id" value="<%= enrollment.getId() %>">
                        <input type="hidden" name="action" value="delete">
                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?')">🗑 Xóa</button>
                    </form>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="5" class="text-center text-muted">🚫 Không có đăng ký nào.</td>
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
