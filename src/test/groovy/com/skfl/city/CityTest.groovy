package com.skfl.city


import com.skfl.city.dto.response.CityNameResponse
import com.skfl.city.dto.response.CityResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CityTest extends AbstractTest {

    @Autowired
    private MockMvc mvc
    @Autowired
    private Jackson2ObjectMapperBuilder builder

    def 'edit logo without token failure with unauthorized'() {
        given:
        var file = new MockMultipartFile('file', 'TestFile.jpg', MediaType.TEXT_PLAIN_VALUE, 'TestFile'.getBytes())
        expect:
        mvc.perform(multipart('/api/v1/city/1/logo').file(file))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))

    }

    def 'get paginated list of all cities'() {
        given:
        var pageSize = 2
        expect:
        mvc.perform(get('/api/v1/city?size=' + pageSize))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.page.size').value(pageSize))
                .andExpect(jsonPath('$.cities.length()').value(pageSize))
    }

    def 'get cities by country name'() {
        given:
        var countryNameQueryParam = 'USA'
        var objectMapper = builder.build();
        var citiesResponse = objectMapper.readValue(mvc.perform(get("/api/v1/city?countryName=" + countryNameQueryParam))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().response.contentAsString, CityResponse.class).getCities()
        expect:
        citiesResponse.every { elem -> (elem.getCountryName() == countryNameQueryParam) }
    }

    def 'get cities by city name'() {
        given:
        var cityNameQueryParam = 'Paris'
        var objectMapper = builder.build();
        var citiesResponse = objectMapper.readValue(mvc.perform(get("/api/v1/city?countryName=" + cityNameQueryParam))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().response.contentAsString, CityResponse.class).getCities()
        expect:
        citiesResponse.every { elem -> (elem.getCountryName() == cityNameQueryParam) }
    }

    def 'get unique city names'() {
        given:
        var objectMapper = builder.build();
        var citiesNamesResponse = objectMapper.readValue(mvc.perform(get("/api/v1/city/names/unique"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().response.contentAsString, CityNameResponse.class).getNames()
        expect:
        citiesNamesResponse.toSet().size() == citiesNamesResponse.size()
    }

    def 'get cities by non-existing country name return empty list'() {
        given:
        var countryNameQueryParam = 'COUNTRY_THAT_DOES_NOT_EXIST'
        expect:
        mvc.perform(get("/api/v1/city?countryName=" + countryNameQueryParam))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.cities').isEmpty())
    }

    def 'get cities by non-existing city name return empty list'() {
        given:
        var cityNameQueryParam = 'CITY_THAT_DOES_NOT_EXIST'
        expect:
        mvc.perform(get("/api/v1/city?countryName=" + cityNameQueryParam))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.cities').isEmpty())
    }
}
