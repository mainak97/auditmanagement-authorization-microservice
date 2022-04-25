package com.auditManagement.authorization;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import com.auditManagement.authorization.controllers.AuthController;
import com.auditManagement.authorization.repository.UserRepo;
import com.auditManagement.authorization.security.JWTUtil;
import com.auditManagement.authorization.security.MyUserDetailsService;
import com.auth0.jwt.exceptions.JWTVerificationException;


@WebMvcTest(AuthController.class)
class AuditManagementJWTFilterTests {
	
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserRepo userRepo;
	
	@MockBean
	private JWTUtil jwtUtil;
	
	@MockBean
	private MyUserDetailsService myUserDetailsService;
	
	@MockBean
	private AuthenticationManager authManager;
	
	
    @Test
    public void checkBlankAuthHeader() throws Exception {
    	Mockito.when(myUserDetailsService.loadUserByUsername(null)).thenReturn(new org.springframework.security.core.userdetails.User(
        		"user",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        mvc.perform(get("/api/authjwt").servletPath("/api/authjwt").contentType("application/json")
        		.header("Authorization", ""))
          .andExpect(status().isBadRequest());
    }
    @Test
    public void checkAbsentAuthHeader() throws Exception {
    	Mockito.when(myUserDetailsService.loadUserByUsername(null)).thenReturn(new org.springframework.security.core.userdetails.User(
        		"user",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        mvc.perform(get("/api/authjwt").servletPath("/api/authjwt").contentType("application/json"))
          .andExpect(status().isBadRequest());
    }
    @Test
    public void checkAuthDoesNotStartWithBearerr() throws Exception {
    	Mockito.when(myUserDetailsService.loadUserByUsername(null)).thenReturn(new org.springframework.security.core.userdetails.User(
        		"user",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        mvc.perform(get("/api/authjwt").servletPath("/api/authjwt").contentType("application/json")
        		.header("Authorization", "simpletoken"))
          .andExpect(status().isBadRequest());
    }
    @Test
    public void jwtTokenBlank() throws Exception {
    	Mockito.when(myUserDetailsService.loadUserByUsername(null)).thenReturn(new org.springframework.security.core.userdetails.User(
        		"user",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        mvc.perform(get("/api/authjwt").servletPath("/api/authjwt").contentType("application/json")
        		.header("Authorization", "Bearer  "))
          .andExpect(status().isBadRequest());
    }
    @Test
    public void jwtTokenExpired() throws Exception {
    	Mockito.when(myUserDetailsService.loadUserByUsername(null)).thenThrow(new JWTVerificationException("Bad Credentials"));
        mvc.perform(get("/api/authjwt").servletPath("/api/authjwt").contentType("application/json")
        		.header("Authorization", "Bearer simpletoken"))
          .andExpect(status().isForbidden());
    }
}
