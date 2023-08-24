package com.skfl.city.controller;

import com.skfl.city.dto.response.ApplicationErrorResponse;
import com.skfl.city.dto.response.CountryResponse;
import com.skfl.city.services.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.skfl.city.config.swagger.constant.ControllerResponseCode.*;
import static com.skfl.city.config.swagger.constant.ControllerResponseStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    @Tag(name = "Get country info")
    @Operation(summary = "Returns paginated list all countries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = STATUS_CODE_SUCCESS, content = @Content(schema = @Schema(implementation = CountryResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = STATUS_CODE_BAD_REQUEST, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED, description = STATUS_CODE_UNAUTHORIZED, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = STATUS_CODE_NOT_FOUND, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER_ERROR, description = STATUS_CODE_INTERNAL_SERVER_ERROR, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class)))
    })
    @GetMapping("")
    public ResponseEntity<CountryResponse> getAllCountries(@RequestParam(defaultValue = "3") int size,
                                                           @RequestParam(defaultValue = "0") int page) {
        var pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                countryService.getAllCountries(pageable)
        );
    }
}
