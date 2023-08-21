package com.skfl.city.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ApplicationErrorResponse {

    private int statusCode;

    private String message;

    private String errorMessage;

    @JsonFormat(pattern = "yyyy HH:mm:ss ")
    private LocalDateTime timestamp;
}
