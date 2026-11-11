package com.escuela.gestion_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class GestionApiApplication {

	public static void main(String[] args) {
		// Configuramos nuestros Dotfiles
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		// System.out.println("🔐 Password cargado desde .env: " +
		// System.getProperty("DB_PASSWORD"));
		SpringApplication.run(GestionApiApplication.class, args);
	}

}
