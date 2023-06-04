package fr.fms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

public interface SecurityConfigTest {
    @Bean
    AuthenticationManager configure(AuthenticationManagerBuilder auth) throws Exception;
}
