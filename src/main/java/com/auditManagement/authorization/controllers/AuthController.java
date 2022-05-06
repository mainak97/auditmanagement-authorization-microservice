package com.auditManagement.authorization.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auditManagement.authorization.models.LoginCredentials;
import com.auditManagement.authorization.repository.UserRepo;
import com.auditManagement.authorization.security.JWTUtil;

import io.swagger.annotations.ApiImplicitParam;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    @Autowired private UserRepo userRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;

    @PostMapping("/getjwt")
    public Map<String, Object> getJWT(@RequestBody LoginCredentials body){
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());
            authManager.authenticate(authInputToken);
            Map<String,Object> token = jwtUtil.generateToken(body.getUsername());
            Map<String, Object> map = new HashMap<>(token);
            map.put("username", body.getUsername());
            return map;
    }

    @GetMapping("/authjwt")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true,
    	allowEmptyValue = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public Map<String, Object> authJWT(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Collections.singletonMap("valid", userRepo.findByUsername(username).isPresent());
    }


}
