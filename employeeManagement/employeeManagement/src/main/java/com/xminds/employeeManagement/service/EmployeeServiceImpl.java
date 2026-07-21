package com.xminds.employeeManagement.service;

import com.xminds.employeeManagement.dto.EmployeeRequest;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import com.xminds.employeeManagement.entity.Employee;
import com.xminds.employeeManagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeResponse save(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setEmployeeName(request.employeeName());
        employee.setDepartment(request.department());
        employee.setSalary(request.salary());

        Employee savedEmployee = repository.save(employee);
        return mapToResponse(savedEmployee);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }
}
