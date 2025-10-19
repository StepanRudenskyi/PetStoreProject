package org.example.petstore.service.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageStorageService {
    private static final String IMAGE_UPLOAD_DIR = "uploads/images/products";

    public String saveProductImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";

        String filename = UUID.randomUUID() + extension;

        Path uploadDir = Paths.get(IMAGE_UPLOAD_DIR);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // relative URL path that matches ResourceHandler mapping
        return "/images/products/" + filename;
    }
}
