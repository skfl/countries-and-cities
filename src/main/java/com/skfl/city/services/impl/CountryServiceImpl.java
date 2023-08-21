package com.skfl.city.services.impl;

import com.skfl.city.dto.response.CityResponse;
import com.skfl.city.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    @Override
    public CityResponse getAllUniqueCountriesByName(Pageable pageable) {
        return null;
    }
}
