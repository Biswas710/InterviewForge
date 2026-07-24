package com.interviewforge.repository;

import com.interviewforge.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCompanyIgnoreCase(String company);

    @Query(value = """
SELECT * FROM questions
WHERE LOWER(company) = LOWER(:company)
  AND LOWER(role) = LOWER(:role)
  AND LOWER(difficulty) = LOWER(:difficulty)
ORDER BY RAND()
LIMIT :limit
""", nativeQuery = true)
    List<Question> findRandomQuestions(
            @Param("company") String company,
            @Param("role") String role,
            @Param("difficulty") String difficulty,
            @Param("limit") int limit
    );
}