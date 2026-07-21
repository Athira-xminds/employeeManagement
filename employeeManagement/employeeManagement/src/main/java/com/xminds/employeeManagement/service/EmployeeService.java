package com.xminds.employeeManagement.service;



import com.xminds.employeeManagement.dto.EmployeeRequest;
import com.xminds.employeeManagement.dto.EmployeeResponse;

public interface EmployeeService {
    EmployeeResponse save(EmployeeRequest request);
}
