<%@ page import="com.fai.study.onlinecourse.entities.User" %>
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
    <div class="card">
        <div class="card-header bg-primary text-white">
            <h2 class="mb-0">Thêm Khóa Học</h2>
        </div>
        <div class="card-body">
            <!-- Hiển thị lỗi nếu có -->
            <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger"><%= request.getParameter("error") %></div>
            <% } %>

            <form action="courses" method="post">
                <div class="mb-3">
                    <label class="form-label">Tên Khóa Học:</label>
                    <input type="text" name="name" class="form-control" placeholder="Nhập tên khóa học" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mô Tả:</label>
                    <textarea name="description" class="form-control" rows="3" placeholder="Nhập mô tả khóa học"></textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label">Giảng Viên:</label>
                    <select name="instructor_id" class="form-select" required>
                        <option value="">-- Chọn giảng viên --</option>
                        <%
                            List<User> users = (List<User>) request.getAttribute("users");
                            if (users != null && !users.isEmpty()) {
                                for (User user : users) {
                        %>
                        <option value="<%= user.getId() %>"><%= user.getUsername() %></option>
                        <%
                            }
                        } else {
                        %>
                        <option value="">Không có giảng viên nào</option>
                        <% } %>
                    </select>
                </div>

                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-primary">Thêm Khóa Học</button>
                    <a href="<%= request.getContextPath() %>/courses" class="btn btn-secondary">Quay lại</a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../../includes/footer.jsp"/>
</body>
</html>
