package com.auditManagement.authorization;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import com.auditManagement.authorization.controllers.AuthController;
import com.auditManagement.authorization.repository.UserRepo;
import com.auditManagement.authorization.security.JWTUtil;
import com.auditManagement.authorization.security.MyUserDetailsService;

@WebMvcTest(AuthController.class)
public class AuditManagementAuthorizationExceptionTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserRepo userRepo;
	
	@MockBean
	private JWTUtil jwtUtil;
	
	@MockBean
	private MyUserDetailsService myUserDetailsService;
	
	@Mock
	private AuthenticationManager authManager;
	
	@Test
    public void wrongCredentialsGetJAuthJwtTest() throws Exception {
    	Mockito.when(authManager.authenticate(null)).thenThrow(new BadCredentialsException("message"));
        mvc.perform(post("/api/getjwt").servletPath("/api/getjwt").contentType("application/json").content("{}").header("Authorization", "Bearer simpletoken"))
          .andExpect(status().isForbidden());
    }
}
