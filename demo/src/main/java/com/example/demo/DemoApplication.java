package com.example.demo;

import java.util.ArrayList;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	CommandLineRunner run(UserService userService){
		return (args) -> {
			userService.saveRole(new Role(null,"ROLL_USER"));
			userService.saveRole(new Role(null,"ROLL_MANAGER"));
			userService.saveRole(new Role(null,"ROLL_ADMIN"));
			userService.saveRole(new Role(null,"ROLL_SUPER_ADMIN"));
			
			userService.saveUser(new User(null,"john", "1234", new ArrayList<>()));
			userService.saveUser(new User(null,"mark", "1234", new ArrayList<>()));
			userService.saveUser(new User(null,"bob", "1234", new ArrayList<>()));
			userService.saveUser(new User(null,"jack", "1234", new ArrayList<>()));

			userService.addRoleToUser("john", "ROLE_USER");
			userService.addRoleToUser("john", "ROLE_MANAGER");
			userService.addRoleToUser("mark", "ROLE_USER");
			userService.addRoleToUser("bob", "ROLE_USER");
			userService.addRoleToUser("jack", "ROLE_USER");

		};
	}
}
