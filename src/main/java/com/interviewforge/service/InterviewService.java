package com.interviewforge.service;

import com.interviewforge.dto.InterviewRequest;
import com.interviewforge.dto.InterviewResponse;

import java.util.List;

public interface InterviewService {

    String createInterview(InterviewRequest request);

    List<InterviewResponse> getMyInterviews();
}