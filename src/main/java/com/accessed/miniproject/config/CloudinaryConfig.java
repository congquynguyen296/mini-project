package com.accessed.miniproject.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.name}")
    private String NAME;

    @Value("${cloudinary.apiKey}")
    private String API_KEY;

    @Value("${cloudinary.secretKey}")
    private String SECRET_KEY;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", NAME,
                "api_key", API_KEY,
                "api_secret", SECRET_KEY
        ));
    }
}
