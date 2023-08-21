package com.skfl.city.dto.request;

import com.skfl.city.dto.common.Page;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityRequest {

    private Page page;

    private String countryName;

    private String cityName;

    private Boolean unique;
}
