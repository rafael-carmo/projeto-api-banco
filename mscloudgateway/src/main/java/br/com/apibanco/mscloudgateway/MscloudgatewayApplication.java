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
				// .route("ms-clientes", r -> r.path("/clientes/**").uri("lb://ms-clientes"))
				// .route("ms-contas", r -> r.path("/contas/**").uri("lb://ms-contas"))
				.route("mstransacoes", r -> r.path("/api/v1/transacoes/**").uri("lb://mstransacoes"))
				.build();
	}

}
