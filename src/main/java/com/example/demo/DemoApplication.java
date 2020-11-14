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
//		UserEntity userEntity = new UserEntity();
//		userEntity.setLastName("Kupchik");
//		userEntity.setFirstName("Daryna");
//		userEntity.setGender(Gender.FEMALE);
//		userEntity.setAge(20);
//		repository.save(userEntity);
		repository.findAll(PageRequest.of(0,10, Sort.Direction.DESC, "age"));
		return (args -> repository.findById(1L).ifPresent(System.out::println));
	}

}
