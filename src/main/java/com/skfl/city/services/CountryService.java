package com.skfl.city.services;

import com.skfl.city.dto.response.CountryResponse;
import org.springframework.data.domain.Pageable;

public interface CountryService {

    CountryResponse getAllCountries(Pageable pageable);
}
