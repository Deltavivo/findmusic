package com.deltavivo.findmusic;

import com.deltavivo.findmusic.principal.Principal;
import com.deltavivo.findmusic.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FindmusicApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FindmusicApplication.class, args);
	}

	@Autowired
	private ArtistaRepository repository;

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.exibeMenu();
	}
}
