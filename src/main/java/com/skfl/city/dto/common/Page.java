package com.skfl.city.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page {

    private Integer totalPages;

    @Positive
    private Integer size;

    @PositiveOrZero
    private Integer number;
}
