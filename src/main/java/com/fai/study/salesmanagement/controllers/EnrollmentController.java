package com.fai.study.salesmanagement.controllers;

import com.fai.study.salesmanagement.entities.Course;
import com.fai.study.salesmanagement.entities.Enrollment;
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
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/enrollments")
public class EnrollmentController extends HttpServlet {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    @Override
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("OnlineCoursesPU");
        entityManager = entityManagerFactory.createEntityManager();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Enrollment> enrollments = entityManager.createQuery("SELECT e FROM Enrollment e", Enrollment.class).getResultList();
        List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        List<Course> courses = entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();

        req.setAttribute("enrollments", enrollments);
        req.setAttribute("users", users);
        req.setAttribute("courses", courses);
        req.getRequestDispatcher("/views/enrollments/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("user_id"));
            int courseId = Integer.parseInt(req.getParameter("course_id"));

            User user = entityManager.find(User.class, userId);
            Course course = entityManager.find(Course.class, courseId);

            if (user == null || course == null) {
                resp.sendRedirect("./enrollments");
                return;
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setUser(user);
            enrollment.setCourse(course);
            enrollment.setEnrolledAt(LocalDateTime.now());

            entityManager.getTransaction().begin();
            entityManager.persist(enrollment);
            entityManager.getTransaction().commit();

            resp.sendRedirect("./enrollments");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            resp.sendRedirect("./enrollments");
        }
    }
}
