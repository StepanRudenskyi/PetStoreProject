package org.example.petstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get("uploads/images/products");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("images/products/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }

}
