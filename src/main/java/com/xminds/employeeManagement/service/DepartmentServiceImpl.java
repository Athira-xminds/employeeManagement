package com.xminds.employeeManagement.service;

import com.xminds.employeeManagement.dto.DepartmentRequest;
import com.xminds.employeeManagement.dto.DepartmentResponse;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import com.xminds.employeeManagement.entity.Department;
import com.xminds.employeeManagement.entity.Employee;
import com.xminds.employeeManagement.entity.EmployeeProfile;
import com.xminds.employeeManagement.repository.DepartmentRepository;
import com.xminds.employeeManagement.exception.DepartmentNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (departmentRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Department with name '" + request.name() + "' already exists.");
        }
        Department department = new Department();
        department.setName(request.name());
        Department saved = departmentRepository.save(department);
        return mapToDepartmentResponse(saved);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToDepartmentResponse)
                .toList();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id " + id));
        return mapToDepartmentResponse(department);
    }

    @Override
    public List<EmployeeResponse> getEmployeesByDepartmentId(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id " + departmentId));

        return department.getEmployees().stream()
                .map(this::mapToEmployeeResponse)
                .toList();
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        List<EmployeeResponse> employeeResponses = department.getEmployees().stream()
                .map(this::mapToEmployeeResponse)
                .toList();
        return new DepartmentResponse(department.getId(), department.getName(), employeeResponses);
    }


    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        EmployeeProfile profile = employee.getEmployeeProfile();
        return new EmployeeResponse(
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getDepartment() != null ? employee.getDepartment().getName() : null,
                employee.getSalary(),
                profile != null ? profile.getAddress() : null,
                profile != null ? profile.getPhoneNumber() : null,
                profile != null ? profile.getDateOfBirth() : null,
                employee.getDepartment() != null ? employee.getDepartment().getId() : null
        );
    }
}
