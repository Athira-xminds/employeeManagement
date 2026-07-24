package com.xminds.employeeManagement.service;

import com.xminds.employeeManagement.dto.EmployeeRequest;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService; 

import java.util.List;


public interface EmployeeService extends UserDetailsService {

    EmployeeResponse save(EmployeeRequest request);
    EmployeeResponse findById(Long id);
    List<EmployeeResponse> findAll();
    EmployeeResponse update(Long id, EmployeeRequest request);
    void delete(Long id);
    EmployeeResponse createEmployee(Long departmentId, EmployeeRequest request);
    List<EmployeeResponse> getEmployeesBySalaryRange(Double minSalary, Double maxSalary);
    List<EmployeeResponse> searchEmployeesByName(String searchText);
    List<EmployeeResponse> getEmployeesByDeptAndMinSalary(Long deptId, Double minSalary);
    List<EmployeeResponse> getEmployeesByDepartmentName(String deptName);
    Page<EmployeeResponse> getEmployeesByDepartmentPaginated(Long departmentId, Pageable pageable);
    Page<EmployeeResponse> getHighSalaryEmployeesPaginated(Double minSalary, Pageable pageable);
    void assignProjectToEmployee(Long employeeId, Long projectId);
}
