<%@ page import="com.fai.study.onlinecourse.entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 02/03/2025
  Time: 1:35 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String error = request.getParameter("error");
  if (error != null) {
    error = URLDecoder.decode(error, StandardCharsets.UTF_8);
%>
<p style="color: red;"><%= error %></p>
<%
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
  <h2 class="mb-4">Danh Sách Người Dùng</h2>

  <a href="<%= request.getContextPath() %>/views/users/addUser.jsp" class="btn btn-primary mb-3">Thêm Người Dùng</a>

  <table class="table table-bordered table-hover">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>Username</th>
      <th>Email</th>
      <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<User> users = (List<User>) request.getAttribute("users");
      if (users != null) {
        for (User user : users) {
    %>
    <tr>
      <td><%= user.getId() %></td>
      <td><%= user.getUsername() %></td>
      <td><%= user.getEmail() %></td>
      <td>
        <a href="<%= request.getContextPath() %>/users?action=edit&id=<%= user.getId() %>" class="btn btn-warning btn-sm">Sửa</a>
        <form action="<%= request.getContextPath() %>/users" method="post" style="display:inline;">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="id" value="<%= user.getId() %>">
          <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</button>
        </form>

      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="4" class="text-center">Không có dữ liệu</td>
    </tr>
    <%
      }
    %>

    </tbody>
  </table>
</div>
<br>
<jsp:include page="../../includes/footer.jsp"/>
</body>
</html>
