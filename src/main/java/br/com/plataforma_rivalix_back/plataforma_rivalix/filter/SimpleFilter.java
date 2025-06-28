package br.com.plataforma_rivalix_back.plataforma_rivalix.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Garante que este filtro rode antes de outros filtros de segurança
public class SimpleFilter implements Filter {

    // Injetando a origem permitida do application.properties para ser mais flexível
    @Value("${cors.allowed.origin:http://localhost:5173}")
    private String allowedOriginsConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Adiciona os headers de CORS em TODAS as respostas para garantir consistência.
        // O WebConfig ainda pode ser usado para configurações mais complexas, mas isso resolve o preflight.
        httpResponse.setHeader("Access-Control-Allow-Origin", allowedOriginsConfig);
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, x-requested-with");


        String path = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // ========================================================================
        // REGRA 0: Se a requisição for OPTIONS, ela é uma checagem de preflight do CORS.
        // Já adicionamos os headers acima, então apenas retornamos OK.
        // ========================================================================
        if ("OPTIONS".equalsIgnoreCase(method)) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return; // Permite e sai do filtro
        }

        // ========================================================================
        // REGRA 1: Permite o LOGIN (POST /api/auth/login) SEM token
        // ========================================================================
        if (path.equals("/api/auth/login") && method.equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        // ========================================================================
        // REGRA 2: Permite o CADASTRO (POST /api/auth/register) SEM token
        // ========================================================================
        if (path.equals("/api/auth/register") && method.equals("POST")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Exemplo para permitir GET de todos os usuários sem token (se necessário)
        if (path.equals("/usuarios") && method.equals("GET")) {
            chain.doFilter(request, response);
            return;
        }

        // ========================================================================
        // REGRA GERAL: Para todas as OUTRAS requisições, exige um token
        // ========================================================================
        String authToken = httpRequest.getHeader("Authorization");

        if (authToken != null && authToken.startsWith("Bearer ")) {
            String token = authToken.substring(7);

            if (!token.isEmpty()) {
                chain.doFilter(request, response);
                return;
            }
        }
        
        // SE CHEGAR AQUI: Acesso não autorizado.
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401 Unauthorized
        httpResponse.getWriter().write("Acesso não autorizado. Token ausente ou inválido.");
    }
}
