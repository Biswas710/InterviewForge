package com.interviewforge.service;

import com.interviewforge.dto.GeminiContent;
import com.interviewforge.dto.GeminiPart;
import com.interviewforge.dto.GeminiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {
    private final WebClient.Builder webClientBuilder;
    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;
    @Override
    public String evaluateAnswer(String question,
                                 String expectedAnswer,
                                 String userAnswer) {

        String prompt = """
            You are an interview evaluator.

            Question:
            %s

            Expected Answer:
            %s

            Candidate Answer:
            %s

            Evaluate the answer and provide:
            1. Score out of 10
            2. Strengths
            3. Weaknesses
            4. Correct Answer
            """
                .formatted(question, expectedAnswer, userAnswer);

        GeminiRequest request = new GeminiRequest(
                List.of(
                        new GeminiContent(
                                List.of(
                                        new GeminiPart(prompt)
                                )
                        )
                )
        );

        String response = webClientBuilder.build()
                .post()
                .uri(apiUrl + "?key=" + apiKey)
                .bodyValue(request)
                .exchangeToMono(clientResponse ->
                        clientResponse.bodyToMono(String.class)
                )
                .block();

        System.out.println(response);

        return response;
    }




}