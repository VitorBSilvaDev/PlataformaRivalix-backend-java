package br.com.plataforma_rivalix_back.plataforma_rivalix.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    // 1) PasswordEncoder continua aqui
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) Define a cadeia de filtros do Spring Security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // habilita CORS usando o CorsConfigurationSource abaixo
            .cors().and()
            // geralmente para API stateless com JWT você desabilita CSRF
            .csrf().disable()
            // libera endpoints de auth e exige token nos demais
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            );
            // (aqui você encaixa seu filtro de JWT, sessões etc.)

        return http.build();
    }

    // 3) Aqui está a configuração de CORS “oficial” para o Spring Security:
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // SOMENTE esses hosts — sem barra final!
        config.setAllowedOriginPatterns(List.of(
            "https://rivalix-gaming.vercel.app",
            "http://localhost:3000"
        ));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // aplica a todos os endpoints
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
