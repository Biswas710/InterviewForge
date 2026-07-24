package com.interviewforge.service;

import com.interviewforge.dto.*;

import java.util.List;

public interface InterviewService {

    String createInterview(InterviewRequest request);

    List<InterviewResponse> getMyInterviews();
    InterviewResponse getInterviewById(Long id);
    InterviewResultResponse getInterviewResult(Long interviewId);
    String updateInterview(Long id, InterviewRequest request);

    String deleteInterview(Long id);
    List<InterviewQuestionResponse> startInterview(Long interviewId);
    void submitAnswers(Long interviewId,AnswerSubmissionRequest request);
}