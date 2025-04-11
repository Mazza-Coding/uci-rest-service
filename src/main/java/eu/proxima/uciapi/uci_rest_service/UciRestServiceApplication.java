package eu.proxima.uciapi.uci_rest_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class UciRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UciRestServiceApplication.class, args);
	}

}
