package com.xminds.employeeManagement.dto;

public record AuthRequest(
        String email,
        String password
) {}
