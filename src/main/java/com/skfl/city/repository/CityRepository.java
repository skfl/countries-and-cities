package com.skfl.city.repository;

import com.skfl.city.entity.City;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @EntityGraph(attributePaths = {"country"})
    Page<City> findAll(@NotNull Pageable pageable);

    @EntityGraph(attributePaths = {"country"})
    Page<City> findAllByCountry_Name(String country, Pageable pageable);

    @EntityGraph(attributePaths = {"country"})
    Page<City> findAllByName(String city, Pageable pageable);

    @Query("SELECT DISTINCT c.name FROM City c")
    Page<String> findAllDistinctName(Pageable pageable);
}
