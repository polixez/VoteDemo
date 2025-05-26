package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String option;

    // Конструкторы
    public Vote() {}

    public Vote(String option) {
        this.option = option;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
