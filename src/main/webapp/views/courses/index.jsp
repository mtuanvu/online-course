<%@ page import="com.fai.study.onlinecourse.entities.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <h2>Danh Sách Khóa Học</h2>
        <a href="<%= request.getContextPath() %>/views/courses/addCourse.jsp" class="btn btn-success">Thêm Khóa Học</a>
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
                <th>Tên Khóa Học</th>
                <th>Mô Tả</th>
                <th>Giảng Viên</th>
                <th>Hành Động</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Course> courses = (List<Course>) request.getAttribute("courses");
                if (courses != null && !courses.isEmpty()) {
                    for (Course course : courses) {
            %>
            <tr>
                <td><%= course.getId() %></td>
                <td><%= course.getName() %></td>
                <td><%= course.getDescription() %></td>
                <td><%= course.getInstructor().getUsername() %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/cours?action=edit&id=<%= course.getId() %>" class="btn btn-warning btn-sm">Sửa</a>
                    <form action="courses" method="post" class="d-inline">
                        <input type="hidden" name="id" value="<%= course.getId() %>">
                        <input type="hidden" name="action" value="delete">
                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</button>
                    </form>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="5" class="text-center text-muted">Không có khóa học nào.</td>
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
