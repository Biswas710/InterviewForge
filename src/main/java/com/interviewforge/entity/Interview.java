package com.interviewforge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String company;

    private String role;

    private String difficulty;

    private String status;

    private LocalDate interviewDate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}