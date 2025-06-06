package com.tubes.studypop.repository;

import com.tubes.studypop.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findByTopikQuizId(Long topikQuizId);  // Mencari flashcard berdasarkan topik quiz ID
}
