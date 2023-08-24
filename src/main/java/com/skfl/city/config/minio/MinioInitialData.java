package com.skfl.city.config.minio;

import com.skfl.city.repository.CountryRepository;
import com.skfl.city.services.CityService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class MinioInitialData {


    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @EventListener(ApplicationReadyEvent.class)
    public void initMinioStorage() {
        Arrays.stream(Locale.getISOCountries()).parallel()
                .map(code -> String.format("https://flagsapi.com/%s/flat/64.png", code))
                .forEach(str -> {
                    try {
                        var url = new URL(str);
                        var connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        minioClient.putObject(PutObjectArgs.builder()
                                .object(str.substring(21, 23) + ".png")
                                .bucket(bucketName)
                                .stream(connection.getInputStream(), -1, 10000000)
                                .build());
                    } catch (Exception ignored) {
                        log.info("Error downloading file from: " + str);
                    }
                });
        log.info("Minio data was loaded");
    }
}
