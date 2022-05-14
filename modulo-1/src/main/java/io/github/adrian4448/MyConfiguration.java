package io.github.adrian4448;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@DevelopmentConfig
public class MyConfiguration {

    @Bean(name = "outraConfiguracao")
    public String outraConfiguracao() {
        return "Sistema de vendas com outraConfiguracao";
    }

    @Bean(name = "runner")
    public CommandLineRunner executar() {
        return args -> {
            System.out.println("RODANDO A CONFIGURACAO DE DESENVOLVIMENTO");
        };
    }
}
