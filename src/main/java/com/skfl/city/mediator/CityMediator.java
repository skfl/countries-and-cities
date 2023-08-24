package com.skfl.city.mediator;

import com.skfl.city.dto.CityDto;
import com.skfl.city.dto.response.CityNameResponse;
import com.skfl.city.dto.response.CityResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CityMediator {

    CityNameResponse getAllUniqueCityName(Pageable pageable);

    CityResponse getAllCities(String countryName,String cityName,Pageable pageable);

    CityDto editCityNameByCityId(CityDto cityDto,Long id);

    CityDto uploadCityLogo(MultipartFile file,Long id) throws Exception;
}
