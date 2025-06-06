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
        // Mengambil semua topik quiz dari database
        List<TopikQuiz> topics = topicQuizRepository.findAll();
        // Menambahkan topik ke model agar bisa ditampilkan
        model.addAttribute("topik_quiz", topics);
        return "choose_topic";  // Mengembalikan ke halaman memilih topik
    }

    // Menampilkan quiz berdasarkan topik
    @GetMapping("/quiz_page")
    public String quizPage(@RequestParam("topicId") Long topicId,
                           @RequestParam(value = "questionIndex", defaultValue = "0") int questionIndex,
                           Model model) {

        try {
        // Debugging log untuk melihat nilai yang diterima
        System.out.println("Received topicId: " + topicId);
        System.out.println("Received questionIndex: " + questionIndex);

        // Mengambil topik quiz berdasarkan ID
        TopikQuiz topic = topicQuizRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid topic Id"));

        // Mengambil soal (flashcard) terkait dengan topik
        List<Flashcard> flashcards = flashcardRepository.findByTopikQuizId(topicId);

        // Pastikan index tidak melebihi jumlah soal
        if (questionIndex >= flashcards.size()) {
            return "quiz_result"; // Halaman selesai jika soal sudah habis
        }

        // Ambil soal berdasarkan index
        Flashcard flashcard = flashcards.get(questionIndex);

        // Menambahkan soal ke model
        model.addAttribute("flashcard", flashcard);  // Mengirimkan soal
        model.addAttribute("topicName", topic.getName());  // Mengirimkan nama topik
        model.addAttribute("topicId", topicId);  // Menambahkan ID topik ke model
        model.addAttribute("questionIndex", questionIndex); // Menambahkan index soal ke model
        } catch (Exception e) {
            // Log error yang lebih detail
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "error_page";  // Tampilkan halaman error jika terjadi kesalahan
        }
        return "quiz_page"; // Mengarahkan ke halaman quiz_page
    }

    @PostMapping("/submit-answer")
    public String submitAnswer(@RequestParam("flashcardIds[]") Long[] flashcardIds,
                               @RequestParam("userAnswer[]") String[] userAnswers,
                               @RequestParam(value = "questionIndex", defaultValue = "0") int questionIndex,
                               @RequestParam("topicId") Long topicId,
                               @RequestParam("role") String role,
                               Model model) {

        // Debugging log untuk memastikan nilai yang diteruskan
        System.out.println("topicId: " + topicId);
        System.out.println("questionIndex: " + questionIndex);


        // Cek panjang array jawaban dan ID flashcard
        if (flashcardIds.length != userAnswers.length) {
            model.addAttribute("error", "Jumlah soal dan jawaban tidak cocok.");
            return "error_page";  // Tampilkan halaman error jika ada masalah dengan data
        }

        for (int i = 0; i < flashcardIds.length; i++) {
            Long flashcardId = flashcardIds[i];
            String userAnswer = userAnswers[i];

            // Proses setiap flashcard dan jawaban yang diterima
            Flashcard flashcard = flashcardRepository.findById(flashcardId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid flashcard Id"));

            boolean correct = flashcard.getAnswer().equals(userAnswer);

            // Tambahkan logika untuk memproses jawaban (misalnya menambah skor)
            model.addAttribute("correct", correct);
            model.addAttribute("score", correct ? 1 : 0);
        }

        // Lanjutkan logika untuk mengarahkan ke soal berikutnya atau dashboard
        return "redirect:/quiz_page?topicId=" + topicId + "&questionIndex=" + (questionIndex + 1);
    }

}