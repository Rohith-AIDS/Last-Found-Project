package com.project1.demo.dto;

public class AuthResponseDTO {
	private String accessToken;
	private String refreshToken;
	
	public AuthResponseDTO(String accessToken, String refreshToken)
	{
		this.setAccessToken(accessToken);
		this.refreshToken=refreshToken;
		
	}

	public String getAccessToken() {
		return accessToken;
	}
	
	public String getRefreshToken()
	{
		return refreshToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public void setRefreshToken(String refreshToken)
	{
		this.refreshToken = refreshToken;
	}
}
