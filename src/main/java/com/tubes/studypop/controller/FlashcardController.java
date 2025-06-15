package com.tubes.studypop.controller;

import com.tubes.studypop.model.*;
import com.tubes.studypop.repository.*;
import com.tubes.studypop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FlashcardController {

    @Autowired
    private TopicQuizRepository topicQuizRepository;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @GetMapping("/flashcard_topics")
    public String flashcardTopicsPage(@RequestParam Integer userId, Model model) {
        User user = authService.findById(userId).orElseThrow();
        List<TopikQuiz> topics = topicQuizRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("topics", topics);
        model.addAttribute("userId", userId);
        return "flashcard_topic_list";
    }

    @GetMapping("/flashcard_list")
    public String flashcardListPage(@RequestParam Integer userId,
                                    @RequestParam Long topikId,
                                    Model model) {

        // Ambil user
        User user = authService.findById(userId).orElseThrow();

        // Ambil topik
        TopikQuiz topik = topicQuizRepository.findById(topikId)
                .orElseThrow(() -> new RuntimeException("Topik tidak ditemukan"));

        // Ambil semua flashcard untuk topik tersebut
        List<Flashcard> flashcards = flashcardRepository.findByTopikQuiz_Id(topikId);

        // Debug log
        System.out.println("User ID: " + userId);
        System.out.println("Topic ID: " + topikId);
        System.out.println("Topic Name: " + topik.getName());
        System.out.println("Flashcards count: " + flashcards.size());

        model.addAttribute("user", user);
        model.addAttribute("topik", topik);
        model.addAttribute("flashcards", flashcards);
        model.addAttribute("userId", userId);

        return "flashcard_list";
    }
}