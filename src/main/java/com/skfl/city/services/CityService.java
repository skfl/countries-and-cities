package com.skfl.city.services;

import com.skfl.city.dto.CityDto;
import com.skfl.city.dto.response.CityNameResponse;
import com.skfl.city.dto.response.CityResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CityService {

    CityResponse getAllCitiesByCountryName(String country, Pageable pageable);

    CityResponse getAllCitiesByCityName(String city, Pageable pageable);

    CityResponse getAllCities(Pageable pageable);

    CityDto editCityNameByCityId(Long id, CityDto dto);

    CityDto editCityLogoByCityId(Long id, MultipartFile multipartFile) throws Exception;

    CityNameResponse getAllUniqueCityName(Pageable pageable);
}
