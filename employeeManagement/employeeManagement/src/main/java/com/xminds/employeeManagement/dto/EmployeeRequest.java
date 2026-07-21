package com.xminds.employeeManagement.dto;

public record EmployeeRequest(
        String employeeName,
        String department,
        double salary
) {}
