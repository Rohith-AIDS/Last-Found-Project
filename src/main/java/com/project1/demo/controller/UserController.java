package com.project1.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project1.demo.dto.ChangePasswordDTO;
import com.project1.demo.dto.UpdateProfileDTO;
import com.project1.demo.dto.UserResponseDTO;
import com.project1.demo.entity.User;

import com.project1.demo.repository.UserRepository;
import com.project1.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	private final UserRepository repository;
	
	private final UserService userService;
	
	public UserController(UserRepository repository,UserService userService)
	{
		this.repository=repository;
		this.userService=userService;
	}
	
	
	@GetMapping("/user/me")
	public User getCurrentUser(Authentication authentication)
	{
		String username= authentication.getName();
		
		return repository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
	}
	
	@PutMapping("/profile")
	public ResponseEntity<UserResponseDTO> updateProfile(@Valid @RequestBody UpdateProfileDTO dto, Authentication authentication)
	{
		String username=authentication.getName();
		return ResponseEntity.ok(userService.updateProfile(dto,username));
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<String> changePassword(
	        @RequestBody ChangePasswordDTO dto,
	        Authentication authentication) {

	    String username = authentication.getName();

	    userService.changePassword(dto, username);

	    return ResponseEntity.ok("Password updated successfully");
	}
}
