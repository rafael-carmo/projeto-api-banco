package br.com.apibanco.msextratoconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsextratoconsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsextratoconsumerApplication.class, args);
	}

}
