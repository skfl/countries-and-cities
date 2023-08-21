package com.skfl.city.services;

import com.skfl.city.dto.response.CityResponse;
import org.springframework.data.domain.Pageable;

public interface CountryService {

    CityResponse getAllUniqueCountriesByName(Pageable pageable);
}
