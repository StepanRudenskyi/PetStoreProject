package org.example.petstore.mapper.helpers;

import org.mapstruct.Named;

public interface ImageUrlMapper {
    @Named("toFullImageUrl")
    String toFullImageUrl(String relativePath);

    @Named("toRelativeImagePath")
    String toRelativeImagePath(String fullUrl);
}
