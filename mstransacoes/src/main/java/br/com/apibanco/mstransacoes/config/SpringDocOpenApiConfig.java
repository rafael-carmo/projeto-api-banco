package br.com.apibanco.mstransacoes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()

                        .title("API de Transações bancárias - SpringDoc OpenAPI 3.1")
                        .description(
                                "API para gerenciamento de transações bancárias, incluindo depósitos, saques e transferências.")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Rafael Linsmar").email("teste@gmail.com")));
    }

}
