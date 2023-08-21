package com.skfl.city.dto.response;

import com.skfl.city.dto.common.Page;
import com.skfl.city.dto.CityDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CityResponse {

    private Page page;

    private List<CityDto> cities;
}
