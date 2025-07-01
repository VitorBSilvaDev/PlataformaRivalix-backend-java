// src/test/java/br/com/plataforma_rivalix_back/plataforma_rivalix/PlataformaRivalixApplicationTests.java
package br.com.plataforma_rivalix_back.plataforma_rivalix;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; // Importe esta anotação

@SpringBootTest
@ActiveProfiles("test") // Ativa o perfil 'test' para esta classe de teste
class PlataformaRivalixApplicationTests {

	@Test
	void contextLoads() {
        // Este teste simplesmente verifica se o contexto da aplicação pode ser carregado
        // com as configurações do perfil 'test'.
	}

}
