package com.tubes.studypop.controller;

import com.tubes.studypop.model.*;
import com.tubes.studypop.repository.*;
import com.tubes.studypop.service.AuthService;
import com.tubes.studypop.service.Quiz;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class QuizController {

    @Autowired
    private TopicQuizRepository topicQuizRepository;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private Quiz quiz;

    @Autowired
     private QuizAnswerRepository quizAnswerRepository;

    @Autowired
     private UserRepository userRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private AuthService authService;

    @GetMapping("/choose_topic")
    public String chooseTopicPage(@RequestParam Integer userId, Model model) {
        User user = authService.findById(userId).orElseThrow();
        List<TopikQuiz> topics = topicQuizRepository.findAll();

        model.addAttribute("user", user);      // untuk tombol "Mulai Kuis"
        model.addAttribute("topics", topics);  // untuk list topik
        return "choose_topic";
    }


//    @GetMapping("/quiz_page")
//    public String quizPage(@RequestParam Integer userId,
//                           @RequestParam Long topikId,
//                           @RequestParam(defaultValue = "0") int questionIndex,
//                           Model model) {
//
//        System.out.println("User in model: " + model.getAttribute("user"));
//        System.out.println("Topic list: " + model.getAttribute("topics"));
//
//        List<Flashcard> flashcards = flashcardRepository.findByTopikQuiz_Id(topikId);
//
//        if (questionIndex >= flashcards.size()) {
//            // Jika semua soal selesai
//            return "redirect:/quiz_result?userId=" + userId + "&topikId=" + topikId;
//        }
//
//        Flashcard currentFlashcard = flashcards.get(questionIndex);
//
//        model.addAttribute("flashcard", currentFlashcard);
//        model.addAttribute("userId", userId);
//        model.addAttribute("topikId", topikId);
//        model.addAttribute("questionIndex", questionIndex);
//
//        return "quiz_page";
//    }
@GetMapping("/quiz_page")
public String quizPage(@RequestParam Integer userId,
                       @RequestParam Long topikId,
                       @RequestParam(defaultValue = "0") int questionIndex,
                       Model model) {

    System.out.println("User in model: " + model.getAttribute("user"));
    System.out.println("Topic list: " + model.getAttribute("topics"));

    List<Flashcard> flashcards = flashcardRepository.findByTopikQuiz_Id(topikId);

    if (questionIndex >= flashcards.size()) {
        // Jika semua soal selesai
        return "redirect:/quiz_result?userId=" + userId + "&topikId=" + topikId;
    }

    Flashcard currentFlashcard = flashcards.get(questionIndex);

    // Ambil nama topik dari flashcard atau langsung dari repository
    String topicName = currentFlashcard.getTopikQuiz().getName();
    // Atau alternatif:
    // TopikQuiz topik = topicQuizRepository.findById(topikId).orElseThrow();
    // String topicName = topik.getNama();

    model.addAttribute("flashcard", currentFlashcard);
    model.addAttribute("userId", userId);
    model.addAttribute("topikId", topikId);
    model.addAttribute("questionIndex", questionIndex);
    model.addAttribute("topicName", topicName); // Tambahkan ini

    return "quiz_page";
}
    @PostMapping("/submit-answer")
    public String submitAnswer(@RequestParam Long flashcardId,
                               @RequestParam String studentAnswer,
                               @RequestParam Integer userId,
                               @RequestParam Long topikId,
                               @RequestParam Integer questionIndex) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        QuizAnswer answer = new QuizAnswer();
        answer.setFlashcard(flashcard);
        answer.setUser(user);
        answer.setStudentAnswer(studentAnswer);
        answer.setIsCorrect(studentAnswer.trim().equalsIgnoreCase(flashcard.getAnswer().trim()));

        quizAnswerRepository.save(answer);

        return "redirect:/quiz_page?userId=" + userId + "&topikId=" + topikId + "&questionIndex=" + (questionIndex + 1);
    }


    @GetMapping("/quiz_result")
    public String quizResult(@RequestParam Integer userId,
                             @RequestParam Long topikId,
                             Model model) {
        List<QuizAnswer> allAnswers = quizAnswerRepository.findByUser_IdAndFlashcard_TopikQuiz_Id(userId, topikId);

        System.out.println("userId = " + userId);

        // Ambil jawaban terbaru untuk setiap flashcard
        List<QuizAnswer> latestAnswers = allAnswers.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getFlashcard().getId(),
                        Collectors.maxBy(Comparator.comparing(QuizAnswer::getAnsweredAt))
                ))
                .values().stream()
                .flatMap(Optional::stream)
                .toList();

        long score = latestAnswers.stream().filter(QuizAnswer::getIsCorrect).count();

        if (!latestAnswers.isEmpty()) {
            User user = latestAnswers.get(0).getUser();
            TopikQuiz topik = latestAnswers.get(0).getFlashcard().getTopikQuiz();

            QuizResult result = new QuizResult();
            result.setUser(user);
            result.setTopikQuiz(topik);
            result.setScore((int) score);
            result.setStartedAt(LocalDateTime.now().minusMinutes(5)); // dummy waktu
            result.setCompletedAt(LocalDateTime.now());

            quizResultRepository.save(result);
        }

        model.addAttribute("answers", latestAnswers);
        model.addAttribute("score", score);
        model.addAttribute("userId", userId);

        return "quiz_result";
    }




}
