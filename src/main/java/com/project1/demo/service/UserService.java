package com.project1.demo.service;

import com.project1.demo.dto.RegisterRequestDTO;
import com.project1.demo.entity.User;

public interface UserService {
	
	User register(RegisterRequestDTO request);
}
