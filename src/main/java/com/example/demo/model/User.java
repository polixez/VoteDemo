package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Избегаем конфликтов с зарезервированными словами SQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Конструкторы
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    // id не имеет сеттера, так как генерируется автоматически

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
