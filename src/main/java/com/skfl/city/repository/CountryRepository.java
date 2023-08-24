package com.skfl.city.repository;

import com.skfl.city.entity.Country;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    @EntityGraph(attributePaths = {"cities"})
    Page<Country> findAll(@NotNull Pageable pageable);
}
