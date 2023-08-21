package com.skfl.city

import com.skfl.city.configuration.MinioContainer
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@SpringBootTest(classes = [CityApplication.class])
@AutoConfigureMockMvc
class AbstractTest extends Specification {

    @Shared
    private static PostgreSQLContainer POSTGRES = new PostgreSQLContainer('postgres:11.1')
    @Shared
    private static MinioContainer MINIO = new MinioContainer()

    static {
        POSTGRES.start()
        MINIO.start()
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add('minio.username', MINIO::getUsername)
        registry.add('minio.password',  MINIO::getPassword)
        registry.add('minio.url', MINIO::getHostAddress)
        registry.add('spring.datasource.url', POSTGRES::getJdbcUrl)
        registry.add('spring.datasource.username', POSTGRES::getUsername)
        registry.add('spring.datasource.password', POSTGRES::getPassword)
    }
}
