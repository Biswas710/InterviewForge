package com.interviewforge.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class InterviewResponse {

    private Long id;

    private String title;

    private String company;

    private String role;

    private String difficulty;

    private String status;

    private LocalDate interviewDate;

    private String description;
}