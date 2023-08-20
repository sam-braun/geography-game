package com.geography.geography.service;

import com.geography.geography.model.City;
import com.geography.geography.repository.CityRepo;
import com.geography.geography.repository.UsedCityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class GameService {

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private UsedCityRepo usedCityRepo;

    private Random random = new Random();

    public City getInitialCity() {
        // Retrieve all cities from database
        ArrayList<City> allCities = new ArrayList<>(cityRepo.findAll());

        // Randomly select a city
        City initialCity = allCities.get(random.nextInt(allCities.size()));

        // Add the selected city to the UsedCityRepo
        usedCityRepo.save(initialCity);

        return initialCity;
    }

    public City getNextCity(String userCityName) {
        // Retrieve the city entered by user from the CityRepo
        City userCity = cityRepo.findByName(userCityName).orElse(null);
        if (userCity == null) {
            throw new IllegalArgumentException("The city " + userCityName + " was not found in the database.");
        }

        // Check if the city entered by user was already used
        if (usedCityRepo.findByName(userCityName).isPresent()) {
            throw new IllegalArgumentException("The city " + userCityName + " was already used.");
        }

        // Retrieve all cities starting with the last letter of the user entered city
        String lastChar = String.valueOf(userCityName.charAt(userCityName.length() - 1));
        ArrayList<City> potentialCities = cityRepo.findByNameStartingWith(lastChar);

        // Filter out cities that were already used
        potentialCities.removeIf(city -> usedCityRepo.existsById(city.getCityId()));

        if (potentialCities.isEmpty()) {
            throw new IllegalStateException("No available cities starting with the letter " + lastChar);
        }

        // Randomly select a city from the potential cities list
        City nextCity = potentialCities.get(random.nextInt(potentialCities.size()));

        // Add the selected city to the UsedCityRepo
        usedCityRepo.save(nextCity);

        return nextCity;
    }

    public ArrayList<City> getUsedCities() {
        return new ArrayList<>(usedCityRepo.findAll());
    }
    
    public City getCurrentCity() {
        return getUsedCities().get(getUsedCities().size() - 1);
    }
    

    public void resetGame() {
        usedCityRepo.deleteAll();
    }
}
