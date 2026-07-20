package com.xminds.employeeManagement.dto;

public record EmployeeResponse(
        Long employeeId,
        String employeeName,
        String department,
        double salary
) {}

