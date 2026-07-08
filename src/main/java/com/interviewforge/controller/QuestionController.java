package com.interviewforge.controller;

import com.interviewforge.dto.QuestionRequest;
import com.interviewforge.dto.QuestionResponse;
import com.interviewforge.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public String addQuestion(@RequestBody QuestionRequest request) {
        return questionService.addQuestion(request);
    }

    @GetMapping
    public List<QuestionResponse> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public QuestionResponse getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id,
                                 @RequestBody QuestionRequest request) {
        return questionService.updateQuestion(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }
    @GetMapping("/company/{company}")
    public List<QuestionResponse> getQuestionsByCompany(
            @PathVariable String company) {

        return questionService.getQuestionsByCompany(company);
    }
}