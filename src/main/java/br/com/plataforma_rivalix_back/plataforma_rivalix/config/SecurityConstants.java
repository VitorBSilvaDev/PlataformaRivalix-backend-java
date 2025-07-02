// Em SecurityConstants.java
package br.com.plataforma_rivalix_back.plataforma_rivalix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Configuration // Necessário para que @Value funcione
public class SecurityConstants {

    // Define um valor padrão para desenvolvimento local.
    // Em produção, a variável de ambiente 'JWT_SECRET' no Heroku deve sobrescrever isso.
    @Value("${jwt.secret:minha_chave_secreta_super_segura_e_longa_para_testes_PADRAO_DEV}")
    private String secretString;

    public static SecretKey CHAVE_SECRETA; // Torna-se não-final para ser atribuída

    @PostConstruct // Este método será executado após a injeção da dependência
    public void init() {
        CHAVE_SECRETA = Keys.hmacShaKeyFor(secretString.getBytes());
    }

    // Não precisa mais de um construtor privado que lança exceção se for @Configuration e tiver @PostConstruct
    // public SecurityConstants() {
    //    throw new IllegalStateException("Classe de constantes não pode ser instanciada");
    // }
}