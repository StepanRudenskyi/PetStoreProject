package org.example.petstore.mapper.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Profile("prod")
@Component
public class GcsImageUrlMapper implements ImageUrlMapper {

    @Value("${gcp.bucket.url}")
    private String bucketUrl;

    @Override
    public String toFullImageUrl(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) return null;
        // prepend Google Cloud Storage base URL
        return bucketUrl + relativePath.replaceFirst("^/", "");
    }

    @Override
    public String toRelativeImagePath(String fullUrl) {
        if (fullUrl == null || fullUrl.isEmpty()) return null;
        // remove the bucket prefix
        return fullUrl.replaceFirst("^" + Pattern.quote(bucketUrl), "");
    }
}