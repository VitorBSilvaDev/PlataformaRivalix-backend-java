package br.com.plataforma_rivalix_back.plataforma_rivalix.config; // Ou em um pacote de sua preferência

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

public class SecurityConstants {

    // 1. Definimos a chave secreta como uma constante pública e estática.
    // Assim, ela existe em um único lugar.
    public static final SecretKey CHAVE_SECRETA = Keys.hmacShaKeyFor(
        "minha_chave_secreta_super_segura_e_longa_para_testes".getBytes()
    );

    // 2. Prevenimos que esta classe seja instanciada.
    private SecurityConstants() {
        throw new IllegalStateException("Classe de constantes não pode ser instanciada");
    }
}
