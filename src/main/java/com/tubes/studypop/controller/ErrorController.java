package com.tubes.studypop.controller;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@Controller
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("/error_page")
    public String handleError(Model model) {
        logger.error("Terjadi error dan diarahkan ke /error_page");
        model.addAttribute("errorMessage", "Terjadi masalah di aplikasi!");
        return "error_page";
    }
}





