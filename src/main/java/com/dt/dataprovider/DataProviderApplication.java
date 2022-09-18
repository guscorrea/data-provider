package com.dt.dataprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Data provider",
		description = "The data-provider simulates the real-world, IoT sensor data that will be consumed by the virtual DT microservices.",
		version = "1.0.0"), servers = { @Server(url = "http://localhost:8080", description = "Local Docker deployment URL") })
public class DataProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataProviderApplication.class, args);
	}

}
