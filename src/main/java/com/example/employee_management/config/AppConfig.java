package com.example.employee_management.config;
import com.example.employee_management.util.WelcomeMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public WelcomeMessage welcomeMessage() {
        return new WelcomeMessage();
    }
}
