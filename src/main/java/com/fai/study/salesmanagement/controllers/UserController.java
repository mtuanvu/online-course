package com.fai.study.salesmanagement.controllers;

import com.fai.study.salesmanagement.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserController extends HttpServlet {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    @Override
    public void init() throws ServletException {
        entityManagerFactory = Persistence.createEntityManagerFactory("OnlineCoursesPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = entityManager.createQuery("SELECT u from User u", User.class).getResultList();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/views/users/index.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        entityManager.getTransaction().begin();
        try {
            if (username == null || email == null || password == null ||
                    username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
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
            response.sendRedirect(request.getContextPath() + "addUser.jsp?error=" + e.getMessage());
        }
    }

}
