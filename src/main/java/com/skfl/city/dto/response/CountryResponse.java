package com.skfl.city.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skfl.city.dto.CityDto;
import com.skfl.city.dto.CountryDto;
import com.skfl.city.dto.common.Page;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryResponse {

    private Page page;

    private List<CountryDto> countries;
}
