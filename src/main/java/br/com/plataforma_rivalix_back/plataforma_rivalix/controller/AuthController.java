package br.com.plataforma_rivalix_back.plataforma_rivalix.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.plataforma_rivalix_back.plataforma_rivalix.config.SecurityConstants;
import br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto.LoginRequest;
import br.com.plataforma_rivalix_back.plataforma_rivalix.controller.dto.LoginResponse;
import br.com.plataforma_rivalix_back.plataforma_rivalix.model.Usuario;
import br.com.plataforma_rivalix_back.plataforma_rivalix.service.UsuarioService;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Usuario usuarioParaLogin = new Usuario();
        usuarioParaLogin.setEmail(loginRequest.getEmail());
        usuarioParaLogin.setSenha(loginRequest.getSenha());

        Boolean loginValido = usuarioService.validarSenha(usuarioParaLogin);

        if (loginValido) {
            // Se o login for válido, gera o JWT
            long agora = System.currentTimeMillis();
            long expiracao = agora + 3600000; // Expira em 1 hora

            String jwtToken = Jwts.builder()
                .setSubject(loginRequest.getEmail()) // Define o "dono" do token (o e-mail do usuário)
                .setIssuedAt(new Date(agora))
                .setExpiration(new Date(expiracao))
                .signWith(SecurityConstants.CHAVE_SECRETA)
                .compact();

            // Retorna o JWT para o cliente
            return ResponseEntity.ok(new LoginResponse(jwtToken, "Bearer"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    // O endpoint de registro continua o mesmo
    @PostMapping("/register")
    public ResponseEntity<Usuario> registerUser(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.criarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
