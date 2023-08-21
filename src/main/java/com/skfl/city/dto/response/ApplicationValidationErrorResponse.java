package com.skfl.city.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ApplicationValidationErrorResponse {

    private int statusCode;

    private String message;

    private String errorMessage;

    @JsonFormat(pattern = "yyyy HH:mm:ss ")
    private LocalDateTime timestamp;

    private Map<String, String> violations;
}
