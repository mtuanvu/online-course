<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 02/03/2025
  Time: 1:36 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-4">Thêm Người Dùng</h2>
  <form action="<%= request.getContextPath() %>/users" method="post">
  <div class="mb-3">
      <label class="form-label">Username</label>
      <input type="text" name="username" class="form-control" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Email</label>
      <input type="email" name="email" class="form-control" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Password</label>
      <input type="password" name="password" class="form-control" required>
    </div>
    <button type="submit" class="btn btn-success">Thêm</button>
    <a href="<%= request.getContextPath() %>/users" class="btn btn-secondary">Hủy</a>
  </form>
</div>
</body>
</html>
