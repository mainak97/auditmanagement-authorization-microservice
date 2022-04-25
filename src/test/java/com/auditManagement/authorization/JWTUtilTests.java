package com.auditManagement.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.auditManagement.authorization.security.JWTUtil;

@SpringBootTest
public class JWTUtilTests {
	@Autowired
	JWTUtil jwtUtil;
	
	@Test
	void generateTokenTest() {
		assertThat(jwtUtil.generateToken("username")).isNotNull();
	}
	@Test
	void validateTokenTest() {
		assertThat(jwtUtil.validateTokenAndRetrieveSubject(jwtUtil.generateToken("username"))).isNotNull();
	}
}
