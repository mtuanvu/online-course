package com.fai.study.onlinecourse.controllers;

import com.fai.study.onlinecourse.entities.User;
import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static jakarta.persistence.ParameterMode.IN;

@WebServlet("/users")
public class UserController extends HttpServlet {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Override
    public void init() throws ServletException {
        entityManagerFactory = Persistence.createEntityManagerFactory("OnlineCoursesPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            showEditForm(request, response);
        } else {
            listUsers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateUser(request, response);
        } else if ("delete".equals(action)) {
            deleteUser(request, response);
        } else {
            addUser(request, response);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/views/users/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi lấy danh sách người dùng!");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        User user = entityManager.find(User.class, id);

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/users?error=User not found");
            return;
        }

        request.setAttribute("user", user);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        request.getRequestDispatcher("/views/users/editUser.jsp").forward(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        entityManager.getTransaction().begin();
        try {
            if (username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin!");
            }

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            entityManager.persist(user);
            entityManager.getTransaction().commit();

            response.sendRedirect(request.getContextPath() + "/users");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/views/users/addUser.jsp?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");

        entityManager.getTransaction().begin();
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("UpdateUser");
            query.registerStoredProcedureParameter("user_id", Long.class, IN);
            query.registerStoredProcedureParameter("new_username", String.class, IN);
            query.registerStoredProcedureParameter("new_email", String.class, IN);

            query.setParameter("user_id", id);
            query.setParameter("new_username", username);
            query.setParameter("new_email", email);

            query.execute();
            entityManager.getTransaction().commit();
            entityManager.clear();

            response.sendRedirect(request.getContextPath() + "/users");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/users?action=edit&id=" + id + "&error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }


    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        entityManager.getTransaction().begin();
        try {
            User user = entityManager.find(User.class, id);
            if (user == null) {
                throw new IllegalArgumentException("Người dùng không tồn tại!");
            }

            entityManager.remove(user);
            entityManager.getTransaction().commit();

            response.sendRedirect(request.getContextPath() + "/users");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/users?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }
}
