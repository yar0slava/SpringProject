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
		user.setId(113L);
		user.setFirstName("Svitlana");
		user.setLastName("Lukichova");
		user.setEmail("Svitlana.Lukichova@ukma.edu.ua");
		user.setPassword("Lukichova");

		User u1 = new User(110L, "Svitlana", "Lukichova", "Svitlana.Lukichova@ukma.edu.ua", "Lukichova");

		return (args -> {
			repository.save(user);
			repository.findById(113L).ifPresent(System.out::println);
			repository.findByEmail("Svitlana.Lukichova@ukma.edu.ua").ifPresent(System.out::println);
			repository.delete(user);
			repository.deleteById(111L);
			System.out.println(repository.groupingByName());
			repository.update(u1);
			repository.findById(110L).ifPresent(System.out::println);

			for (User u: repository.findAll()) {
				System.out.println(u.toString());
			}

		});
	}

}
