package com.interviewforge.service;

import com.interviewforge.dto.InterviewQuestionResponse;
import com.interviewforge.dto.InterviewRequest;
import com.interviewforge.entity.Interview;
import com.interviewforge.entity.Question;
import com.interviewforge.entity.User;
import com.interviewforge.repository.InterviewRepository;
import com.interviewforge.repository.QuestionRepository;
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
    private final QuestionRepository questionRepository;
    @Override
    public String createInterview(InterviewRequest request) {

        User user = getCurrentUser();

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

        User user = getCurrentUser();

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
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public InterviewResponse getInterviewById(Long id) {

        User user = getCurrentUser();

        Interview interview = interviewRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Interview not found"));

        return InterviewResponse.builder()
                .id(interview.getId())
                .title(interview.getTitle())
                .company(interview.getCompany())
                .role(interview.getRole())
                .difficulty(interview.getDifficulty())
                .status(interview.getStatus())
                .interviewDate(interview.getInterviewDate())
                .description(interview.getDescription())
                .build();
    }
    @Override
    public String updateInterview(Long id, InterviewRequest request) {

        User user = getCurrentUser();

        Interview interview = interviewRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Interview not found"));

        interview.setTitle(request.getTitle());
        interview.setCompany(request.getCompany());
        interview.setRole(request.getRole());
        interview.setDifficulty(request.getDifficulty());
        interview.setStatus(request.getStatus());
        interview.setInterviewDate(request.getInterviewDate());
        interview.setDescription(request.getDescription());

        interviewRepository.save(interview);

        return "Interview updated successfully";
    }
    @Override
    public String deleteInterview(Long id) {

        User user = getCurrentUser();

        Interview interview = interviewRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Interview not found"));

        interviewRepository.delete(interview);

        return "Interview deleted successfully";
    }
    @Override
    public List<InterviewQuestionResponse> startInterview(Long interviewId) {

        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        List<Question> questions = questionRepository.findRandomQuestions(
                interview.getCompany(),
                interview.getDifficulty(),
                interview.getTotalQuestions()
        );

        interview.setStatus("IN_PROGRESS");
        interviewRepository.save(interview);

        return questions.stream()
                .map(question -> InterviewQuestionResponse.builder()
                        .id(question.getId())
                        .topic(question.getTopic())
                        .difficulty(question.getDifficulty())
                        .question(question.getQuestion())
                        .build())
                .toList();
    }

}