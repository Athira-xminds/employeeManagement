package com.xminds.employeeManagement.service;

import com.xminds.employeeManagement.dto.EmployeeRequest;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import com.xminds.employeeManagement.entity.Department;
import com.xminds.employeeManagement.entity.Employee;
import com.xminds.employeeManagement.entity.EmployeeProfile;
import com.xminds.employeeManagement.exception.EmployeeNotFoundException;
import com.xminds.employeeManagement.exception.DepartmentNotFoundException;
import com.xminds.employeeManagement.repository.DepartmentRepository;
import com.xminds.employeeManagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository repository, DepartmentRepository departmentRepository) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public EmployeeResponse createEmployee(Long departmentId, EmployeeRequest request) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id " + departmentId));

        EmployeeProfile profile = new EmployeeProfile();
        profile.setAddress(request.address());
        profile.setPhoneNumber(request.phoneNumber());
        profile.setDateOfBirth(request.dateOfBirth());

        Employee employee = new Employee();
        employee.setEmployeeName(request.employeeName());
        employee.setSalary(request.salary());
        employee.setEmployeeProfile(profile);
        employee.setDepartment(department);

        Employee savedEmployee = repository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse save(EmployeeRequest request) {
        Department department = null;
        if (request.departmentId() != null) {
            department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id " + request.departmentId()));
        }

        EmployeeProfile profile = new EmployeeProfile();
        profile.setAddress(request.address());
        profile.setPhoneNumber(request.phoneNumber());
        profile.setDateOfBirth(request.dateOfBirth());

        Employee employee = new Employee();
        employee.setEmployeeName(request.employeeName());
        employee.setSalary(request.salary());
        employee.setEmployeeProfile(profile);
        employee.setDepartment(department);

        Employee savedEmployee = repository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse findById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));
        return mapToResponse(employee);
    }

    @Override
    public List<EmployeeResponse> findAll() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));

        if (request.departmentId() != null) {
            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id " + request.departmentId()));
            employee.setDepartment(department);
        } else {
            employee.setDepartment(null);
        }

        employee.setEmployeeName(request.employeeName());
        employee.setSalary(request.salary());

        if (employee.getEmployeeProfile() != null) {
            employee.getEmployeeProfile().setAddress(request.address());
            employee.getEmployeeProfile().setPhoneNumber(request.phoneNumber());
            employee.getEmployeeProfile().setDateOfBirth(request.dateOfBirth());
        }

        Employee updated = repository.save(employee);
        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));
        repository.delete(employee);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
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
    @Override
    public List<EmployeeResponse> getEmployeesBySalaryRange(Double minSalary, Double maxSalary) {
        return repository.findBySalaryBetween(minSalary, maxSalary).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> searchEmployeesByName(String searchText) {
        return repository.findByEmployeeNameContainingIgnoreCase(searchText).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeesByDeptAndMinSalary(Long deptId, Double minSalary) {
        return repository.findEmployeesByDeptAndMinSalary(deptId, minSalary).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeesByDepartmentName(String deptName) {
        return repository.findEmployeesByDepartmentName(deptName).stream()
                .map(this::mapToResponse)
                .toList();
    }


}
