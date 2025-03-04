<%@ page import="com.fai.study.onlinecourse.entities.Course" %>
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
    <div class="card">
        <div class="card-header bg-warning text-dark">
            <h2 class="mb-0">Sửa Khóa Học</h2>
        </div>
        <div class="card-body">
            <%
                Course course = (Course) request.getAttribute("course");
                if (course != null) {
            %>

            <form action="courses" method="post">
                <input type="hidden" name="id" value="<%= course.getId() %>">
                <input type="hidden" name="action" value="update">

                <div class="mb-3">
                    <label class="form-label">Tên Khóa Học:</label>
                    <input type="text" name="name" class="form-control" value="<%= course.getName() %>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mô Tả:</label>
                    <textarea name="description" class="form-control" rows="3"><%= course.getDescription() %></textarea>
                </div>

                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-primary">Cập Nhật</button>
                    <a href="index.jsp" class="btn btn-secondary">Quay lại</a>
                </div>
            </form>

            <% } else { %>
            <div class="alert alert-danger">Khóa học không tồn tại!</div>
            <% } %>
        </div>
    </div>
</div>

<jsp:include page="../../includes/footer.jsp"/>
</body>
</html>
