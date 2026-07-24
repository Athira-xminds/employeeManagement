package com.xminds.employeeManagement.dto;

public record EmployeeRequest(
        String employeeName,
        String department,
        double salary,
        String address,
        String phoneNumber,
        String dateOfBirth,
        Long departmentId,
        String email,
        String password,
        String role
) {}
