package br.com.plataforma_rivalix_back.plataforma_rivalix.filter; // Ou outro pacote de filtros

import br.com.plataforma_rivalix_back.plataforma_rivalix.config.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections; 

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continua a cadeia de filtros
            return;
        }

        String token = header.substring(7); // Remove "Bearer "

        try {
            // Valida e decodifica o JWT usando a chave secreta
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SecurityConstants.CHAVE_SECRETA)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject(); // O email do usuário que foi salvo como Subject

            // Se o token for válido, autentica o usuário no contexto de segurança do Spring
            // Por simplicidade, está sendo usado uma authority vazia ou genérica aqui.
            // Em uma aplicação real, seria buscado as roles/permissões do usuário do banco de dados.
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Exemplo de role
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            logger.error("Erro na validação do JWT: " + e.getMessage());
            // Limpa o contexto de segurança e retorna não autorizado
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("Token inválido ou expirado.");
            return; // Interrompe a cadeia de filtros para esta requisição
        }

        filterChain.doFilter(request, response); // Continua a cadeia de filtros
    }
}