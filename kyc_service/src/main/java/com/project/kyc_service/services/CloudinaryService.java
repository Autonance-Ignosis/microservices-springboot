package com.project.kyc_service.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(@Value("${cloudinary.cloud-name}") String cloudName,
                             @Value("${cloudinary.api-key}") String apiKey,
                             @Value("${cloudinary.api-secret}") String apiSecret) {
        System.out.println("Cloudinary Service Initialized");
        System.out.println("Cloud Name: " + cloudName);
        System.out.println("API Key: " + apiKey);
        System.out.println("API Secret: " + apiSecret);
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    public String uploadFile(MultipartFile file, String folderName) throws IOException {
        Map<String, Object> uploadParams = ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", folderName
        );

        Map<String, Object> fileMap = ObjectUtils.asMap(
                "file", file.getBytes()
        );

        Map uploadResult = cloudinary.uploader().upload(fileMap.get("file"), uploadParams);

        return uploadResult.get("secure_url").toString();
    }
}
