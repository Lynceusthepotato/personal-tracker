package com.learning.personal.tracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user_list")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition = "serial")
    private Long userId;
    @Column(unique = true, nullable = false)
    private String email;
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Finance finance;
    @OneToMany(mappedBy = "user")
    private List<Todo> todo;

    public User(long userId, String email, String username, String password, LocalDateTime lastLogin, LocalDateTime createdAt) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
    }
}
