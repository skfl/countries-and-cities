package com.skfl.city.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryCityDto {

    @Schema(example = "Istanbul")
    private String name;

    @Schema(example = "uuid-logo-name-with-extension.jpg")
    private String logo;
}
