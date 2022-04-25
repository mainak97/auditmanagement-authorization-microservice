package com.auditManagement.authorization.security;

import com.auditManagement.authorization.exception.AuthHeaderNotFoundException;
import com.auditManagement.authorization.models.ErrorResponse;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired private MyUserDetailsService userDetailsService;
    @Autowired private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        logger.info(request.getServletPath());
        if(!request.getServletPath().equals("/api/authjwt")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
        	if(authHeaderChecker(authHeader)){
                String jwt = authHeader.substring(7);
                String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
                if(SecurityContextHolder.getContext().getAuthentication() == null){
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                	filterChain.doFilter(request, response);
                } else {
            	throw new AuthHeaderNotFoundException();
            }
        } catch(AuthHeaderNotFoundException e) {
        	ErrorResponse errorResponse = new ErrorResponse(8005,"Authentication header not found");
        	response.setStatus(400);
        	ObjectMapper mapper = new ObjectMapper();
        	String json = mapper.writeValueAsString(errorResponse);
        	response.setContentType("application/json");
            response.getWriter().write(json);
        } catch(JWTVerificationException e) {
        	ErrorResponse errorResponse = new ErrorResponse(8005,"JWT Token is invalid or has expired");
        	response.setStatus(403);
        	ObjectMapper mapper = new ObjectMapper();
        	String json = mapper.writeValueAsString(errorResponse);
        	response.setContentType("application/json");
            response.getWriter().write(json);
        }
    }
    static boolean authHeaderChecker(String authHeader) {
    	if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
    		String jwt = authHeader.substring(7);
            if(!jwt.isBlank()) {
            	return true;
            }
    	}
    	return false;
    }
}
