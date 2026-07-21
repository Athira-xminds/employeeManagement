package com.xminds.employeeManagement.service;



import com.xminds.employeeManagement.dto.EmployeeRequest;
import com.xminds.employeeManagement.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse save(EmployeeRequest request);
    EmployeeResponse findById(Long id);
    List<EmployeeResponse> findAll();
    EmployeeResponse update(Long id, EmployeeRequest request);
    void delete(Long id);
    EmployeeResponse createEmployee(Long departmentId, EmployeeRequest request);

}


