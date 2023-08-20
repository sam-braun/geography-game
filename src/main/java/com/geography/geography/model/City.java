package com.geography.geography.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String asciiName;
    private double[] coordinates = new double[2];
    private String country;
    private boolean isNationalCapital;
    private int population;
    private int cityId;

    // Constructors
    public City() {}

    public City(String name, String asciiName, double[] coordinates, String country, boolean isNationalCapital, int population, int cityId) {
        this.name = name;
        this.asciiName = asciiName;
        this.coordinates = coordinates;
        this.country = country;
        this.isNationalCapital = isNationalCapital;
        this.population = population;
        this.cityId = cityId;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAsciiName() {
        return asciiName;
    }

    public String getCountry() {
        return country;
    }

    public boolean isNationalCapital() {
        return isNationalCapital;
    }

    public int getPopulation() {
        return population;
    }

    public int getCityId() {
        return cityId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAsciiName(String asciiName) {
        this.asciiName = asciiName;
    }

    public void setCoordinates(double lat, double lon) {
        this.coordinates = new double[]{lat, lon};
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setNationalCapital(boolean cap) {
        this.isNationalCapital = cap;
    }

    public void setPopulation(int pop) {
        this.population = pop;
    }

    public void setCityId(int cid) {
        this.cityId = cid;
    }

    

    @Override
    public String toString() {
        return "City{" +
               "name='" + name + '\'' +
               ", asciiName='" + asciiName + '\'' +
               ", coordinates=[" + coordinates[0] + ", " + coordinates[1] + "]" +
               ", country='" + country + '\'' +
               ", isNationalCapital=" + isNationalCapital +
               ", population=" + population +
               ", id=" + id +
               '}';
    }
}
