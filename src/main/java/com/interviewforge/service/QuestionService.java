package com.interviewforge.service;

import com.interviewforge.dto.QuestionRequest;
import com.interviewforge.dto.QuestionResponse;

import java.util.List;

public interface QuestionService {

    String addQuestion(QuestionRequest request);

    List<QuestionResponse> getAllQuestions();

    QuestionResponse getQuestionById(Long id);

    String updateQuestion(Long id, QuestionRequest request);

    String deleteQuestion(Long id);
    List<QuestionResponse> getQuestionsByCompany(String company);
}