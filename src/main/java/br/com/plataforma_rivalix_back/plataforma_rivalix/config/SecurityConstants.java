package br.com.plataforma_rivalix_back.plataforma_rivalix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Configuration // Necessário para que @Value funcione
public class SecurityConstants {

    // Define um valor padrão para desenvolvimento local.
  
    @Value("${jwt.secret:minha_chave_secreta_super_segura_e_longa_para_testes_PADRAO_DEV}")
    private String secretString;

    public static SecretKey CHAVE_SECRETA;

    @PostConstruct // Método que será executado após a injeção da dependência
    public void init() {
        CHAVE_SECRETA = Keys.hmacShaKeyFor(secretString.getBytes());
    }
}