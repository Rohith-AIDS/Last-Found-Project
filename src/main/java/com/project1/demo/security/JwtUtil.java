package com.project1.demo.security;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final String SECRET="mysecretkeymysecretkeymysecretkey";
	
	public String generateToken(UserDetails userDetails)
	{
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.claim("roles", userDetails.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String generateRefreshToken(UserDetails userDetails) {
	    return Jwts.builder()
	            .setSubject(userDetails.getUsername())
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days
	            .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
	            .compact();
	}
	
	public String extractUserName(String token)
	{
		return Jwts.parserBuilder()
				.setSigningKey(SECRET.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean validateToken(String token)
	{
		try {
			Jwts.parserBuilder()
			.setSigningKey(SECRET.getBytes())
			.build()
			.parseClaimsJws(token);
			return true;
		}
		
		catch(Exception e)
		{
			return false;
		}
	}
}
