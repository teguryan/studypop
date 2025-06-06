package com.tubes.studypop.service;

import com.tubes.studypop.model.Flashcard;
import com.tubes.studypop.model.TopikQuiz;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collections;

@Service
public class Quiz {

    private int score = 0;

    // Fungsi untuk memulai quiz berdasarkan topik tertentu
    public List<Flashcard> startQuiz(TopikQuiz topikQuiz) {
        // Mengambil semua soal dalam topik yang dipilih
        List<Flashcard> flashcards = topikQuiz.getFlashcards();

        // Mengacak soal (optional, tergantung kebutuhan)
        Collections.shuffle(flashcards);

        return flashcards;
    }

    // Fungsi untuk mengecek jawaban pengguna
    public boolean checkAnswer(Flashcard flashcard, String userAnswer) {
        boolean correct = flashcard.getAnswer().equalsIgnoreCase(userAnswer);
        if (correct) {
            score++;  // Jika jawaban benar, skor bertambah
        }
        return correct;
    }

    // Fungsi untuk mendapatkan skor akhir
    public int getScore() {
        return score;  // Mengembalikan skor pengguna
    }
}
