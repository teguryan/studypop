package com.tubes.studypop.service;

import com.tubes.studypop.model.TopikQuiz;
import com.tubes.studypop.model.Flashcard;
import com.tubes.studypop.repository.TopicQuizRepository;
import com.tubes.studypop.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    private TopicQuizRepository topicQuizRepository;

    @Autowired
    private FlashcardRepository flashcardRepository;

    public void createTopicAndFlashcards() {
        // Membuat topik baru
        TopikQuiz topic = new TopikQuiz();
        topic.setName("Matematika Dasar");

        // Menyimpan topik ke database
        topicQuizRepository.save(topic);

        // Menambahkan soal ke topik
        Flashcard flashcard1 = new Flashcard();
        flashcard1.setQuestion("Berapa hasil dari 5 + 3?");
        flashcard1.setAnswer("8");
        flashcard1.setTopikQuiz(topic);  // Menyambungkan soal ke topik
        flashcardRepository.save(flashcard1);

        Flashcard flashcard2 = new Flashcard();
        flashcard2.setQuestion("Berapa hasil dari 10 - 4?");
        flashcard2.setAnswer("6");
        flashcard2.setTopikQuiz(topic);
        flashcardRepository.save(flashcard2);
    }
}
