package br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto; // Crie um pacote 'dto' para isso

import lombok.Data; // Se estiver usando Lombok

@Data // Gera getters, setters, etc.
public class LoginRequest {
    private String email;
    private String senha; // Ou password
}