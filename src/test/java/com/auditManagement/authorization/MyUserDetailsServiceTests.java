package com.auditManagement.authorization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.auditManagement.authorization.entity.User;
import com.auditManagement.authorization.repository.UserRepo;
import com.auditManagement.authorization.security.JWTUtil;
import com.auditManagement.authorization.security.MyUserDetailsService;

@WebMvcTest(MyUserDetailsService.class)
class MyUserDetailsServiceTests {
	@Mock
	private UserRepo userRepo;
	
	@MockBean
	private JWTUtil jwtUtil;
	
	@MockBean
	private MyUserDetailsService myUserDetailsService;
	
	@MockBean
	private AuthenticationManager authManager;
	
	
	
	 @InjectMocks
	 private MyUserDetailsService userService;
	
	@Test
    void userNotFoundTest() throws Exception {
    	Mockito.when(userRepo.findByUsername("")).thenReturn(Optional.empty());
    	assertThrows(UsernameNotFoundException.class, () -> {
    		userService.loadUserByUsername("");
    	});
    }
	@Test
    void userFoundTest() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setUsername("test");
		user.setPassword("test");
    	Mockito.when(userRepo.findByUsername("test")).thenReturn(Optional.of(user));
    	assertThat(userService.loadUserByUsername("test")).isNotNull();
    }
}
