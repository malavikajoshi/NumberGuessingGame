package com.uniquehire.numberguessinggame;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
public class GameController {

    private int secretNumber = new Random().nextInt(100) + 1;
    private int attempts = 0;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Guess a number between 1 and 100");
        model.addAttribute("attempts", attempts);
        return "index";
    }

    @PostMapping("/guess")
    public String guessNumber(@RequestParam int userGuess, Model model) {
        attempts++;

        String message;
        String status;

        if (userGuess < secretNumber) {
            message = "Too low! Try a bigger number.";
            status = "low";
        } else if (userGuess > secretNumber) {
            message = "Too high! Try a smaller number.";
            status = "high";
        } else {
            message = "Congratulations! You guessed it correctly!";
            status = "correct";
        }

        model.addAttribute("message", message);
        model.addAttribute("status", status);
        model.addAttribute("attempts", attempts);

        return "index";
    }

    @PostMapping("/reset")
    public String resetGame(Model model) {
        secretNumber = new Random().nextInt(100) + 1;
        attempts = 0;

        model.addAttribute("message", "New game started! Guess a number between 1 and 100");
        model.addAttribute("attempts", attempts);

        return "index";
    }
}