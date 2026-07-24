package com.interviewforge.service;

import com.interviewforge.dto.EvaluationResult;

public interface GeminiService {



        EvaluationResult evaluateAnswer(
                String question,
                String expectedAnswer,
                String userAnswer
        );
    }
