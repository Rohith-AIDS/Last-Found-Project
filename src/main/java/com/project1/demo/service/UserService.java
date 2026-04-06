package com.project1.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.project1.demo.dto.RegisterRequestDTO;
import com.project1.demo.dto.UpdateProfileDTO;
import com.project1.demo.dto.UserResponseDTO;
import com.project1.demo.entity.User;
import com.project1.demo.mapper.UserMapper;

public interface UserService {
	
	User register(RegisterRequestDTO request);
	
	UserResponseDTO updateProfile(UpdateProfileDTO dto, String currentUsername);
}
