package com.xminds.employeeManagement.service;

import com.xminds.employeeManagement.dto.DepartmentRequest;
import com.xminds.employeeManagement.dto.DepartmentResponse;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import java.util.List;

public interface DepartmentService {
    DepartmentResponse createDepartment(DepartmentRequest request);
    List<DepartmentResponse> getAllDepartments();
    DepartmentResponse getDepartmentById(Long id);
    List<EmployeeResponse> getEmployeesByDepartmentId(Long departmentId);
}
