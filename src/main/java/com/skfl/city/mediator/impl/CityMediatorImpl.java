package com.skfl.city.mediator.impl;

import com.skfl.city.dto.CityDto;
import com.skfl.city.dto.response.CityNameResponse;
import com.skfl.city.dto.response.CityResponse;
import com.skfl.city.mediator.CityMediator;
import com.skfl.city.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CityMediatorImpl implements CityMediator {

    private final CityService cityService;

    @Override
    public CityNameResponse getAllUniqueCityName(Pageable pageable) {
        return cityService.getAllUniqueCityName(pageable);
    }

    @Override
    public CityResponse getAllCities(String countryName, String cityName, Pageable pageable) {
        if (countryName != null) {
            return cityService.getAllCitiesByCountryName(countryName, pageable);
        }
        if (cityName != null) {
            return cityService.getAllCitiesByCityName(cityName, pageable);
        }
        return cityService.getAllCities(pageable);
    }

    @Override
    public CityDto editCityNameByCityId(CityDto cityDto, Long id) {
        return cityService.editCityNameByCityId(id, cityDto);
    }

    @Override
    public CityDto uploadCityLogo(MultipartFile file, Long id) throws Exception {
        return cityService.editCityLogoByCityId(id, file);
    }
}
