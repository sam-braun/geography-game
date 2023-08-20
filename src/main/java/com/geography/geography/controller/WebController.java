package com.geography.geography.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.geography.geography.service.GameService;

@Controller
public class WebController {

    @Autowired
    private GameService gameService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/gameplay")
    public String gameplay(Model model) {
        model.addAttribute("computerCity", gameService.getInitialCity());
        model.addAttribute("usedCities", gameService.getUsedCities());
        return "gameplay";
    }

    @GetMapping("/goodbye")
    public String goodbye() {
        gameService.resetGame();
        return "goodbye";
    }
}
