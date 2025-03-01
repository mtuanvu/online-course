package com.fai.study.salesmanagement.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, length = 100)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    @ManyToOne
            @JoinColumn(name = "instructor_id")
    User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    List<Enrollment> enrollments;
}
