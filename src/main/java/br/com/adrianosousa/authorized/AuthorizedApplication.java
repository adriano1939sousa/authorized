package br.com.adrianosousa.authorized;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class AuthorizedApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizedApplication.class, args);
	}

}
