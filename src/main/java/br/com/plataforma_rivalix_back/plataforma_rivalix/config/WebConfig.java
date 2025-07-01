package br.com.plataforma_rivalix_back.plataforma_rivalix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins}") // definindo variável de ambiente que está dentro do applications.properties
    private String allowedOrigins;

    // CORS(Cross-Origin Resource Sharing) diz ao navegador quais outras origens
    // possuem permissão para enviar requisições á essa aplicação
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // <— aqui
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
