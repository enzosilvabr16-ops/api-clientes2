
package br.com.cotiinformatica.api_clientes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("API Clientes")
                        .description(
                                "API REST desenvolvida durante o curso Java WebDeveloper Fullstack da COTI Informática. "
                                        + "O sistema permite o gerenciamento de clientes e planos, "
                                        + "disponibilizando endpoints para operações de cadastro, consulta, atualização e exclusão."
                        )
                        .version("v1")
                        .contact(new Contact()
                                .name("COTI Informática")
                                .email("contato@cotiinformatica.com.br")
                                .url("https://www.cotiinformatica.com.br")
                        )
                );
    }
}
