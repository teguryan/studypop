//package com.tubes.studypop.controller;
//
//import com.tubes.studypop.model.TopikQuiz;
//import com.tubes.studypop.model.Flashcard;
//import com.tubes.studypop.repository.FlashcardRepository;
//import com.tubes.studypop.repository.TopicQuizRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class FlashcardController {
//
//    @Autowired
//    private TopicQuizRepository topicQuizRepository;
//
//    @Autowired
//    private FlashcardRepository flashcardRepository;
//
//    @GetMapping("/choose_flashcard")
//    public String showFlashcards(Model model) {
//        // Ambil topik kuis dengan id = 1 (Matematika)
//        TopikQuiz topicQuiz = topicQuizRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Invalid topic ID"));
//
//        // Ambil flashcards yang terkait dengan topik kuis id = 1
//        List<Flashcard> flashcards = flashcardRepository.findByTopikQuizId(topicQuiz.getId());
//
//        // Menambahkan data ke model agar bisa ditampilkan di view
//        model.addAttribute("topicQuiz", topicQuiz);
//        model.addAttribute("flashcards", flashcards);
//        model.addAttribute("currentCardIndex", 0); // Default index
//
//
//
//        return "choose_flashcard"; // Nama template HTML yang akan dirender
//    }
//}

package com.tubes.studypop.controller;

import com.tubes.studypop.model.Flashcard;
import com.tubes.studypop.model.TopikQuiz;
import com.tubes.studypop.service.Quiz;
import com.tubes.studypop.repository.FlashcardRepository;
import com.tubes.studypop.repository.TopicQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FlashcardController {

    @Autowired
    private TopicQuizRepository topicQuizRepository;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @GetMapping("/choose_flashcard")
    public String chooseFlashcard(Model model) {
        // Mengambil semua topik quiz dari database
        List<TopikQuiz> topics = topicQuizRepository.findAll();
        // Menambahkan topik ke model agar bisa ditampilkan
        model.addAttribute("topik_quiz", topics);
        return "choose_flashcard";  // Mengembalikan ke halaman memilih topik
    }

//    @GetMapping("/flashcard_page")
//    public String flashcard(@RequestParam("topicId") Long topicId, Model model) {
//        // Ambil flashcards berdasarkan topicId
//        List<Flashcard> flashcards = flashcardRepository.findByTopikQuizId(topicId);
//        model.addAttribute("flashcards", flashcards);
//        return "flashcard_page";
//    }

    @GetMapping("/flashcard_page")
    public String flashcard(@RequestParam("topicId") Long topicId,
                            @RequestParam(value = "index", defaultValue = "0") int index,
                            Model model) {
        List<Flashcard> flashcards = flashcardRepository.findByTopikQuizId(topicId);

        if (flashcards.isEmpty()) {
            model.addAttribute("error", "Tidak ada flashcard untuk topik ini.");
            return "flashcard_page"; // atau redirect ke halaman lain jika perlu
        }

        if (index < 0) index = 0;
        if (index >= flashcards.size()) index = flashcards.size() - 1;

        Flashcard currentFlashcard = flashcards.get(index);

        model.addAttribute("flashcard", currentFlashcard);
        model.addAttribute("index", index);
        model.addAttribute("maxIndex", flashcards.size() - 1);
        model.addAttribute("topicId", topicId);

        return "flashcard_page";
    }


}
