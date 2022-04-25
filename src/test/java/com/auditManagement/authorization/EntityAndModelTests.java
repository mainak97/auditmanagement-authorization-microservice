package com.auditManagement.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.auditManagement.authorization.entity.User;
import com.auditManagement.authorization.models.ErrorResponse;
import com.auditManagement.authorization.models.LoginCredentials;

@SpringBootTest
class EntityAndModelTests {
	@Test
	void UserEntityTest() {
		User user = new User();
		user.setId(1L);
		user.setUsername("username");
		user.setPassword("password");
		assertThat(user.getId()).isEqualTo(1L);
		assertThat(user.getUsername()).isEqualTo("username");
		assertThat(user.getPassword()).isEqualTo("password");
		assertThat(user.toString()).hasToString("User(id=1, username=username, password=password)");
	}
	@Test
	void ErrorResponseTest() {
		ErrorResponse response = new ErrorResponse();
		response.setErrorCode(1000);
		response.setErrorMsg("error");
		assertThat(response.getErrorCode()).isEqualTo(1000);
		assertThat(response.getErrorMsg()).isEqualTo("error");
		assertThat(response.toString()).hasToString("ErrorResponse(errorCode=1000, errorMsg=error)");
	}
	@Test
	void LoginCredentialsTest() {
		LoginCredentials credentials = new LoginCredentials("","");
		credentials.setUsername("test");
		credentials.setPassword("test");
		assertThat(credentials.toString()).hasToString("LoginCredentials(username=test, password=test)");
	}
}
