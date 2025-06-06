package com.tubes.studypop.model;

import jakarta.persistence.*;

@Entity
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id soal (flashcard)

    private String question;  // Soal pertanyaan
    private String answer;    // Jawaban yang benar

    @ManyToOne
    @JoinColumn(name = "topik_quiz_id") // nyimpan relasi dr topikquiz
    private TopikQuiz topikQuiz;  // Setiap soal terkait dengan suatu topik

    // Constructor, getter, setter
    public Flashcard() {}

    public Flashcard(String question, String answer, TopikQuiz topikQuiz) {
        this.question = question;
        this.answer = answer;
        this.topikQuiz = topikQuiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public TopikQuiz getTopikQuiz() {
        return topikQuiz;
    }

    public void setTopikQuiz(TopikQuiz topikQuiz) {
        this.topikQuiz = topikQuiz;
    }
}
