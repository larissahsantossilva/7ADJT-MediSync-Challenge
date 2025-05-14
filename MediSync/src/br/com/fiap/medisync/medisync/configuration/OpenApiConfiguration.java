package main.java.br.com.fiap.medisync.medisync.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@OpenAPIDefinition
@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI getMedisyncAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("MediSync API Turma 7ADJT Grupo 4")
                                .description("Projeto desenvolvido para challenge.")
                                .version("v1.0.0")
                                .license(new License().name("Apache 2.0").url("https://github.com/larissahsantossilva/7ADJT-MediSync-Challenge"))
                );
    }
}