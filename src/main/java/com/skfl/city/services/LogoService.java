package com.skfl.city.services;

import io.minio.GetObjectResponse;
import org.springframework.web.multipart.MultipartFile;

public interface LogoService {

    GetObjectResponse getLogoById(String logoId) throws Exception;

    String loadLogoFile(MultipartFile multipartFile) throws Exception;

    void deleteLogoFile(String previousLogoUuid) throws Exception;
}
