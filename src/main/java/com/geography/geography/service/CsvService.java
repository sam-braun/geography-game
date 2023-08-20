package com.geography.geography.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.geography.geography.model.City;
import com.geography.geography.repository.CityRepo;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import org.springframework.transaction.annotation.Transactional;


@Service
public class CsvService {


    private static final String CSV_FILE_PATH = "/Users/samuelbraun/Desktop/geography_git3/geography/src/main/resources/data/worldcities.csv";
    private final CityRepo cityRepo;

    @Autowired
    public CsvService(CityRepo cityRepo) {
        this.cityRepo = cityRepo;


    }

    public ArrayList<City> loadCitiesFromCsv() {
        
        
        ArrayList<City> cities = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(CSV_FILE_PATH).getInputStream()))) {
            // Skip header
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
        
                City city=new City();
                city.setName(fields[0].trim());
                city.setAsciiName(fields[1].trim());
                city.setCoordinates(Double.parseDouble(fields[2].trim()), Double.parseDouble(fields[3].trim()));
                city.setCountry(fields[4].trim());
                
                if (fields[8].trim().equals("primary")) {
                    city.setNationalCapital(true);
                }
                
                else {
                    city.setNationalCapital(false);
                }

                city.setPopulation(Integer.parseInt(fields[9].trim()));
                city.setCityId(Integer.parseInt(fields[10].trim()));
                if (validateCity(city)) {
                    cities.add(city);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load cities from CSV.", e);
        }

        return cities;
    }

    @Transactional
    public void loadCitiesFromCsvToDatabase() {
        List<City> cities = loadCitiesFromCsv();
        cityRepo.saveAll(cities);
    }

    private boolean validateCity(City city) {
        if (city.getName() == null || city.getName().isEmpty()) {
            return false;
        }
        return true;
    }
 
    



    
}
