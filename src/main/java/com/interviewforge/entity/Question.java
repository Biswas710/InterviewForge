package com.interviewforge.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false, length = 1000)
    private String question;

    @Column(length = 5000)
    private String answer;
}