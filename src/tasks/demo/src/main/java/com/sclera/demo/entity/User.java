package com.sclera.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false, unique = true)
    private  String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    private String role;

}
