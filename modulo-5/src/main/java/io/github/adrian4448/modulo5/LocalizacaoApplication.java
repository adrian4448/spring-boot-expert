package io.github.adrian4448.modulo5;

import io.github.adrian4448.modulo5.domain.entity.Cidade;
import io.github.adrian4448.modulo5.domain.repository.CidadeRepository;
import io.github.adrian4448.modulo5.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocalizacaoApplication implements CommandLineRunner {

	@Autowired
	private CidadeService cidadeService;

	@Override
	public void run(String... args) throws Exception {
		Cidade cidade = new Cidade();
		cidade.setNome("Sao paulo");

		cidadeService.filtroDinamicoComExample(cidade);
	}

	public static void main(String[] args) {
		SpringApplication.run(LocalizacaoApplication.class, args);
	}

}
