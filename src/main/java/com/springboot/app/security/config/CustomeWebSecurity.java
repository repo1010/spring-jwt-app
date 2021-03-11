package com.springboot.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.app.user.repository.UserRepository;
import com.springboot.app.util.JwtRequestFilter;

@Configuration
public class CustomeWebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	private UserRepository userRepository;

	public CustomeWebSecurity(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new CustomeUserDetailsService(this.userRepository));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/api/v1/tyres", "/api/v1/tryes/**")
				.hasAnyRole("ADMIN", "EDITOR", "USER").antMatchers("/api/v1/users", "/api/v1/users/**").hasRole("ADMIN")
				.antMatchers("/h2-console/**", "/api/v1/login", "/v2/api-docs", "/configuration/ui",
						"/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**")
				.permitAll().anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		http.headers().frameOptions().disable();
		System.out.println("In http");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
