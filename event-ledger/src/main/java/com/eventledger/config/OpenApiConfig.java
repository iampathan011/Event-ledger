package com.eventledger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI eventLedgerOpenAPI() {
		return new OpenAPI().info(new Info().title("Event Ledger API").version("1.0.0")
				.description("Financial transaction event ledger. " + "Guarantees: idempotent event submission, "
						+ "out-of-order tolerance, and correct balance computation.")
				.contact(new Contact().name("Event Ledger Team")));
	}
}
