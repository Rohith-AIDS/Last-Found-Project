package com.project1.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
			.csrf(csrf->csrf.disable())
			.authorizeHttpRequests(auth->auth
					.requestMatchers("/h2-console/**").permitAll()
					.requestMatchers("/items/**").authenticated()
					.anyRequest().permitAll()
					)
		.httpBasic(Customizer.withDefaults());
		http.headers(headers->headers.frameOptions(frame->frame.disable()));
		
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService()
	{
		return new InMemoryUserDetailsManager(
				User.withUsername("rohith")
				.password("{noop}1234")
				.roles("USER")
				.build(),
				
				User.withUsername("admin")
				.password("{noop}1234")
				.roles("ADMIN")
				.build()
				);
	}
	
}
