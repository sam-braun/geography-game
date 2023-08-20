package com.geography.geography.controller;

import com.geography.geography.model.City;
import com.geography.geography.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/start")
    public String startGame(HttpSession session) {
        City initialCity = gameService.getInitialCity();
        session.setAttribute("computerCity", initialCity);
        return "gameplay";
    }

    @PostMapping("/play")
    public String play(@RequestParam String userCity, HttpSession session) {
        try {
            City nextCity = gameService.getNextCity(userCity);
            session.setAttribute("computerCity", nextCity);
            session.setAttribute("usedCities", gameService.getUsedCities()); // Assuming you've a method getUsedCities() in GameService
            session.removeAttribute("errorMessage");  // Clear any previous error messages
            return "gameplay";
        } catch (IllegalArgumentException | IllegalStateException ex) {
            session.setAttribute("errorMessage", ex.getMessage());
            return "gameplay";
        }
    }

    @GetMapping("/reset")
    public String resetGame(HttpSession session) {
        gameService.resetGame();
        session.removeAttribute("computerCity");
        session.removeAttribute("usedCities");
        session.removeAttribute("errorMessage");
        return "home";  // Redirect to the home page or wherever you'd like
    }
}
