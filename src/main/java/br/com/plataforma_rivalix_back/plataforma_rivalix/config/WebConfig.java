/* package br.com.plataforma_rivalix_back.plataforma_rivalix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // lista EXATA dos domínios que podem acessar (sem barra final!)
                .allowedOriginPatterns(
                        "https://rivalix-gaming.vercel.app",
                        "http://localhost:3000" // se quiser testar localmente
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
} */