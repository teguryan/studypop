package com.tubes.studypop.controller;

import com.tubes.studypop.model.QuizResult;
import com.tubes.studypop.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StatistikController {

    @Autowired
    private QuizResultRepository quizResultRepository;

    @GetMapping("/statistik")
    public String statistikPage(@RequestParam Integer userId, Model model) {
        List<QuizResult> results = quizResultRepository.findByUser_Id(userId);

        model.addAttribute("results", results);
        model.addAttribute("userId", userId); // Wajib

        return "statistik";
    }
}
