package br.com.plataforma_rivalix_back.plataforma_rivalix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Esta classe de configuração é usada para definir Beans que serão gerenciados pelo Spring.
 * Beans são objetos que formam a espinha dorsal da sua aplicação.
 */
@Configuration
public class SecurityConfig {

    /**
     * Este método define como o Spring deve criar um objeto PasswordEncoder.
     * A anotação @Bean diz ao Spring: "Quando alguém pedir por um PasswordEncoder,
     * execute este método e use o objeto que ele retorna".
     * return uma instância de BCryptPasswordEncoder, que é uma implementação segura de PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}