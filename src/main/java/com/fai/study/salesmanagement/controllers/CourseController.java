package com.fai.study.salesmanagement.controllers;

import com.fai.study.salesmanagement.entities.Course;
import com.fai.study.salesmanagement.entities.User;
import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/courses")
public class CourseController extends HttpServlet {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    @Override
    public void init() throws ServletException {
        entityManagerFactory = Persistence.createEntityManagerFactory("OnlineCoursesPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Course> courses = entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        List<User> instructors = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        req.setAttribute("courses", courses);
        req.setAttribute("instructors", instructors);
        req.getRequestDispatcher("/views/courses/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            int instructorId = Integer.parseInt(req.getParameter("instructor_id"));

            User instructor = entityManager.find(User.class, instructorId);
            if (instructor == null) {
                resp.sendRedirect("./courses");
                return;
            }

            Course course = new Course();
            course.setName(name);
            course.setDescription(description);
            course.setInstructor(instructor);

            entityManager.getTransaction().begin();
            entityManager.persist(course);
            entityManager.getTransaction().commit();

            resp.sendRedirect("./courses");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            resp.sendRedirect("./courses");
        }
    }
}
