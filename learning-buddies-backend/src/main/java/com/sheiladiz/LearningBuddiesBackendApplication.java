package com.sheiladiz;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sheiladiz.models.Permission;
import com.sheiladiz.models.Role;
import com.sheiladiz.models.RoleEnum;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.repositories.UserRepository;

@SpringBootApplication
public class LearningBuddiesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningBuddiesBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			Permission createPermission = new Permission("CREATE");
			Permission readPermission = new Permission("READ");
			Permission updatePermission = new Permission("UPDATE");
			Permission deletePermission = new Permission("DELETE");
			Permission refactorPermission = new Permission("REFACTOR");

			Role roleAdmin = new Role(RoleEnum.ADMIN,
					Set.of(createPermission, readPermission, updatePermission, deletePermission));
			Role roleUser = new Role(RoleEnum.USER, Set.of(createPermission, readPermission));
			Role roleInvited = new Role(RoleEnum.INVITED, Set.of(readPermission));
			Role roleDeveloper = new Role(RoleEnum.ADMIN,
					Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission));

			String email = System.getenv("SPRING_EMAIL");
			String password = System.getenv("SPRING_PASS");
			UserEntity userSheila = new UserEntity(email, password, password, "local", true, true, true, true,
					Set.of(roleDeveloper));

			userRepository.save(userSheila);
		};
	}

}
