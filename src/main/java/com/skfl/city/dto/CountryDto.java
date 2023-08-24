package com.skfl.city.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CountryDto {

    private String name;

    private List<CountryCityDto> cities;
}
