package com.project1.demo.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project1.demo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository repository;
	
	public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		 com.project1.demo.entity.User user = repository.findByUsername(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		 
		 return new User(user.getUsername(),user.getPassword(),Collections.emptyList());
	}
}
