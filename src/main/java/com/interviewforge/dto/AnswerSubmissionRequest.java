package com.interviewforge.dto;

import com.interviewforge.dto.InterviewAnswerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerSubmissionRequest {

    private List<InterviewAnswerRequest> answers;
}