package com.project1.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
	
	private final InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(
			User.withUsername("rohith")
			.password("{noop}1234")
			.roles("USER")
			.build(),
			
			User.withUsername("guna")
			.password("{noop}1234")
			.roles("USER")
			.build(),
			
			User.withUsername("admin")
			.password("{noop}1234")
			.roles("ADMIN")
			.build()
			);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return manager.loadUserByUsername(username);
	}
}
