package com.project1.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.demo.dto.AuthRequestDTO;
import com.project1.demo.dto.AuthResponseDTO;
import com.project1.demo.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request)
	{
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
				
				);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthResponseDTO(token));
	}
}
