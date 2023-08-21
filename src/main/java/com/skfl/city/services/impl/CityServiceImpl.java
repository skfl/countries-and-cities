package com.skfl.city.services.impl;

import com.skfl.city.dto.CityDto;
import com.skfl.city.dto.common.Page;
import com.skfl.city.dto.response.CityNameResponse;
import com.skfl.city.dto.response.CityResponse;
import com.skfl.city.exception.constant.ExceptionMessage;
import com.skfl.city.mapper.CityMapper;
import com.skfl.city.repository.CityRepository;
import com.skfl.city.services.CityService;
import com.skfl.city.services.LogoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final LogoService logoService;

    @Override
    public CityResponse getAllCitiesByCountryName(String countryName, Pageable pageable) {
        var cities = cityRepository.findAllByCountry_Name(countryName, pageable);
        return CityResponse.builder()
                .page(Page.builder()
                        .totalPages(cities.getTotalPages())
                        .number(cities.getNumber())
                        .size(cities.getSize())
                        .build())
                .cities(cities.get()
                        .map(CityMapper.INSTANCE::toDto)
                        .toList())
                .build();
    }

    @Override
    public CityResponse getAllCitiesByCityName(String city, Pageable pageable) {
        var cities = cityRepository.findAllByName(city, pageable);
        return CityResponse.builder()
                .page(Page.builder()
                        .size(cities.getSize())
                        .totalPages(cities.getTotalPages())
                        .number(cities.getNumber())
                        .build())
                .cities(cities.get()
                        .map(CityMapper.INSTANCE::toDto)
                        .toList())
                .build();
    }

    @Override
    public CityResponse getAllCities(Pageable pageable) {
        var cities = cityRepository.findAll(pageable);
        return CityResponse.builder()
                .page(Page.builder()
                        .totalPages(cities.getTotalPages())
                        .number(cities.getNumber())
                        .size(cities.getSize())
                        .build())
                .cities(cities.get()
                        .map(CityMapper.INSTANCE::toDto)
                        .toList())
                .build();
    }

    @Override
    public CityDto editCityNameByCityId(Long id, CityDto dto) {
        var foundCity = cityRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(ExceptionMessage.CITY_NOT_FOUND_MESSAGE.getValue()));
        foundCity.setName(dto.getName());
        return CityMapper.INSTANCE.toDto(cityRepository.save(foundCity));
    }

    @Override
    public CityDto editCityLogoByCityId(Long id, MultipartFile multipartFile) throws Exception {
        var foundCity = cityRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(ExceptionMessage.CITY_NOT_FOUND_MESSAGE.getValue()));
        var previousLogo = foundCity.getLogo();
        foundCity.setLogo(logoService.loadLogoFile(multipartFile));
        if (previousLogo != null) {
            logoService.deleteLogoFile(previousLogo);
        }
        return CityMapper.INSTANCE.toDto(cityRepository.save(foundCity));
    }

    @Override
    public CityNameResponse getAllUniqueCityName(Pageable pageable) {
        var names = cityRepository.findAllDistinctName(pageable);
        return CityNameResponse.builder()
                .page(Page.builder()
                        .totalPages(names.getTotalPages())
                        .number(names.getNumber())
                        .size(names.getSize())
                        .build())
                .names(names.get().toList())
                .build();
    }
}
