package br.com.plataforma_rivalix_back.plataforma_rivalix.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// IMPORTANTE: Adicione esta importação para o CSRF
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and() // Habilita CORS via CorsConfigurationSource
                // === NOVO CSRF: Comente o .csrf().disable() e use esta configuração ===
                .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/api/auth/**") // Ignora CSRF para todas as rotas de autenticação
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // Recomendado para SPAs
                )
                // === FIM DO NOVO CSRF ===
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Endpoint de registro e login são públicos
                        .anyRequest().authenticated()); // Todas as outras requisições precisam ser autenticadas

        // Certifique-se de que não há nenhuma linha adicionando SimpleFilter aqui.
        // Ele já foi removido, certo?

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // ... (Seu código CorsConfigurationSource que está funcionando)
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of(
                "https://rivalix-gaming.vercel.app",
                "http://localhost:3000",
                "https://plataforma-rivalix-gaming-24af3d5ab112.herokuapp.com"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "Accept"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}