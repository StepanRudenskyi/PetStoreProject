package org.example.petstore.service.image;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Profile("prod")
@Service
public class GcsImageStorageService implements ImageStorageService {

    private final Storage storage;
    private final String bucketName;

    @Value("${gcp.bucket.path}")
    private String basePath;

    public GcsImageStorageService(@Value("${gcp.bucket.name}") String bucketName) {
        this.storage = StorageOptions.getDefaultInstance().getService();
        this.bucketName = bucketName;
    }

    @Override
    public String saveProductImage(MultipartFile file) throws IOException {
        return saveFile(file, "products");
    }

    public String saveCategoryImage(MultipartFile file) throws IOException {
        return saveFile(file, "categories");
    }

    private String saveFile(MultipartFile file, String subDir) throws IOException {
        String extension = getFileExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + extension;
        String objectPath = basePath + subDir + "/" + filename;

        BlobId blobId = BlobId.of(bucketName, objectPath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        storage.create(blobInfo, file.getBytes());

        // return relative path for DB
        return "/" + objectPath;
    }

    public String getFileExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) return ".jpg";
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
