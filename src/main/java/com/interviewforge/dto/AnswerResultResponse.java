package com.interviewforge.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerResultResponse {

    private String question;

    private String expectedAnswer;

    private String userAnswer;

    private Integer score;

    private String feedback;
}