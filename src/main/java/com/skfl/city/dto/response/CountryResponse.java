package com.skfl.city.dto.response;

import com.skfl.city.dto.CityDto;
import com.skfl.city.dto.CountryDto;
import com.skfl.city.dto.common.Page;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CountryResponse {

    private Page page;

    private List<CountryDto> countries;
}
