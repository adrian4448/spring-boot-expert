package io.github.adrian4448;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
Aqui e para exportar bibliotecas externas, que nao estao dentro do pacote
anotado com SpringBootApplication
@ComponentScan(
        basePackages = {"com.outraBiblioteca.projeto"}
)
*/
@SpringBootApplication
@RestController
public class VendasApplication {

    // Pega a configuracao dentro da classe anotada com a anottation @Configuration
    @Autowired
    @Qualifier("outraConfiguracao")
    private String outraConfiguracao;

    // pega as configuracoes dentro do arquivo de configuracao application.properties
    @Value("${application.name}")
    private String applicationName;

    @GetMapping("/hello")
    public String helloWorld() {
        return applicationName;
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
