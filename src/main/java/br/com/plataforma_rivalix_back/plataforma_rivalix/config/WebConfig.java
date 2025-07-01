package br.com.plataforma_rivalix_back.plataforma_rivalix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
              .allowedOrigins("*")    // ou allowedOriginPatterns("*")
              .allowedMethods("*")
              .allowedHeaders("*")
              .allowCredentials(false); // ← aqui
    }
}
