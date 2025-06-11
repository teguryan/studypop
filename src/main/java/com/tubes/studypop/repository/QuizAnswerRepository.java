package com.tubes.studypop.repository;

import com.tubes.studypop.model.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
//    List<QuizAnswer> findByUser_Id(Integer userId);

    List<QuizAnswer> findByUser_IdAndFlashcard_TopikQuiz_Id(Integer userId, Long topikId);

}
