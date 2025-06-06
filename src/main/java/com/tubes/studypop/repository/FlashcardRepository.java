package com.tubes.studypop.repository;

import com.tubes.studypop.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findByTopikQuizId(Long topikQuizId);  // Mencari flashcard berdasarkan topik quiz ID

    // Menghitung jumlah flashcard berdasarkan topik_quiz_id
    @Query("SELECT COUNT(f) FROM Flashcard f WHERE f.topikQuiz.id = :topicQuizId")
    long countByTopikQuizId(@Param("topicQuizId") Long topicQuizId);
}
