package com.interviewforge.repository;

import com.interviewforge.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCompanyIgnoreCase(String company);

}