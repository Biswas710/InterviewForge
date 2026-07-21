package com.interviewforge.service;

import com.interviewforge.dto.GeminiContent;
import com.interviewforge.dto.GeminiPart;
import com.interviewforge.dto.GeminiRequest;
import com.interviewforge.dto.GeminiResponse;
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

                Compare the candidate answer with the expected answer.
                
                                  Scoring Rules:
                                  - 10/10: Candidate answer is semantically identical to the expected answer, even if wording differs.
                                  - 9–10: Minor grammatical or spelling mistakes only.
                                  - 7–8: Correct concept but missing some important details.
                                  - 4–6: Partially correct with significant missing concepts.
                                  - 0–3: Incorrect or irrelevant answer.
                
                                  Do NOT deduct marks for wording differences if the meaning is the same.
                
                                  Return:
                                  Score: X/10
                                  Strengths:
                                  Weaknesses:
                                  Correct Answer:
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

        System.out.println(response);

        return response.getCandidates()
                .get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText();
    }




}