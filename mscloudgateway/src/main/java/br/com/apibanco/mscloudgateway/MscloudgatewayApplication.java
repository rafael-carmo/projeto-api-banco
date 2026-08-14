package br.com.apibanco.mscloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MscloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscloudgatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				// Rota de Transações
				.route("mstransacoes", r -> r.path("/mstransacoes/**")
						// Corta o "/mstransacoes" antes de encaminhar para o filho
						.filters(f -> f.stripPrefix(1))
						.uri("lb://mstransacoes"))

				// Rota de Extratos
				.route("msextratoconsumer", r -> r.path("/msextratoconsumer/**")
						// Corta o "/msextratoconsumer" antes de encaminhar para o filho
						.filters(f -> f.stripPrefix(1))
						.uri("lb://msextratoconsumer"))
				.build();

		// return builder.routes()
		// // .route("ms-clientes", r -> r.path("/clientes/**").uri("lb://ms-clientes"))
		// // .route("ms-contas", r -> r.path("/contas/**").uri("lb://ms-contas"))
		// .route("mstransacoes",
		// r -> r.path(
		// "/api/v1/transacoes",
		// "/api/v1/transacoes/**",
		// "/mstransacoes/v3/api-docs" // <-- ADICIONADO: Rota para o Swagger do
		// Transações
		// ).uri("lb://mstransacoes"))
		// .route("msextratoconsumer",
		// r -> r.path(
		// "/api/v1/extratos",
		// "/api/v1/extratos/**",
		// "/msextratoconsumer/v3/api-docs" // <-- ADICIONADO: Rota para o Swagger do
		// Extratos
		// ).uri("lb://msextratoconsumer"))
		// .build();
	}

}
