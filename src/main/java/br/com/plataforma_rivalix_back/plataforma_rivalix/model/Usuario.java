package br.com.plataforma_rivalix_back.plataforma_rivalix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotBlank(message = "O nome é obrigatório") // Define que o campo não pode conter string vazia " " nem ser nulo ""
	@Size(min = 3, max = 100, message = "O nome deve ter no mínimo 3 caracteres e máximo 26 caracteres")
	@Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s']+$", message = "O nome deve conter apenas letras e espaços.")
	@Column(name = "nome", length = 200, nullable = false)
	private String nome;

	@NotBlank(message = "O nome de usuário é obrigatório")
	@Size(min = 3, max = 20, message = "O nome de usuário deve ter entre 3 e 20 caracteres")
	@Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "O nome de usuário pode conter apenas letras, números, _ (underscore), . (ponto) e - (hífen).")
	@Column(name = "nome_usuario", length = 200, nullable = false, unique = true)
	private String nomeUsuario;

	@Email(message = "Insira um e-mail válido")
	@NotBlank(message = "O e-mail é obrigatório")
	@Size(max = 50, message = "O e-mail pode ter no máximo 50 caracteres")
	@Column(name = "email", length = 50, nullable = false, unique = true)
	private String email;

	@NotBlank(message = "A senha é obrigatória")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=\\-\\[\\]{};':\"|,.<>/?]).*$", message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial.")
	@Size(min = 8, message = "Senha deve conter no mínimo 8, no máximo 30 caracteres")
	@Column(name = "senha", columnDefinition = "TEXT", nullable = false)
	private String senha;
}
