package com.fai.study.onlinecourse.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enrollments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
            @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
            @JoinColumn(name = "course_id", nullable = false)
    Course course;

    @Column(name = "enrolled_at")
    LocalDateTime enrolledAt = LocalDateTime.now();
}
