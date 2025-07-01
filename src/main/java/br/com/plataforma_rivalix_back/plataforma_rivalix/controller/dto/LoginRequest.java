package br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto;

import lombok.Data; 

// Definindo exatamente o que um campo de login espera. Email e senha.
@Data // Gera getters, setters, etc.
public class LoginRequest {
    private String email;
    private String senha; // Ou password
}