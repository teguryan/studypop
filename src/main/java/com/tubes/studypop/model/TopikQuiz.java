package com.tubes.studypop.model;

import jakarta.persistence.*;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "topik_quiz")
public class TopikQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;  // Nama topik

    @OneToMany(mappedBy = "topikQuiz", cascade = CascadeType.ALL)
    private List<Flashcard> flashcards;  // Kumpulan soal dalam topik quiz

    // Constructor, getter, setter
//    public TopikQuiz() {
//
//    }
//
//    public TopikQuiz(String name) {
//        this.name = name;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
}
