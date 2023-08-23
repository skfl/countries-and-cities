package com.skfl.city.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class CityDto {

    @NotBlank
    @Pattern(regexp = "^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$")
    @Length(max = 255)
    @Schema(example = "Istanbul")
    private String name;

    @Pattern(regexp = "^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$")
    @Length(max = 255)
    @Schema(example = "Turkey")
    private String countryName;

    @Schema(example = "uuid-logo-name-with-extension.jpg")
    @Length(max = 255)
    private String logo;
}
