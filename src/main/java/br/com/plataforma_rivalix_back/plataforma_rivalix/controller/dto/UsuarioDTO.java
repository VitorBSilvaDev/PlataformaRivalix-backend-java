package br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto;

import br.com.plataforma_rivalix_back.plataforma_rivalix.model.Usuario;
import lombok.Data; 

// Para fins de segurança define um usuário com a senha omitida, para requisições seguras no front-end

@Data 
public class UsuarioDTO {
	private Integer id;
    private String nome;
    private String nomeUsuario;
    private String email;
    // O campo 'senha' é intencionalmente omitido.

    // Construtor que converte uma Entidade Usuario em um UsuarioDTO
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.nomeUsuario = usuario.getNomeUsuario();
        this.email = usuario.getEmail();
    }
}