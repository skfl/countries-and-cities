package com.skfl.city.mapper;

import com.skfl.city.dto.CountryCityDto;
import com.skfl.city.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryCityMapper {

    CountryCityMapper INSTANCE = Mappers.getMapper(CountryCityMapper.class);

    CountryCityDto cityToCountryCity(City city);
}
