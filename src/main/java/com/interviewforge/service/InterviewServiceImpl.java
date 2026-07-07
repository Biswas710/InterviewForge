package com.interviewforge.service;

import com.interviewforge.dto.InterviewRequest;
import com.interviewforge.entity.Interview;
import com.interviewforge.entity.User;
import com.interviewforge.repository.InterviewRepository;
import com.interviewforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.interviewforge.dto.InterviewResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

    @Override
    public String createInterview(InterviewRequest request) {

        Authentication authentication =
                org.springframework.security.core.context.SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Interview interview = Interview.builder()
                .title(request.getTitle())
                .company(request.getCompany())
                .role(request.getRole())
                .difficulty(request.getDifficulty())
                .status(request.getStatus())
                .interviewDate(request.getInterviewDate())
                .description(request.getDescription())
                .user(user)
                .build();

        interviewRepository.save(interview);

        return "Interview created successfully";
    }

    @Override
    public List<InterviewResponse> getMyInterviews() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return interviewRepository.findByUser(user)
                .stream()
                .map(interview -> InterviewResponse.builder()
                        .id(interview.getId())
                        .title(interview.getTitle())
                        .company(interview.getCompany())
                        .role(interview.getRole())
                        .difficulty(interview.getDifficulty())
                        .status(interview.getStatus())
                        .interviewDate(interview.getInterviewDate())
                        .description(interview.getDescription())
                        .build())
                .toList();
    }
}