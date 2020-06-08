package spring.waregg.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"card.controller","card.mapper","database.config"})
public class WareSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(WareSpringApplication.class, args);
	}

}
