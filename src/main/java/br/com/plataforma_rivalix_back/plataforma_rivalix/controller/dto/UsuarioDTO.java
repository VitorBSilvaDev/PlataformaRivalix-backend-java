package br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto; // Crie um pacote 'dto' para isso

import br.com.plataforma_rivalix_back.plataforma_rivalix.model.Usuario;
import lombok.Data; // Se estiver usando Lombok

@Data // Gera getters, setters, etc.
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