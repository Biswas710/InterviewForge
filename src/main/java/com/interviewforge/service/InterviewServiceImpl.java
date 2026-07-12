package com.interviewforge.service;

import com.interviewforge.dto.*;
import com.interviewforge.entity.*;
import com.interviewforge.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final InterviewAnswerRepository interviewAnswerRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;
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
               .totalQuestions(request.getTotalQuestions())
               .user(user)
               .build();

        interviewRepository.save(interview);

        return "Interview created successfully";
    }

    @Override
    @Transactional
    public void submitAnswers(Long interviewId,
                              AnswerSubmissionRequest request) {

        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        for (InterviewAnswerRequest answerRequest : request.getAnswers()) {

            // Validate that this question belongs to this interview
            boolean assigned = interviewQuestionRepository
                    .existsByInterviewAndQuestionId(
                            interview,
                            answerRequest.getQuestionId()
                    );

            if (!assigned) {
                throw new RuntimeException(
                        "Question does not belong to this interview."
                );
            }

            Question question = questionRepository.findById(
                    answerRequest.getQuestionId()
            ).orElseThrow(() ->
                    new RuntimeException("Question not found"));

            InterviewAnswer answer = InterviewAnswer.builder()
                    .interview(interview)
                    .question(question)
                    .userAnswer(answerRequest.getUserAnswer())
                    .build();

            interviewAnswerRepository.save(answer);
        }

        interview.setStatus("COMPLETED");
        interview.setCompletedAt(LocalDateTime.now());

        interviewRepository.save(interview);
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
    @Transactional
    public List<InterviewQuestionResponse> startInterview(Long interviewId) {

        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        List<Question> questions = questionRepository.findRandomQuestions(
                interview.getCompany(),
                interview.getDifficulty(),
                interview.getTotalQuestions()
        );

        interviewQuestionRepository.deleteAllByInterview(interview);

        List<InterviewQuestionResponse> response = new ArrayList<>();

        int order = 1;

        for (Question question : questions) {

            InterviewQuestion interviewQuestion = InterviewQuestion.builder()
                    .interview(interview)
                    .question(question)
                    .questionOrder(order)
                    .build();

            interviewQuestionRepository.save(interviewQuestion);

            response.add(
                    InterviewQuestionResponse.builder()
                            .id(question.getId())
                            .topic(question.getTopic())
                            .difficulty(question.getDifficulty())
                            .question(question.getQuestion())
                            .build()
            );

            order++;
        }

        interview.setStatus("IN_PROGRESS");
        interviewRepository.save(interview);

        return response;
    }

}