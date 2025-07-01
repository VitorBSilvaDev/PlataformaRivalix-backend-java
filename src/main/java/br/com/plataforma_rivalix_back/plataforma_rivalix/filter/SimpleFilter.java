package br.com.plataforma_rivalix_back.plataforma_rivalix.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
/* import org.springframework.beans.factory.annotation.Value;*/
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Filtra cada requisição antes de passar para o controller, solicitando token para endpoints privados 

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Garante que este filtro rode antes de outros filtros de segurança
public class SimpleFilter implements Filter {

/*     // Injetando a origem permitida do application.properties para ser mais flexível
    @Value("${cors.allowed-origins}")
    private String allowedOriginsConfig; */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
       /*  // --- Adicionar cabeçalhos CORS para todas as requisições (crucial para preflight) ---
        // Pega o Origin da requisição
        String origin = httpRequest.getHeader("Origin");
        
     // Verifica se o Origin da requisição está entre os Origins permitidos
        // O allowedOriginsConfig é uma string separada por vírgulas, então precisamos verificar se contém o origin
        if (origin != null) {
            // Divide a string de allowedOriginsConfig em um array para verificar cada um
            String[] allowedOriginsArray = allowedOriginsConfig.split(",");
            boolean isAllowedOrigin = false;
            for (String allowedOrigin : allowedOriginsArray) {
                if (allowedOrigin.trim().equals(origin)) {
                    isAllowedOrigin = true;
                    break;
                }
            }

            if (isAllowedOrigin) {
                httpResponse.setHeader("Access-Control-Allow-Origin", origin);
                httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                httpResponse.setHeader("Access-Control-Allow-Headers", "*"); // Permite todos os headers, ou liste os específicos do seu frontend
                httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
                httpResponse.setHeader("Access-Control-Max-Age", "3600"); // Cacheia a pré-checagem por 1 hora
            } else {
                // Opcional: Se o Origin não for permitido, você pode logar ou rejeitar aqui,
                // mas o CorsFilter do Spring já faria isso se a requisição passasse.
            }
        } */
        // -----------------------------------------------------------------------
        
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
