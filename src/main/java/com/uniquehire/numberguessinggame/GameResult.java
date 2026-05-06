package com.uniquehire.numberguessinggame;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int secretNumber;
    private int attempts;
    private String result;
    private LocalDateTime playedAt;

    public GameResult() {
    }

    public GameResult(int secretNumber, int attempts, String result) {
        this.secretNumber = secretNumber;
        this.attempts = attempts;
        this.result = result;
        this.playedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public int getAttempts() {
        return attempts;
    }

    public String getResult() {
        return result;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }
}