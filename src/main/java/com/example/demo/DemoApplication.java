package com.example.demo;

import com.example.demo.database.entity.Gender;
import com.example.demo.database.entity.UserEntity;
import com.example.demo.database.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository){
		UserEntity userEntity = new UserEntity();
		userEntity.setLastName("Kupchik");
		userEntity.setFirstName("Alina");
		userEntity.setGender(Gender.FEMALE);
		userEntity.setAge(21);
		userEntity.setEmail("Kupchik.Alina@ukma.edu.ua");
		repository.save(userEntity);

//		System.err.println(repository.findByEmail("Kupchik.Daryna@ukma.edu.ua"));
//		System.err.println(repository.existsByEmail("Kupchik.Daryna@ukma.edu.ua"));
//		System.err.println(repository.findByFirstNameAndLastName("Daryna","Kupchik"));
		return (args -> {
			System.err.println(repository.existsByEmail("Kupchik.Daryna@ukma.edu.ua"));
		});
	}

}
