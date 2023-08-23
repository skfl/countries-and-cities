package com.skfl.city.services.impl;

import com.skfl.city.dto.common.Page;
import com.skfl.city.dto.response.CountryResponse;
import com.skfl.city.mapper.CountryMapper;
import com.skfl.city.repository.CountryRepository;
import com.skfl.city.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public CountryResponse getAllCountries(Pageable pageable) {
        var countries = countryRepository.findAll(pageable);
        return CountryResponse.builder()
                .page(Page.builder()
                        .number(countries.getNumber())
                        .size(countries.getSize())
                        .totalPages(countries.getTotalPages())
                        .build())
                .countries(countries.get()
                        .map(CountryMapper.INSTANCE::toDto)
                        .toList())
                .build();
    }
}
