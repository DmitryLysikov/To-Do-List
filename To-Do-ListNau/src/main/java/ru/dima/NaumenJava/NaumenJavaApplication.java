package ru.dima.naumenjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ru.dima.naumenjava.criteria")
public class NaumenJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaumenJavaApplication.class, args);
	}

}
