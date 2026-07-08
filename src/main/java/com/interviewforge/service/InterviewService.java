package com.interviewforge.service;

import com.interviewforge.dto.InterviewRequest;
import com.interviewforge.dto.InterviewResponse;
import com.interviewforge.dto.QuestionResponse;

import java.util.List;

public interface InterviewService {

    String createInterview(InterviewRequest request);

    List<InterviewResponse> getMyInterviews();
    InterviewResponse getInterviewById(Long id);

    String updateInterview(Long id, InterviewRequest request);

    String deleteInterview(Long id);

}