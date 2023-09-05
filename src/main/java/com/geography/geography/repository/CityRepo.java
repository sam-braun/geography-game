package com.geography.geography.repository;

import com.geography.geography.model.City;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CityRepo extends JpaRepository<City, Integer> {
    Optional<City> findByName(String name);

    ArrayList<City> findByNameStartingWith(String prefix);
}
