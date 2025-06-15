package com.tubes.studypop.controller;

import com.tubes.studypop.model.Flashcard;
import com.tubes.studypop.model.QuizAnswer;
import com.tubes.studypop.model.TopikQuiz;
import com.tubes.studypop.model.User;
import com.tubes.studypop.repository.FlashcardRepository;
import com.tubes.studypop.repository.TopicQuizRepository;
import com.tubes.studypop.repository.QuizAnswerRepository;
import com.tubes.studypop.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AdminFlashcardController {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private TopicQuizRepository topicQuizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;


    @GetMapping("/admin/flashcards")
    public String showFlashcards(@RequestParam(required = false) Integer userId, Model model) {
        try {
            // Log untuk debugging
            System.out.println("Accessing /admin/flashcards endpoint");

            List<Flashcard> flashcards = flashcardRepository.findAll();
            List<TopikQuiz> topics = topicQuizRepository.findAll(); // Tambahkan ini untuk modal

            // Debug log
            System.out.println("Found " + (flashcards != null ? flashcards.size() : "null") + " flashcards");

            // Ensure flashcards is never null
            if (flashcards == null) {
                flashcards = new ArrayList<>();
            }

            // Validate each flashcard and its relationships
            for (int i = 0; i < flashcards.size(); i++) {
                Flashcard fc = flashcards.get(i);
                System.out.println("Flashcard " + i + ": " +
                        "ID=" + fc.getId() +
                        ", Question=" + (fc.getQuestion() != null ? fc.getQuestion().substring(0, Math.min(20, fc.getQuestion().length())) : "null") +
                        ", TopikQuiz=" + (fc.getTopikQuiz() != null ? fc.getTopikQuiz().getName() : "null"));

                // Handle null topikQuiz
                if (fc.getTopikQuiz() == null) {
                    System.err.println("Warning: Flashcard " + fc.getId() + " has null topikQuiz");
                }
            }

            model.addAttribute("flashcards", flashcards);
            model.addAttribute("topics", topics); // Tambahkan topics untuk dropdown di modal

            if (userId != null) {
                model.addAttribute("userId", userId);
            }

            System.out.println("Successfully prepared model for admin_flashcard_list");
            return "admin_flashcard_list";

        } catch (Exception e) {
            System.err.println("Error in showFlashcards: " + e.getMessage());
            e.printStackTrace();

            // Add error details to model
            model.addAttribute("error", "Error loading flashcards: " + e.getMessage());
            model.addAttribute("flashcards", new ArrayList<>());
            model.addAttribute("topics", new ArrayList<>()); // Tambahkan topics kosong jika error

            // Still return the template, but with error message
            return "admin_flashcard_list";
        }
    }


    @GetMapping("/admin/flashcards/add")
    public String showAddForm(Model model) {
        model.addAttribute("flashcard", new Flashcard());
        model.addAttribute("topics", topicQuizRepository.findAll());
        return "admin_flashcard_form";
    }

    @PostMapping("/admin/flashcards/add")
    public String addFlashcard(@ModelAttribute Flashcard flashcard,
                               @RequestParam Long topikId) {
        TopikQuiz topik = topicQuizRepository.findById(topikId).orElseThrow();
        flashcard.setTopikQuiz(topik);
        flashcardRepository.save(flashcard);
        return "redirect:/admin/flashcards?added=true"; // Tambahkan parameter untuk success message
    }

    @GetMapping("/admin/flashcards/delete")
    public String deleteFlashcard(@RequestParam Long id) {
        flashcardRepository.deleteById(id);
        return "redirect:/admin/flashcards?deleted=true"; // Tambahkan parameter untuk success message
    }

    @GetMapping("/admin/topics")
    public String listTopics(Model model) {
        List<TopikQuiz> topics = topicQuizRepository.findAll();
        model.addAttribute("topics", topics);
        return "admin_topic_list";
    }

    @GetMapping("/admin/topics/add")
    public String showAddTopicForm(Model model) {
        model.addAttribute("topic", new TopikQuiz());
        return "admin_topic_form";
    }

    @PostMapping("/admin/topics/add")
    public String saveTopic(@ModelAttribute TopikQuiz topic) {
        topicQuizRepository.save(topic);
        return "redirect:/admin/topics";
    }

    @GetMapping("/admin_dashboard")
    public String adminDashboard(@RequestParam(required = false) Integer userId, Model model) {
        // Use default userId if none provided
        if (userId == null) {
            userId = 1; // or get from session/authentication
        }

        User user = userRepository.findById(userId).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);

        return "admin_dashboard";
    }


    @GetMapping("/admin/kerjakan_quiz")
    public String showQuizTopics(@RequestParam(required = false) Long topikId,
                                 @RequestParam(required = false) Integer userId,
                                 Model model) {

        userId = 1;
        System.out.println("DEBUG - topikId: " + topikId);
        System.out.println("DEBUG - userId: " + userId);

        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow();
            model.addAttribute("user", user);
            System.out.println("DEBUG - User added to model: " + user.getId());
        } else {
            System.out.println("DEBUG - userId is null, user not added to model");
        }

        // Pastikan userId ada
        if (userId != null) {
            // Ambil user berdasarkan userId
            User user = userRepository.findById(userId).orElseThrow();

            // Menambahkan user ke model
            model.addAttribute("user", user);  // user ditambahkan ke model
        }

        // Jika topikId tidak ada, tampilkan daftar topik quiz
        if (topikId == null) {
            List<TopikQuiz> topics = topicQuizRepository.findAll();
            model.addAttribute("topics", topics);
            return "admin_kerjakan_quiz";  // Menampilkan form pemilihan topik
        } else {
            // Jika topikId ada, tampilkan soal berdasarkan topikId
            TopikQuiz topik = topicQuizRepository.findById(topikId).orElseThrow();
            List<Flashcard> flashcards = flashcardRepository.findByTopikQuiz_Id(topikId);

            model.addAttribute("topik", topik);
            model.addAttribute("flashcards", flashcards);
            return "admin_kerjakan_quiz";  // Menampilkan soal sesuai topik
        }
    }


    @PostMapping("/admin/kerjakan_quiz/{topikId}")
    public String saveQuizResults(@PathVariable Long topikId,
                                  @RequestParam Map<String, String> answers,
                                  @RequestParam Integer userId,
                                  Model model) {
        // Debug log
        System.out.println("Topik ID: " + topikId);
        System.out.println("User ID: " + userId);
        System.out.println("Answers: " + answers);

        List<Flashcard> flashcards = flashcardRepository.findByTopikQuiz_Id(topikId);
        int score = 0;

        User user = userRepository.findById(userId).orElseThrow();

        for (Flashcard flashcard : flashcards) {
            String studentAnswer = answers.get("answers[" + flashcard.getId() + "]");
            System.out.println("Question: " + flashcard.getQuestion());
            System.out.println("Answer: " + studentAnswer);
            boolean isCorrect = flashcard.getAnswer().equalsIgnoreCase(studentAnswer.trim());
            quizAnswerRepository.save(new QuizAnswer(flashcard, studentAnswer, isCorrect, user));
            if (isCorrect) score++;
        }

        model.addAttribute("score", score);
        model.addAttribute("topikId", topikId);
        return "admin_quiz_result";
    }

    // Rekap hasil kuis yang sudah dikerjakan
    @GetMapping("/admin/quiz_results")
    public String viewQuizResults(Model model) {
        List<QuizAnswer> results = quizAnswerRepository.findAll(); // Ambil semua hasil kuis
        model.addAttribute("results", results);
        return "admin_quiz_results"; // Template rekap hasil kuis
    }

    @GetMapping("/admin_quiz_statistic")
    public String viewQuizStatistics(Model model) {
        // Ambil semua jawaban kuis dari database
        List<QuizAnswer> allAnswers = quizAnswerRepository.findAll();

        // Menyaring hasil untuk hanya menampilkan jawaban siswa (student)
        List<QuizAnswer> studentAnswers = allAnswers.stream()
                .filter(result -> result.getUser().getRole().equals("student"))
                .collect(Collectors.toList());

        // Menghitung statistik global
        int totalAnswered = studentAnswers.size();
        int correctAnswers = (int) studentAnswers.stream().filter(QuizAnswer::getIsCorrect).count();
        double accuracy = (totalAnswered > 0) ? (correctAnswers / (double) totalAnswered) * 100 : 0;

        // Mengelompokkan jawaban per siswa dan per topik
        Map<String, List<QuizAnswer>> groupedByUserAndTopic = studentAnswers.stream()
                .collect(Collectors.groupingBy(answer ->
                        answer.getUser().getUsername() + "-" + answer.getFlashcard().getTopikQuiz().getName()
                ));

        // Membuat data ringkasan per siswa per topik
        List<Map<String, Object>> userTopicSummaries = groupedByUserAndTopic.entrySet().stream()
                .map(entry -> {
                    List<QuizAnswer> answers = entry.getValue();
                    QuizAnswer firstAnswer = answers.get(0);

                    int userCorrect = (int) answers.stream().filter(QuizAnswer::getIsCorrect).count();
                    double userAccuracy = (answers.size() > 0) ? (userCorrect / (double) answers.size()) * 100 : 0;

                    Map<String, Object> summary = new HashMap<>();
                    summary.put("user", firstAnswer.getUser());
                    summary.put("topic", firstAnswer.getFlashcard().getTopikQuiz());
                    summary.put("totalAnswers", answers.size());
                    summary.put("correctAnswers", userCorrect);
                    summary.put("accuracy", userAccuracy);

                    return summary;
                })
                .collect(Collectors.toList());

        // Menambahkan data ke model
        model.addAttribute("totalAnswered", totalAnswered);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("accuracy", accuracy);
        model.addAttribute("userTopicSummaries", userTopicSummaries);

        return "admin_quiz_statistic";
    }

    @GetMapping("/admin/quiz_results_detail")
    public String viewQuizResultsDetail(@RequestParam Integer userId,
                                        @RequestParam Long topikId,
                                        Model model) {
        // Ambil semua jawaban kuis berdasarkan userId
        List<QuizAnswer> results = quizAnswerRepository.findByUser_IdAndFlashcard_TopikQuiz_Id(userId, topikId);

        // Menyaring hasil untuk menampilkan jawaban per soal
        model.addAttribute("results", results);

        // Menghitung akurasi per siswa
        int correctAnswers = (int) results.stream().filter(QuizAnswer::getIsCorrect).count();
        int totalAnswers = results.size();
        double accuracy = (totalAnswers > 0) ? (correctAnswers / (double) totalAnswers) * 100 : 0;

        // Menambahkan statistik ke model
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("totalAnswers", totalAnswers);
        model.addAttribute("accuracy", accuracy);

        return "admin_quiz_results_detail";  // Template untuk menampilkan detail hasil kuis per siswa
    }




}
