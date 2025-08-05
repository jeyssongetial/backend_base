package com.lego.backend.application;


import com.lego.backend.domain.usecases.TareasUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class UseCaseConfig {
    @Bean
    public TareasUseCase tareasUseCase() {
        return new TareasUseCase();
    }
}
