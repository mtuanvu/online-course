<%@ page import="com.fai.study.onlinecourse.entities.User" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 02/03/2025
  Time: 11:52 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) request.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/users?error=User khong ton tai");
        return;
    }
%>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <jsp:include page="../../includes/header.jsp"/>
</head>

<body>
<div class="container mt-5">
    <h2 class="mb-4">Chỉnh sửa người dùng</h2>
    <form action="<%= request.getContextPath() %>/users?action=update" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= user.getId() %>">
        <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" name="username" class="form-control" value="<%= user.getUsername() %>" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" value="<%= user.getEmail() %>" required>
        </div>
        <button type="submit" class="btn btn-primary">Cập nhật</button>
        <a href="<%= request.getContextPath() %>/users" class="btn btn-secondary">Hủy</a>
    </form>
</div>
<br>
<jsp:include page="../../includes/footer.jsp"/>
</body>
</html>
