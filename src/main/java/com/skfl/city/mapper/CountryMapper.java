package com.skfl.city.mapper;

import com.skfl.city.dto.CityDto;
import com.skfl.city.dto.CountryCityDto;
import com.skfl.city.dto.CountryDto;
import com.skfl.city.entity.City;
import com.skfl.city.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    CountryDto toDto(Country country);

    default CountryCityDto cityToCountryCityDto(City city){
        return Mappers.getMapper(CountryCityMapper.class).cityToCountryCity(city);
    }
}
