package com.oneandahalf.backend.common.image;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.endpoints.internal.Value.Str;

public interface ImageUploadClient {

    default List<String> upload(List<MultipartFile> file) {
        return file.stream()
                .map(this::upload)
                .toList();
    }

    String upload(MultipartFile file);
}
