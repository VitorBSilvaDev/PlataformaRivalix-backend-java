package br.com.plataforma_rivalix_back.plataforma_rivalix.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.plataforma_rivalix_back.plataforma_rivalix.config.SecurityConstants;
import br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto.UsuarioDTO;
import br.com.plataforma_rivalix_back.plataforma_rivalix.model.Usuario;
import br.com.plataforma_rivalix_back.plataforma_rivalix.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

// "Porta de entrada" da aplicação, que recebe e envia dados ao frontend, chamando os serviços, processando a lógica e enviando
// respostas de (Return)

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;	
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> listaUsuarios() {
		;
		return ResponseEntity.status(200).body(usuarioService.listarUsuario());
	}

	@GetMapping("/perfil")
	public ResponseEntity<UsuarioDTO> getUsuarioLogado(HttpServletRequest request) {
		try {
			// 1. Pega o token do cabeçalho "Authorization"
			String authHeader = request.getHeader("Authorization");
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			String token = authHeader.substring(7);

			// 2. Valida e decodifica o JWT usando a chave secreta centralizada
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SecurityConstants.CHAVE_SECRETA).build()
					.parseClaimsJws(token);

			String email = claimsJws.getBody().getSubject();

			// 3. Usa o e-mail para buscar o usuário completo
			Usuario usuario = usuarioService.buscarPorEmail(email);

			// 4. Converte a entidade para DTO antes de enviar
			UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);

			return ResponseEntity.ok(usuarioDTO); // Retorna o DTO seguro

		} catch (Exception e) {
			// Se o token for inválido/expirado, retorna não autorizado
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping
	public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
		return ResponseEntity.status(201).body(usuarioService.criarUsuario(usuario));
	}

	@PutMapping
	public ResponseEntity<Usuario> editarUsuario(@Valid @RequestBody Usuario usuario) {
		return ResponseEntity.status(200).body(usuarioService.editarUsuario(usuario));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirUsuario(@PathVariable Integer id) {
		usuarioService.excluirUsuario(id);
		return ResponseEntity.status(204).build();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);

		});
		return errors;
	}
}
