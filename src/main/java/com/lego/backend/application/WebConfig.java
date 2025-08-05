package com.lego.backend.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173","https://portfolio-esteban-ucc-igpr-git-rama-p-7114b1-jeyban37s-projects.vercel.app") // o el puerto de tu frontend
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
