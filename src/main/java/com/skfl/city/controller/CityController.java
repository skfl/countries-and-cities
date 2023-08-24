package com.skfl.city.controller;

import com.skfl.city.dto.CityDto;
import com.skfl.city.dto.response.ApplicationErrorResponse;
import com.skfl.city.dto.response.CityNameResponse;
import com.skfl.city.dto.response.CityResponse;
import com.skfl.city.services.LogoService;
import com.skfl.city.mediator.CityMediator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.skfl.city.config.swagger.constant.ControllerResponseCode.*;
import static com.skfl.city.config.swagger.constant.ControllerResponseStatus.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private final CityMediator cityMediator;

    private final LogoService logoService;

    @Tag(name = "Get city info")
    @Operation(summary = "Returns paginated list of unique city names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = STATUS_CODE_SUCCESS, content = @Content(schema = @Schema(implementation = CityNameResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = STATUS_CODE_BAD_REQUEST, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED, description = STATUS_CODE_UNAUTHORIZED, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = STATUS_CODE_NOT_FOUND, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER_ERROR, description = STATUS_CODE_INTERNAL_SERVER_ERROR, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class)))
    })
    @GetMapping("/names/unique")
    public ResponseEntity<CityNameResponse> getAllUniqueCityName(@RequestParam(defaultValue = "3") int size,
                                                                 @RequestParam(defaultValue = "0") int page) {
        var pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                cityMediator.getAllUniqueCityName(pageable)
        );
    }

    @Tag(name = "Get city info")
    @Operation(summary = "Returns paginated list of all cities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = STATUS_CODE_SUCCESS, content = @Content(schema = @Schema(implementation = CityResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = STATUS_CODE_BAD_REQUEST, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED, description = STATUS_CODE_UNAUTHORIZED, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = STATUS_CODE_NOT_FOUND, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER_ERROR, description = STATUS_CODE_INTERNAL_SERVER_ERROR, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class)))
    })
    @GetMapping("")
    public ResponseEntity<CityResponse> getAllCities(@RequestParam(defaultValue = "3") int size,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(required = false) String countryName,
                                                     @RequestParam(required = false) String cityName) {
        if (countryName != null && cityName != null) {
            throw new IllegalArgumentException();
        }
        var pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                cityMediator.getAllCities(countryName, cityName, pageable)
        );
    }

    @Tag(name = "Edit city info")
    @Operation(summary = "Edit city name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = STATUS_CODE_SUCCESS, content = @Content(schema = @Schema(implementation = CityDto.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = STATUS_CODE_BAD_REQUEST, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED, description = STATUS_CODE_UNAUTHORIZED, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = STATUS_CODE_NOT_FOUND, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER_ERROR, description = STATUS_CODE_INTERNAL_SERVER_ERROR, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class)))
    })
    @PutMapping("/name/{id}")
    public ResponseEntity<CityDto> editCityNameByCityId(@RequestBody @Valid CityDto cityDto,
                                                        @Valid @PositiveOrZero @PathVariable Long id) {
        return ResponseEntity.ok(
                cityMediator.editCityNameByCityId(cityDto, id)
        );
    }

    @Tag(name = "Edit city info")
    @Operation(summary = "Upload new city logo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = STATUS_CODE_SUCCESS, content = @Content(schema = @Schema(implementation = CityDto.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = STATUS_CODE_BAD_REQUEST, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED, description = STATUS_CODE_UNAUTHORIZED, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = STATUS_CODE_NOT_FOUND, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER_ERROR, description = STATUS_CODE_INTERNAL_SERVER_ERROR, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class)))
    })
    @PostMapping("/{id}/logo")
    public ResponseEntity<CityDto> uploadCityLogo(@RequestParam("file") MultipartFile file,
                                                  @PositiveOrZero @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(
                cityMediator.uploadCityLogo(file, id)
        );
    }

    @Tag(name = "Get city info")
    @Operation(summary = "Returns city logo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = STATUS_CODE_SUCCESS),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = STATUS_CODE_BAD_REQUEST, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED, description = STATUS_CODE_UNAUTHORIZED, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = STATUS_CODE_NOT_FOUND, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER_ERROR, description = STATUS_CODE_INTERNAL_SERVER_ERROR, content = @Content(schema = @Schema(implementation = ApplicationErrorResponse.class)))
    })
    @GetMapping(value = "/logo/{logoId}")
    public void getLogo(@PathVariable String logoId, HttpServletResponse response) throws Exception {
        var minioResponse = logoService.getLogoById(logoId);
        response.setHeader(CONTENT_TYPE, minioResponse.headers().get(CONTENT_TYPE));
        IOUtils.copy(minioResponse, response.getOutputStream());
    }
}
