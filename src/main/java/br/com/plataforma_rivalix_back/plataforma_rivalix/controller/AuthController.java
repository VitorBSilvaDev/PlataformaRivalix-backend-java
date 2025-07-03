package br.com.plataforma_rivalix_back.plataforma_rivalix.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto.LoginRequest;
import br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto.LoginResponse;
import br.com.plataforma_rivalix_back.plataforma_rivalix.model.Usuario;
import br.com.plataforma_rivalix_back.plataforma_rivalix.service.UsuarioService;
import jakarta.validation.Valid;

// Controller específico para requisições de cadastro e login
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
	    // autenticarUsuario retorna o JWT completo
	    String jwtToken = usuarioService.autenticarUsuario(loginRequest.getEmail(), loginRequest.getSenha());

	    // Se nenhuma exceção foi lançada, o token é válido
	    return ResponseEntity.ok(new LoginResponse(jwtToken, "Bearer"));
	}

	@PostMapping("/register")
	public ResponseEntity<Usuario> registerUser(@Valid @RequestBody Usuario usuario) {

		// A ResponseStatusException lançada pelo service para e-mail duplicado
		// será capturada pelo @ExceptionHandler(ResponseStatusException.class).
		Usuario novoUsuario = usuarioService.criarUsuario(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    StringBuilder sb = new StringBuilder(); // Para concatenar mensagens

	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        
	        // Adiciona a mensagem formatada para o campo específico
	        errors.put(fieldName, errorMessage); 
	        
	        // E também constrói uma mensagem geral para o campo "message"
	        if (sb.length() > 0) {
	            sb.append("; ");
	        }
	        sb.append(fieldName).append(": ").append(errorMessage);
	    });
	    
	    // Adiciona a chave "message" para ser capturada pelo frontend
	    Map<String, String> responseBody = new HashMap<>();
	    responseBody.put("message", sb.toString()); // Mensagem geral para o alert
	    responseBody.put("details", errors.toString()); // Detalhes dos erros por campo (opcional, para console.error)
	    
	    return responseBody; // Retorna JSON como {"message": "campo: mensagem; outro_campo: mensagem"}
	}

	/**
	 * Trata erros lançados com ResponseStatusException pelo serviço, como e-mail
	 * duplicado (HttpStatus.CONFLICT), ou credenciais inválidas no login
	 * (HttpStatus.UNAUTHORIZED). Retorna o JSON { "message": "Mensagem da exceção"
	 * } e o status HTTP correto.
	 */
	// Este @ExceptionHandler capturará as ResponseStatusException lançadas pelo
	// UsuarioService.
	// Ele retornará um JSON com a chave "message" (como o frontend espera) e o
	// status HTTP correto.
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Map<String, String>> tratarConflito(ResponseStatusException ex) {
		Map<String, String> erro = new HashMap<>();
		// Usa "message" como chave no JSON, pois é o que o frontend espera.
		erro.put("message", ex.getReason());
		return ResponseEntity.status(ex.getStatusCode()).body(erro);
	}
}