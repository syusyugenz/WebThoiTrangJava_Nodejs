package com.example.demo.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dsufxiry8"); // Thay thế bằng cloud name của bạn
        config.put("api_key", "368661952794852"); // Thay thế bằng API key của bạn
        config.put("api_secret", "McHjbdl9bXbx8F3hTpAErh6cQ_E"); // Thay thế bằng API secret của bạn
        return new Cloudinary(config);
    }
}