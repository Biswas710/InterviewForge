package com.interviewforge.repository;

import com.interviewforge.entity.Interview;
import com.interviewforge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByUser(User user);

}