package com.skfl.city.dto.response;

import com.skfl.city.dto.common.Page;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CityNameResponse {

    private Page page;

    private List<String> names;
}
