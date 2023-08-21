package com.skfl.city.services.impl;

import com.skfl.city.services.LogoService;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogoServiceImpl implements LogoService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public GetObjectResponse getLogoById(String logoId) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(logoId)
                .build());
    }


    @Override
    public String loadLogoFile(MultipartFile multipartFile) throws Exception {
        var filename = UUID.randomUUID() + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .contentType(multipartFile.getContentType())
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                .build());
        return filename;
    }


    @Override
    public void deleteLogoFile(String previousLogoUuid) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(previousLogoUuid)
                .build());
    }
}
