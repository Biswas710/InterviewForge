package com.interviewforge.controller;

import com.interviewforge.dto.InterviewRequest;
import com.interviewforge.dto.InterviewResponse;
import com.interviewforge.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public List<InterviewResponse> getMyInterviews() {
        return interviewService.getMyInterviews();
    }
}