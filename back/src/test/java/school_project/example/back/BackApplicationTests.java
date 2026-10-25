package school_project.example.back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
// Confirma que o contexto principal do Spring inicia no perfil isolado de teste.
class BackApplicationTests {

	@Test
	void contextLoads() {
	}

}
