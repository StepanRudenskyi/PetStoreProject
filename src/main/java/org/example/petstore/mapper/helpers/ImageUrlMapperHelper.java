package org.example.petstore.mapper.helpers;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ImageUrlMapperHelper {

    @Named("toFullImageUrl")
    public String toFullImageUrl(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return null;
        }
        // build full URL https://localhost:8443/images/products/uuid.png
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(relativePath)
                .toUriString();
    }

    @Named("toRelativeImagePath")
    public String toRelativeImagePath(String fullUrl) {
        if (fullUrl == null || fullUrl.isEmpty()) {
            return null;
        }
        return fullUrl.replaceFirst("^https?://[^/]+", "");
    }
}

