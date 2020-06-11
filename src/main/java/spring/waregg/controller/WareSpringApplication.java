package spring.waregg.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"*.controller","*.service","database.config","email.send"})
public class WareSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(WareSpringApplication.class, args);
	}

}
