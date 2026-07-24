package com.xminds.employeeManagement.controller;

import com.xminds.employeeManagement.dto.AuthRequest;
import com.xminds.employeeManagement.dto.AuthResponse;
import com.xminds.employeeManagement.dto.EmployeeRequest;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import com.xminds.employeeManagement.security.JwtUtils;
import com.xminds.employeeManagement.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager,
                          EmployeeService employeeService,
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeResponse> register(@RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.save(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login") 
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        final UserDetails userDetails = employeeService.loadUserByUsername(request.email());
        final String jwtToken = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
}
