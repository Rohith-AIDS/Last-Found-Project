package com.project1.demo.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project1.demo.dto.RegisterRequestDTO;
import com.project1.demo.entity.User;
import com.project1.demo.repository.UserRepository;

@Service
public class UserServiceImplementation  implements UserService{
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImplementation(UserRepository repository, PasswordEncoder passwordEncoder)
	{
		this.repository=repository;
		this.passwordEncoder=passwordEncoder;
	}
	
	@Override
	public User register (RegisterRequestDTO request)
	{
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("USER");
		return repository.save(user);
	}
}


