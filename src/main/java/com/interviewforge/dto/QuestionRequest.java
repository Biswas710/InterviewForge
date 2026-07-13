package com.interviewforge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {

    private String company;
    private String role;
    private String topic;

    private String difficulty;

    private String question;

    private String answer;
}