package com.tubes.studypop.model;

import jakarta.persistence.*;

@Entity
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id soal (flashcard)
    private String option1;   // Opsi 1
    private String option2;   // Opsi 2
    private String option3;   // Opsi 3
    private String option4;   // Opsi 4
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

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

}
