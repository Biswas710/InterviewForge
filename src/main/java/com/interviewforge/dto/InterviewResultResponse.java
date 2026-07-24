package com.interviewforge.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewResultResponse {

    private Long interviewId;

    private String company;

    private String role;

    private Integer totalScore;

    private Double averageScore;

    private List<AnswerResultResponse> answers;
}