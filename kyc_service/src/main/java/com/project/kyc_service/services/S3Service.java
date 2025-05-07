package com.project.kyc_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.core.sync.RequestBody;


@Service
@RequiredArgsConstructor
public class S3Service
{

    @Autowired
    private S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * Uploads a file to the S3 bucket.
     */
    public void uploadFile(String keyName, String filePath)
    {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        s3Client.putObject(request, RequestBody.fromFile(Paths.get(filePath)));
    }

    /**
     * Downloads a file from the S3 bucket
     */
    public void downloadFile(String keyName, String downloadPath)
    {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();
        s3Client.getObject(getObjectRequest, Paths.get(downloadPath));
    }
}

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.*;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//public class S3Service {
//
//
//    private final S3Client s3Client;
//    private final String bucketName;
//
//
//    public S3Service(S3Client s3Client,
//                     @Value("${spring.cloud.aws.s3.bucket}") String bucketName) {
//        this.s3Client = s3Client;
//        this.bucketName = bucketName;
//    }
//
//    public String uploadFile(MultipartFile file, String folderPath) throws IOException {
//        String key = folderPath + UUID.randomUUID() + "-" + file.getOriginalFilename();
//
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(key)
//                .contentType(file.getContentType())
//                .acl(ObjectCannedACL.PUBLIC_READ)
//                .build();
//
//        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
//
//        return s3Client.utilities()
//                .getUrl(builder -> builder.bucket(bucketName).key(key))
//                .toExternalForm();
//    }
//
//    public byte[] downloadFile(String fileName) {
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(fileName)
//                .build();
//
//        return s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
//    }
//
//    public void deleteFile(String fileName) {
//        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
//                .bucket(bucketName)
//                .key(fileName)
//                .build();
//
//        s3Client.deleteObject(deleteObjectRequest);
//    }
//}