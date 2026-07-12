package com.interviewforge.repository;

import com.interviewforge.entity.Interview;
import com.interviewforge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByUser(User user);
    Optional<Interview> findByIdAndUser(Long id, User user);

}