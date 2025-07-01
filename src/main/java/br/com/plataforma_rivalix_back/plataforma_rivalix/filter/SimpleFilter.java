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
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Preflight é liberado pelo CorsConfigurationSource
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // endpoints públicos
        String path   = request.getRequestURI();
        String method = request.getMethod();
        if (path.startsWith("/api/auth/") && ("POST".equals(method))) {
            chain.doFilter(req, res);
            return;
        }

        // JWT no header
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            if (!token.isEmpty()) {
                chain.doFilter(req, res);
                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Acesso não autorizado. Token ausente ou inválido.");
    }
}
