package com.interviewforge.controller;

import com.interviewforge.dto.AnswerSubmissionRequest;
import com.interviewforge.dto.InterviewQuestionResponse;
import com.interviewforge.dto.InterviewRequest;
import com.interviewforge.dto.InterviewResponse;

import com.interviewforge.service.GeminiService;
import com.interviewforge.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping
    public String createInterview(@Valid @RequestBody InterviewRequest request) {
        return interviewService.createInterview(request);
    }
    private final GeminiService geminiService;

    @GetMapping("/test-gemini")
    public String testGemini() {

        return geminiService.evaluateAnswer(
                "What is JVM?",
                "JVM is Java Virtual Machine responsible for running Java bytecode.",
                "JVM is Java"
        );
    }
    @GetMapping
    public List<InterviewResponse> getMyInterviews() {
        return interviewService.getMyInterviews();

    }
    @GetMapping("/{id}")
    public InterviewResponse getInterviewById(@PathVariable Long id) {

        return interviewService.getInterviewById(id);

    }
    @PutMapping("/{id}")
    public String updateInterview(@PathVariable Long id,
                                  @RequestBody InterviewRequest request) {

        return interviewService.updateInterview(id, request);
    }
    @DeleteMapping("/{id}")
    public String deleteInterview(@PathVariable Long id) {

        return interviewService.deleteInterview(id);

    }
    @PostMapping("/{id}/start")
    public ResponseEntity<List<InterviewQuestionResponse>> startInterview(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                interviewService.startInterview(id)
        );
    }
    @PostMapping("/{id}/submit")
    public ResponseEntity<String> submitAnswers(
            @PathVariable Long id,
            @RequestBody AnswerSubmissionRequest request) {

        interviewService.submitAnswers(id, request);

        return ResponseEntity.ok("Interview submitted successfully.");
    }
}