package br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto; // Crie um pacote 'dto' para isso

import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class LoginResponse {
    private String token; // Gera um identificador assim que o usuario é logado para acesso ás rotas protegidas
    private String tipo = "Bearer"; 
}
 