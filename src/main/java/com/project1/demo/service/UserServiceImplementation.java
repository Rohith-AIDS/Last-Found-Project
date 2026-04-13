package com.project1.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project1.demo.dto.ChangePasswordDTO;
import com.project1.demo.dto.RegisterRequestDTO;
import com.project1.demo.dto.UpdateProfileDTO;
import com.project1.demo.dto.UserResponseDTO;
import com.project1.demo.entity.User;
import com.project1.demo.exception.BadRequestException;
import com.project1.demo.exception.ResourceNotFoundException;
import com.project1.demo.mapper.UserMapper;
import com.project1.demo.repository.UserRepository;

@Service
public class UserServiceImplementation  implements UserService{
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserMapper userMapper;
	
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
	
	
	public UserResponseDTO updateProfile(UpdateProfileDTO dto, String currentUsername) {
		User user = repository.findByUsername(currentUsername)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (!user.getUsername().equals(dto.getUsername()) && repository.existsByUsername(dto.getUsername())) {
			throw new BadRequestException("Username already taken");
		}
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());

		User updatedUser = repository.save(user);

		return userMapper.toDTO(updatedUser);
	}
	
	public void changePassword(ChangePasswordDTO dto, String username) {

	    User user = repository.findByUsername(username)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

	    // 🔐 Check old password
	    if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
	        throw new BadRequestException("Old password is incorrect");
	    }

	    // ⚠️ Prevent same password
	    if (dto.getOldPassword().equals(dto.getNewPassword())) {
	        throw new BadRequestException("New password cannot be same as old password");
	    }

	    // 🔐 Encode new password
	    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

	    repository.save(user);
	}
}

