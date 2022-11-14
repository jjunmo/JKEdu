package com.example.jkedudemo.module.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@RequiredArgsConstructor
public class AmazonS3ResourceStorage {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    //TODO: MediaType 녹음파일 기능 업로드 할시 수정필요.
    public void store(String fullPath, MultipartFile multipartFile) {
        ObjectMetadata metadata =new ObjectMetadata();
        metadata.setContentType(MediaType.ALL_VALUE);
        metadata.setContentLength(multipartFile.getSize());
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, multipartFile.getInputStream(),metadata)
                          .withCannedAcl(CannedAccessControlList.PublicRead));
            amazonS3Client.getResourceUrl(bucket,fullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}