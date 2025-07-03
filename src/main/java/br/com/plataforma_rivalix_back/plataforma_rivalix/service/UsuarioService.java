package br.com.plataforma_rivalix_back.plataforma_rivalix.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import br.com.plataforma_rivalix_back.plataforma_rivalix.config.SecurityConstants;
import br.com.plataforma_rivalix_back.plataforma_rivalix.model.Usuario;
import br.com.plataforma_rivalix_back.plataforma_rivalix.repository.IUsuario;
import io.jsonwebtoken.Jwts;

// Classe que declara métodos que definem a regra de negócios do projeto. 
@Service
public class UsuarioService {

	// Injeção de dependências que automaticamente cria uma instância de IUsuario e
	// PasswordEncoder para usá-los
	private final IUsuario repository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(IUsuario repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Usuario> listarUsuario() {
		return repository.findAll();
	}

	/**
	 * Busca um usuário pelo seu e-mail
	 * 
	 * @return O objeto Usuario se encontrado.
	 * @throws RuntimeException se nenhum usuário for encontrado com o e-mail
	 *                          fornecido.
	 */
	public Usuario buscarPorEmail(String email) {
		return repository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail: " + email));
	}

	@Transactional
	public Usuario criarUsuario(Usuario usuario) {
		System.out.println("DEBUG: Comprimento da senha recebida: " + usuario.getSenha().length());

		if (repository.findByEmail(usuario.getEmail()).isPresent()) {
			// Lançar ResponseStatusException com a mensagem desejada
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
		}

		if (repository.findByNomeUsuario(usuario.getNomeUsuario()).isPresent()) { 
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Este nome de usuário já está em uso.");
		}

		String senhaCriptografada = this.passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		return repository.save(usuario);
	}

	public Usuario editarUsuario(Usuario usuario) {
		Usuario usuarioExistente = repository.findById(usuario.getId())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado para edição. ID: " + usuario.getId()));

		if (usuario.getSenha() != null && !usuario.getSenha().trim().isEmpty()) {
			String senhaCriptografada = this.passwordEncoder.encode(usuario.getSenha());
			usuarioExistente.setSenha(senhaCriptografada);
		}

		usuarioExistente.setNome(usuario.getNome());
		usuarioExistente.setNomeUsuario(usuario.getNomeUsuario());

		return repository.save(usuarioExistente);
	}

	public Boolean excluirUsuario(Integer id) {
		repository.deleteById(id);
		return true;
	}

	public String autenticarUsuario(String email, String senha) {
		Optional<Usuario> usuarioOptional = repository.findByEmail(email);

		if (usuarioOptional.isEmpty()) {
			// Lançar exceção se o usuário não for encontrado
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos.");
		}

		Usuario usuarioDoBanco = usuarioOptional.get();

		// Usar passwordEncoder.matches para comparar a senha fornecida com a senha
		// criptografada do banco
		if (!passwordEncoder.matches(senha, usuarioDoBanco.getSenha())) {
			// Lançar exceção se a senha não coincidir
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos.");
		}
		// Se chegou aqui, as credenciais são válidas.
		long agora = System.currentTimeMillis();
		long expiracao = agora + 3600000; // Expira em 1 hora

		String jwtToken = Jwts.builder()
				.setSubject(email) // O subject geralmente é o identificador único do usuário
				.setIssuedAt(new Date(agora))
				.setExpiration(new Date(expiracao))
				.signWith(SecurityConstants.CHAVE_SECRETA) // Chave secreta será usada
				.compact();

		return jwtToken; // Retorna o JWT gerado
	}
}
