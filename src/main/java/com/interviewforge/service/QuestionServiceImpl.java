package com.interviewforge.service;

import com.interviewforge.dto.QuestionRequest;
import com.interviewforge.dto.QuestionResponse;
import com.interviewforge.entity.Question;
import com.interviewforge.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public String addQuestion(QuestionRequest request) {

        Question question = Question.builder()
                .company(request.getCompany())
                .role(request.getRole())
                .topic(request.getTopic())
                .difficulty(request.getDifficulty())
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .build();

        questionRepository.save(question);

        return "Question added successfully";
    }

    @Override
    public List<QuestionResponse> getAllQuestions() {

        return questionRepository.findAll()
                .stream()
                .map(question -> QuestionResponse.builder()
                        .id(question.getId())
                        .company(question.getCompany())
                        .role(question.getRole())
                        .topic(question.getTopic())
                        .difficulty(question.getDifficulty())
                        .question(question.getQuestion())
                        .answer(question.getAnswer())
                        .build())
                .toList();

    }

    @Override
    public QuestionResponse getQuestionById(Long id) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        return QuestionResponse.builder()
                .id(question.getId())
                .company(question.getCompany())
                .role(question.getRole())
                .topic(question.getTopic())
                .difficulty(question.getDifficulty())
                .question(question.getQuestion())
                .answer(question.getAnswer())
                .build();
    }

    @Override
    public String updateQuestion(Long id, QuestionRequest request) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setCompany(request.getCompany());
        question.setRole(request.getRole());
        question.setTopic(request.getTopic());
        question.setDifficulty(request.getDifficulty());
        question.setQuestion(request.getQuestion());
        question.setAnswer(request.getAnswer());
        questionRepository.save(question);

        return "Question updated successfully";
    }

    @Override
    public String deleteQuestion(Long id) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        questionRepository.delete(question);

        return "Question deleted successfully";
    }
    @Override
    public List<QuestionResponse> getQuestionsByCompany(String company) {

        return questionRepository.findByCompanyIgnoreCase(company)
                .stream()
                .map(question -> QuestionResponse.builder()
                        .id(question.getId())
                        .company(question.getCompany())
                        .role(question.getRole())
                        .topic(question.getTopic())
                        .difficulty(question.getDifficulty())
                        .question(question.getQuestion())
                        .answer(question.getAnswer())
                        .build())
                .toList();
    }
}