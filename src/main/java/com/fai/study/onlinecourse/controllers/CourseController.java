package com.fai.study.onlinecourse.controllers;

import com.fai.study.onlinecourse.entities.Course;
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

@WebServlet("/courses")
public class CourseController extends HttpServlet {
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
        } else if ("add".equals(action)) {
            showAddForm(request, response);
        } else {
            listCourses(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateCourse(request, response);
        } else if ("delete".equals(action)) {
            deleteCourse(request, response);
        } else {
            addCourse(request, response);
        }
    }

    private void listCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Course> courses = entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
            request.setAttribute("courses", courses);
            request.getRequestDispatcher("/views/courses/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi lấy danh sách khóa học!");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = entityManager.find(Course.class, id);

        if (course == null) {
            response.sendRedirect(request.getContextPath() + "/courses?error=Course not found");
            return;
        }

        request.setAttribute("course", course);
        request.getRequestDispatcher("/views/courses/editCourse.jsp").forward(request, response);
    }

    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        String instructorIdParam = request.getParameter("instructor_id");
        if (instructorIdParam == null || instructorIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/courses?action=add&error=" + URLEncoder.encode("Vui lòng chọn giảng viên!", StandardCharsets.UTF_8));
            return;
        }

        int instructorId = Integer.parseInt(instructorIdParam);

        entityManager.getTransaction().begin();
        try {
            User instructor = entityManager.find(User.class, instructorId);
            if (instructor == null) {
                throw new IllegalArgumentException("Giảng viên không tồn tại!");
            }

            Course course = new Course();
            course.setName(name);
            course.setDescription(description);
            course.setInstructor(instructor);

            entityManager.persist(course);
            entityManager.getTransaction().commit();

            response.sendRedirect(request.getContextPath() + "/courses");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/courses?action=add&error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }


    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
            System.out.println(users);
            request.setAttribute("users", users);
            request.getRequestDispatcher("/views/courses/addCourse.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi lấy danh sách giảng viên!");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }



    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        entityManager.getTransaction().begin();
        try {
            Course course = entityManager.find(Course.class, id);
            if (course == null) {
                throw new IllegalArgumentException("Khóa học không tồn tại!");
            }

            course.setName(name);
            course.setDescription(description);

            entityManager.getTransaction().commit();
            response.sendRedirect(request.getContextPath() + "/courses");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/courses?action=edit&id=" + id + "&error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        entityManager.getTransaction().begin();
        try {
            Course course = entityManager.find(Course.class, id);
            if (course == null) {
                throw new IllegalArgumentException("Khóa học không tồn tại!");
            }

            entityManager.remove(course);
            entityManager.getTransaction().commit();
            response.sendRedirect(request.getContextPath() + "/courses");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/courses?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }
}
