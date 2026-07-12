package com.interviewforge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InterviewRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Company is required")
    private String company;

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Difficulty is required")
    private String difficulty;

    @NotBlank(message = "Status is required")
    private String status;

    private LocalDate interviewDate;

    private String description;
    private Integer totalQuestions;
}