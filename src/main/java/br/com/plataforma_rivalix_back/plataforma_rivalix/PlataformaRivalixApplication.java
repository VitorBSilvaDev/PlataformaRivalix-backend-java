package br.com.plataforma_rivalix_back.plataforma_rivalix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import br.com.plataforma_rivalix_back.plataforma_rivalix.filter.SimpleFilter;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PlataformaRivalixApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlataformaRivalixApplication.class, args);
	}
	
	@Bean
    public FilterRegistrationBean<SimpleFilter> simpleAuthFilter() {
        FilterRegistrationBean<SimpleFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SimpleFilter());
        registrationBean.addUrlPatterns("/api/torneios/*"); // Aplica o filtro aos caminhos de torneio
        registrationBean.setOrder(1); // Garante que ele rode antes de outros filtros, se houver
        return registrationBean;
    }

}
