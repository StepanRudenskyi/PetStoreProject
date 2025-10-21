package org.example.petstore.service.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStorageService {
    /**
     * Save uploaded image and return its relative path to store in DB
     */
    String saveProductImage(MultipartFile file) throws IOException;
}
