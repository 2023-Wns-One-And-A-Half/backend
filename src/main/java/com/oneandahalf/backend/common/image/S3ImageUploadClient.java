package com.oneandahalf.backend.common.image;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RequiredArgsConstructor
@Component
public class S3ImageUploadClient implements ImageUploadClient {

    private final S3Client s3Client;
    private final AwsS3Property awsS3Property;

    @Override
    @SneakyThrows
    public void upload(MultipartFile file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(awsS3Property.bucket())
                .key(awsS3Property.key() + generateFileName())
                .contentType(file.getContentType())
                .contentDisposition("inline")
                .build();
        RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
        s3Client.putObject(putObjectRequest, requestBody);
    }

    private String generateFileName() {
        return Base64Util.encode(UUID.randomUUID().toString()) + ".jpeg";
    }
}
