package com.interviewforge.repository;

import com.interviewforge.entity.Interview;
import com.interviewforge.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findByInterviewOrderByQuestionOrderAsc(
            Interview interview
    );

    boolean existsByInterviewAndQuestionId(
            Interview interview,
            Long questionId
    );
    void deleteAllByInterview(Interview interview);
}