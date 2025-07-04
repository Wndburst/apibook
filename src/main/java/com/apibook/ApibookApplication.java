package com.apibook;

import com.apibook.entity.ERole;
import com.apibook.entity.Role;
import com.apibook.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApibookApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApibookApplication.class, args);
	}

	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepo) {
		return args -> {
			for (ERole er : ERole.values()) {
				roleRepo.findByName(er).orElseGet(() ->
						roleRepo.save(new Role(null, er)));
			}
		};
	}
}
