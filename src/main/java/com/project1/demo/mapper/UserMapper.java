package com.project1.demo.mapper;


import org.springframework.stereotype.Component;

import com.project1.demo.dto.UserResponseDTO;
import com.project1.demo.entity.User;

@Component
public class UserMapper {
	public UserResponseDTO toDTO(User user)
	{
		UserResponseDTO dto =new UserResponseDTO();
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setPhone(user.getPhone());
		
		return dto;
	}
}
