package com.tubes.studypop.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private TopikQuiz topikQuiz;

    private Integer score;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    // GETTER
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public TopikQuiz getTopikQuiz() {
        return topikQuiz;
    }

    public Integer getScore() {
        return score;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    // SETTER
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTopikQuiz(TopikQuiz topikQuiz) {
        this.topikQuiz = topikQuiz;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
