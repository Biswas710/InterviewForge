package com.interviewforge.service;

public interface GeminiService {

    String evaluateAnswer(
            String question,
            String expectedAnswer,
            String userAnswer
    );
}