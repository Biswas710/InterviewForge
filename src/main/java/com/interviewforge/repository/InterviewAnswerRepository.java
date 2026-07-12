package com.interviewforge.repository;

import com.interviewforge.entity.Interview;
import com.interviewforge.entity.InterviewAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewAnswerRepository extends JpaRepository<InterviewAnswer, Long> {

    List<InterviewAnswer> findByInterview(Interview interview);

}