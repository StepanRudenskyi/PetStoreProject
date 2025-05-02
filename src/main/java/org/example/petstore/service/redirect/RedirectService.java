package org.example.petstore.service.redirect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
public class RedirectService {

    @Value("${frontend.redirect.url}")
    private String frontendRedirectBaseUrl;

    @Value("${frontend.login.url}")
    private String frontendLoginErrorUrl;

    public String buildSuccessRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString(frontendRedirectBaseUrl)
                .queryParam("token", token)
                .build()
                .toUriString();
    }

    public String getErrorRedirectUrl() {
        return frontendLoginErrorUrl + "?error=oauth2_failed";
    }

    public String getErrorRedirectUrl(String errorMessage) {
        return frontendLoginErrorUrl + "?error=oauth2_failed&message=" + UriUtils.encodeQueryParam(errorMessage, StandardCharsets.UTF_8);
    }

}
