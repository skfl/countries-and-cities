package com.skfl.city

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CityTest extends AbstractTest {

    @Autowired
    private MockMvc mvc

    def 'get name from MultipartFile success'() {
        given:
        var file = new MockMultipartFile('file', 'TestFile.jpg', MediaType.TEXT_PLAIN_VALUE, 'TestFile'.getBytes())
        expect:
        mvc.perform(multipart('/api/v1/city/1/logo').file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.name').value('Paris'))
                .andExpect(jsonPath('$.countryName').value('USA'))
                .andExpect(jsonPath('$.logo').isNotEmpty())
    }
}
