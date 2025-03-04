package com.fai.study.onlinecourse.controllers;

import com.fai.study.onlinecourse.entities.Course;
import com.fai.study.onlinecourse.entities.Enrollment;
import com.fai.study.onlinecourse.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/enrollments")
public class EnrollmentController extends HttpServlet {
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
        if ("add".equals(action)) {
            showEnrollmentForm(request, response);
        } else {
            listEnrollments(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        addEnrollment(request, response);
    }

    private void listEnrollments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Enrollment> enrollments = entityManager.createQuery("SELECT e FROM Enrollment e", Enrollment.class).getResultList();
            request.setAttribute("enrollments", enrollments);
            request.getRequestDispatcher("/views/enrollments/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi lấy danh sách đăng ký!");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void showEnrollmentForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
            List<Course> courses = entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();

            request.setAttribute("users", users);
            request.setAttribute("courses", courses);

            request.getRequestDispatcher("/views/enrollments/enrollment.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải danh sách người dùng và khóa học!");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void addEnrollment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int courseId = Integer.parseInt(request.getParameter("course_id"));

        entityManager.getTransaction().begin();
        try {
            User user = entityManager.find(User.class, userId);
            Course course = entityManager.find(Course.class, courseId);

            if (user == null || course == null) {
                throw new IllegalArgumentException("Người dùng hoặc khóa học không tồn tại!");
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setUser(user);
            enrollment.setCourse(course);
            enrollment.setEnrolledAt(LocalDateTime.now());

            entityManager.persist(enrollment);
            entityManager.getTransaction().commit();

            response.sendRedirect(request.getContextPath() + "/enrollments");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/enrollments?action=add&error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }
}
