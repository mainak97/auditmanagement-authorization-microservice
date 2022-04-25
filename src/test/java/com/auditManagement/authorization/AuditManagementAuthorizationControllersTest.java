package com.auditManagement.authorization;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

@WebMvcTest(AuthController.class)
class AuditManagementAuthorizationControllersTest {
	
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
    void getAuthJwtTest() throws Exception {
    	Mockito.when(myUserDetailsService.loadUserByUsername(null)).thenReturn(new org.springframework.security.core.userdetails.User(
        		"user",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        mvc.perform(get("/api/authjwt").servletPath("/api/authjwt").contentType("application/json")
        		.header("Authorization", "Bearer simpletoken"))
          .andExpect(status().isOk());
    }
    @Test
    void postJwtTest() throws Exception {
    	Mockito.when(authManager.authenticate(null)).thenReturn(null);
        mvc.perform(post("/api/getjwt").servletPath("/api/getjwt").contentType("application/json").content("{}")
        		.header("Authorization", "Bearer simpletoken"))
          .andExpect(status().isOk());
    }
}
