package com.project1.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project1.demo.dto.ChangePasswordDTO;
import com.project1.demo.dto.ItemResponseDTO;
import com.project1.demo.dto.RegisterRequestDTO;
import com.project1.demo.dto.UpdateProfileDTO;
import com.project1.demo.dto.UserResponseDTO;
import com.project1.demo.entity.User;
import com.project1.demo.mapper.UserMapper;

public interface UserService {
	
	User register(RegisterRequestDTO request);
	
	UserResponseDTO updateProfile(UpdateProfileDTO dto, String currentUsername);
	
	Page<ItemResponseDTO> getMyItems(String username, Pageable pageable);
	
	void changePassword(ChangePasswordDTO dto,String username);
}
