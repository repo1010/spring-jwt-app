package com.springboot.app;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.springboot.app.tyre.model.Tyre;
import com.springboot.app.user.model.UserEntity;
import com.springboot.app.user.repository.UserRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringBootAppApplication {

	@Autowired
	private UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(SpringBootAppApplication.class, args);
	}

	@Bean
	public ApplicationRunner populateData() {

		return (args) -> {

			String month = null;
			if (month == null) {
				month = LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
			}

			/* Load User and Tyre Data */
			UserEntity userEntity1 = new UserEntity("user1", "user1", "USER");
			UserEntity userEntity2 = new UserEntity("user2", "user2", "USER");

			UserEntity userEntity3 = new UserEntity("admin", "admin", "ADMIN");
			userEntity3.setTyre(new HashSet<>());
			UserEntity userEntity4 = new UserEntity("editor", "editor", "EDITOR");
			userEntity4.setTyre(new HashSet<>());

			Set<Tyre> tyresSet1 = new HashSet<Tyre>();
			Tyre tyre1 = new Tyre("user1", month, LocalDate.now().getYear(), 20, 30, userEntity1);
			Tyre tyre2 = new Tyre("user1", month, LocalDate.now().getYear(), 40, 60, userEntity1);
			tyresSet1.add(tyre1);
			tyresSet1.add(tyre2);
			userEntity1.setTyre(tyresSet1);

			Set<Tyre> tyresSet2 = new HashSet<Tyre>();
			Tyre tyre3 = new Tyre("user2", month, LocalDate.now().getYear(), 120, 60, userEntity2);
			Tyre tyre4 = new Tyre("user2", month, LocalDate.now().getYear(), 90, 80, userEntity2);
			tyresSet2.add(tyre3);
			tyresSet2.add(tyre4);

			userEntity2.setTyre(tyresSet2);

			userRepository.saveAll(Arrays.asList(userEntity1, userEntity2, userEntity3, userEntity4));

		};

	}

}
