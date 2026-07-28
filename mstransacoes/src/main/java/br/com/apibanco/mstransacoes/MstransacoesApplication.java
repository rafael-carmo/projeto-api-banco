package br.com.apibanco.mstransacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MstransacoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MstransacoesApplication.class, args);
	}

}
