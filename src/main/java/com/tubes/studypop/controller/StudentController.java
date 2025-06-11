package com.tubes.studypop.controller;

import com.tubes.studypop.model.Flashcard;
import com.tubes.studypop.model.TopikQuiz;
import com.tubes.studypop.model.User;
import com.tubes.studypop.repository.TopicQuizRepository;
import com.tubes.studypop.repository.FlashcardRepository;
import com.tubes.studypop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TopicQuizRepository topicQuizRepository;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @GetMapping("/student_dashboard")
    public String studentDashboard(@RequestParam Integer userId, Model model) {
        User user = authService.findById(userId).orElseThrow();
        model.addAttribute("user", user);
        return "student_dashboard";
    }

    @GetMapping("/flashcards")
    public String flashcardPage(@RequestParam Integer userId,
                                @RequestParam Long topikId,
                                Model model) {

        System.out.println("userId = " + userId);

        TopikQuiz topik = topicQuizRepository.findById(topikId).orElseThrow();
        List<Flashcard> flashcards = flashcardRepository.findByTopikQuiz_Id(topikId);

        model.addAttribute("flashcards", flashcards);
        model.addAttribute("topik", topik);
        model.addAttribute("userId", userId); // Wajib

        return "flashcard_list";
    }

    @GetMapping("/flashcard_topics")
    public String flashcardTopics(@RequestParam Integer userId, Model model) {
        List<TopikQuiz> topics = topicQuizRepository.findAll();
        model.addAttribute("topics", topics);
        model.addAttribute("userId", userId);
        return "flashcard_topic_list";
    }
}
