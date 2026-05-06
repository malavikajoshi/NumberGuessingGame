package com.uniquehire.numberguessinggame;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
public class GameController {

    private int secretNumber = new Random().nextInt(100) + 1;
    private int attempts = 0;

    private final GameResultRepository gameResultRepository;
    private final S3LogService s3LogService;

    public GameController(GameResultRepository gameResultRepository, S3LogService s3LogService) {
        this.gameResultRepository = gameResultRepository;
        this.s3LogService = s3LogService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<GameResult> history = gameResultRepository.findAll();

        model.addAttribute("message", "Guess a number between 1 and 100");
        model.addAttribute("attempts", attempts);
        model.addAttribute("history", history);

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

            GameResult gameResult = new GameResult(secretNumber, attempts, "Won");
            gameResultRepository.save(gameResult);

            String log = "Result: Won\n"
                    + "Secret Number: " + secretNumber + "\n"
                    + "Attempts: " + attempts + "\n";

            s3LogService.uploadGameLog(log);

            secretNumber = new Random().nextInt(100) + 1;
            attempts = 0;
        }

        List<GameResult> history = gameResultRepository.findAll();

        model.addAttribute("message", message);
        model.addAttribute("status", status);
        model.addAttribute("attempts", attempts);
        model.addAttribute("history", history);

        return "index";
    }

    @PostMapping("/reset")
    public String resetGame(Model model) {
        String log = "Game reset before winning\n"
                + "Secret Number was: " + secretNumber + "\n"
                + "Attempts before reset: " + attempts + "\n";

        s3LogService.uploadGameLog(log);

        secretNumber = new Random().nextInt(100) + 1;
        attempts = 0;

        List<GameResult> history = gameResultRepository.findAll();

        model.addAttribute("message", "New game started! Guess a number between 1 and 100");
        model.addAttribute("attempts", attempts);
        model.addAttribute("history", history);

        return "index";
    }
}