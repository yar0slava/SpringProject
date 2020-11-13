package com.example.demo;

import com.example.demo.database.entity.User;
import com.example.demo.database.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository){
		User user = new User();
		user.setId(11L);
		user.setLastName("Kupchik");
		user.setFirstName("Daryna");
		user.setAge(20);
		repository.save(user);
		return (args -> repository.findById(1L).ifPresent(System.out::println));
	}

}
