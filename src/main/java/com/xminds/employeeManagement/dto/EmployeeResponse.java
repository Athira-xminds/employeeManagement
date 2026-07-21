package com.xminds.employeeManagement.dto;

public record EmployeeResponse(
        Long employeeId,
        String employeeName,
        String department,
        double salary,
        String address,
        String phoneNumber,
        String dateOfBirth,
        Long departmentId
) {}

