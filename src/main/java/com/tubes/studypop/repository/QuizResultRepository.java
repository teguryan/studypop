package com.tubes.studypop.repository;

import com.tubes.studypop.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByUser_Id(Integer userId);
}
