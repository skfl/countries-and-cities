package com.skfl.city.config.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class MinioConfig {

    @Value("${minio.username}")
    private String minioUsername;

    @Value("${minio.password}")
    private String minioPassword;

    @Value("${minio.url}")
    private String url;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient client = MinioClient.builder()
                .endpoint(url)
                .credentials(minioUsername, minioPassword)
                .build();
        if(!client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())){
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info("Minio bucket {} created successfully", bucketName);
        }
        return client;
    }
}
