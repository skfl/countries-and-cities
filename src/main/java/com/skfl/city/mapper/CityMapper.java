package com.skfl.city.mapper;

import com.skfl.city.dto.CityDto;
import com.skfl.city.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(source = "city.country.name",target = "countryName")
    CityDto toDto(City city);

    City toEntity(CityDto dto);
}
