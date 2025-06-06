package com.tubes.studypop.controller;

import com.tubes.studypop.model.TopikQuiz;
import com.tubes.studypop.model.Flashcard;
import com.tubes.studypop.repository.FlashcardRepository;
import com.tubes.studypop.service.Quiz;
import com.tubes.studypop.repository.TopicQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuizController {

    @Autowired
    private TopicQuizRepository topicQuizRepository;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private Quiz quiz;

    // Menampilkan halaman untuk memilih topik quiz
    @GetMapping("/choose_topic")
    public String chooseTopic(Model model) {
        List<TopikQuiz> topics = topicQuizRepository.findAll();
        model.addAttribute("topik_quiz", topics);
        return "choose_topic";  // Halaman untuk memilih topik
    }

    // Menampilkan quiz berdasarkan topik
    @GetMapping("/start_quiz")  //nama file html nya
    public String startQuiz(@RequestParam("topicId") Long topicId, Model model) {
        TopikQuiz topic = topicQuizRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid topic Id"));

        // Mulai quiz untuk topik yang dipilih
        List<Flashcard> flashcards = quiz.startQuiz(topic);
        model.addAttribute("flashcard", flashcards);   // pakai nama tabel di mysql

        // Menampilkan halaman quiz
        return "quiz_page";  // Halaman quiz untuk memulai kuis
    }

    // Menangani pengiriman jawaban dan menghitung skor
    @PostMapping("/submit_answer")
    public String submitAnswer(@RequestParam("userAnswer") String userAnswer,
                               @RequestParam("flashcardId") Long flashcardId,
                               @RequestParam("role") String role,
                               Model model) {
        Flashcard flashcard = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid flashcard Id"));

        // Cek jawaban pengguna
        boolean correct = quiz.checkAnswer(flashcard, userAnswer);
        model.addAttribute("correct", correct);
        model.addAttribute("score", quiz.getScore());
        model.addAttribute("role", role);

        // Arahkan ke halaman yang sesuai berdasarkan role
        if ("student".equalsIgnoreCase(role)) {
            return "redirect:/student_dashboard";  // Jika role student, redirect ke student dashboard
        } else {
            return "redirect:/admin_dashboard";  // Jika role admin, redirect ke admin dashboard
        }
    }
}
