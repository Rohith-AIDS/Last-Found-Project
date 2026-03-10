package com.project1.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.demo.entity.User;
rg.springframework.web.bind.annotation.RestController;

import com.project1.demo.repository.UserRepository;

@RestController
public class UserController {

	private final UserRepository repository;
	
	public UserController(UserRepository repository)
	{
		this.repository=repository;
	}
	
	
	@GetMapping("/user/me")
	public User getCurrentUser(Authentication authentication)
	{
		String username= authentication.getName();
		
		return repository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
	}
	
}
