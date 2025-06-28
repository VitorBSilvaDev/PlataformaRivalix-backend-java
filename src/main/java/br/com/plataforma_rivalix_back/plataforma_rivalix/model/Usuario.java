package br.com.plataforma_rivalix_back.plataforma_rivalix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

@Entity
@Table (name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotBlank (message = "O nome é obrigatório") // Define que o campo não pode conter string vazia " " nem ser nulo ""
	@Size (min = 3, message = "O nome deve ter no mínimo 3 caracteres")
	@Column(name = "nome", length = 200, nullable = false)
	private String nome;
	
	@NotBlank (message = "O nome de usuário é obrigatório")
	@Size (min = 3, message = "O nome de usuário deve ter no mínimo 3 caracteres")
	@Column (name = "nome_usuario", length = 200, nullable = false)
	private String nomeUsuario;
	
	@Email(message = "Insira um e-mail válido")
	@NotBlank (message = "O e-mail é obrigatório")
	@Column(name = "email", length = 50, nullable = false)
	private String email;
	
	@NotBlank (message = "A senha é obrigatória")
	@Column(name = "senha", columnDefinition = "TEXT", nullable = false)
	private String senha;
}
