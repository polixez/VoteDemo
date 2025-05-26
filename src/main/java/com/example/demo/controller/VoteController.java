package com.example.demo.controller;

import com.example.demo.repository.VoteRepository;
import com.example.demo.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
public class VoteController {

    private final VoteRepository voteRepository;

    private final List<String> options = Arrays.asList("Java", "Python", "C++");

    @Autowired
    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    // Обработка GET-запроса на домашнюю страницу
    @GetMapping("/home")
    public String showHomePage(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("options", options);
        model.addAttribute("voteCounts", getVoteCounts());
        return "home";
    }

    // Обработка POST-запроса из формы голосования
    @PostMapping("/home")
    public String handleVote(@RequestParam String option, Model model) {
        if (options.contains(option)) {
            voteRepository.save(new Vote(option));
            model.addAttribute("voteResult", "Спасибо за ваш голос за " + option + "!");
        } else {
            model.addAttribute("voteResult", "Некорректный выбор.");
        }
        model.addAttribute("options", options);
        model.addAttribute("voteCounts", getVoteCounts());
        return "home";
    }

    // Метод для подсчёта голосов
    private Map<String, Long> getVoteCounts() {
        Map<String, Long> voteCounts = new HashMap<>();
        for (String option : options) {
            voteCounts.put(option, voteRepository.countByOption(option));
        }
        return voteCounts;
    }
}
