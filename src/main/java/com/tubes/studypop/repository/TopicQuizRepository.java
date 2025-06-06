package com.tubes.studypop.repository;

import com.tubes.studypop.model.TopikQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicQuizRepository extends JpaRepository<TopikQuiz, Long> {
    // Anda bisa menambahkan query khusus jika diperlukan
}
