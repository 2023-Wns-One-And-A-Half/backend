package com.oneandahalf.backend.common.image;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadClient {

    default void upload(List<MultipartFile> file) {
        for (MultipartFile multipartFile : file) {
            upload(multipartFile);
        }
    }

    void upload(MultipartFile file);
}
