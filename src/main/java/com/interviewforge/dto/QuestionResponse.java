package com.interviewforge.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionResponse {

    private Long id;

    private String company;
    private String role;
    private String topic;

    private String difficulty;

    private String question;

    private String answer;
}