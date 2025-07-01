package br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto;

import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor; 

// Após uma resposta bem sucedida ao fazer um login, define os atributos do token que será retornado e armazenado no front-end
@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class LoginResponse {
    private String token; // Gera um identificador assim que o usuario é logado para acesso ás rotas protegidas
    private String tipo = "Bearer"; 
}
 