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
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true, length = 50)
    String username;

    @Column(nullable = false,unique = true, length = 100)
    String email;

    @Column(nullable = false)
    String password;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    List<Course> courses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Enrollment> enrollments;
}
