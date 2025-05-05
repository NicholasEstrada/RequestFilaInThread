package com.services.thread.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("${api.data.inventor}")  // Permite apenas requisições originadas de localhost:8080
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Permite métodos específicos
                .allowedHeaders("*");
    }
}