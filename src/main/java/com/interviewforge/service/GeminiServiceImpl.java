package com.interviewforge.service;

import com.interviewforge.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {
    private final WebClient.Builder webClientBuilder;

    private final ObjectMapper objectMapper;
    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;
    @Override
    public EvaluationResult evaluateAnswer(String question,
                                           String expectedAnswer,
                                           String userAnswer) {

        String prompt = """
You are an expert technical interviewer.

Question:
%s

Expected Answer:
%s

Candidate Answer:
%s

Compare the candidate answer with the expected answer.

Evaluate based on:
- Technical correctness
- Completeness
- Accuracy

Return ONLY a valid JSON object.

Example:
{
  "score": 8,
  "feedback": "Good understanding of JVM. Mention bytecode and class loading for full marks."
}

Rules:
- score must be an integer between 0 and 10.
- feedback should be concise (2-4 sentences).
- Do not return markdown.
- Do not use ```json.
- Do not return any explanation outside the JSON.
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

        GeminiResponse response = webClientBuilder.build()
                .post()
                .uri(apiUrl + "?key=" + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .block();

        String json = response.getCandidates()
                .get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText();

        System.out.println(json);

        try {
            return objectMapper.readValue(json, EvaluationResult.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini response", e);
        }
    }




}