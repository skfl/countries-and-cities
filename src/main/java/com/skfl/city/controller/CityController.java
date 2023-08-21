package com.skfl.city.controller;

import com.skfl.city.dto.CityDto;
import com.skfl.city.dto.response.CityNameResponse;
import com.skfl.city.dto.response.CityResponse;
import com.skfl.city.services.CityService;
import com.skfl.city.services.LogoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private final CityService cityService;

    private final LogoService logoService;

    @GetMapping("/unique")
    public ResponseEntity<CityNameResponse> getAllUniqueCityName(@RequestParam(defaultValue = "3") int size,
                                                                 @RequestParam(defaultValue = "0") int page) {
        var pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                cityService.getAllUniqueCityName(pageable)
        );
    }

    @GetMapping("")
    public ResponseEntity<CityResponse> getAllCities(@RequestParam(defaultValue = "3") int size,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(required = false) String countryName,
                                                     @RequestParam(required = false) String cityName) {
        if (countryName != null && cityName != null) {
            throw new IllegalArgumentException();
        }

        var pageable = PageRequest.of(page, size);

        if (countryName != null) {
            return ResponseEntity.ok(
                    cityService.getAllCitiesByCountryName(countryName, pageable)
            );
        }

        if (cityName != null) {
            return ResponseEntity.ok(
                    cityService.getAllCitiesByCityName(cityName, pageable)
            );
        }


        return ResponseEntity.ok(
                cityService.getAllCities(pageable)
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<CityDto> editCityNameAndLogoByCityId(@RequestBody @Valid CityDto cityDto,
                                                               @Valid @PositiveOrZero @PathVariable Long id) {
        return ResponseEntity.ok(
                cityService.editCityNameByCityId(id, cityDto)
        );
    }

    @PostMapping("/{id}/logo")
    public ResponseEntity<CityDto> uploadCityLogo(@RequestParam("file") MultipartFile file,
                                                  @PositiveOrZero @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(
                cityService.editCityLogoByCityId(id, file)
        );
    }

    @GetMapping(value = "{logoId}")
    public void getLogo(@PathVariable String logoId, HttpServletResponse response) throws Exception {
        var minioResponse = logoService.getLogoById(logoId);
        response.setHeader(CONTENT_TYPE, minioResponse.headers().get(CONTENT_TYPE));
        IOUtils.copy(minioResponse, response.getOutputStream());
    }
}
