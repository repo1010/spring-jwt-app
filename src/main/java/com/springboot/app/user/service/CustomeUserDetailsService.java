package com.springboot.app.user.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.springboot.app.user.model.UserEntity;
import com.springboot.app.user.repository.UserRepository;

/**
 * Load data from DB for a USERNAME and construct USerDetails instance.
 * 
 * @author VISHAL
 *
 */
@Component
public class CustomeUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public CustomeUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Load data from DB for a given USERNAME and construct USerDetails Instance. In
	 * case of non validity of USERNAME , it throws UsernameNotFoundException.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = this.userRepository.findByUsername(username);
		if (user != null) {

			return User.withUsername(user.getUsername()).password(passwordEncoder().encode(user.getPassword()))
					.roles(user.getRole()).build();
		}

		throw new UsernameNotFoundException(username);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}