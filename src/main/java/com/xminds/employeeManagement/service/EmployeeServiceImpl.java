package com.xminds.employeeManagement.service;

import com.xminds.employeeManagement.dto.EmployeeRequest;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import com.xminds.employeeManagement.dto.ProjectResponse;
import com.xminds.employeeManagement.entity.Department;
import com.xminds.employeeManagement.entity.Employee;
import com.xminds.employeeManagement.entity.EmployeeProfile;
import com.xminds.employeeManagement.entity.Project;
import com.xminds.employeeManagement.exception.EmployeeNotFoundException;
import com.xminds.employeeManagement.exception.DepartmentNotFoundException;
import com.xminds.employeeManagement.exception.ProjectNotFoundException;
import com.xminds.employeeManagement.repository.DepartmentRepository;
import com.xminds.employeeManagement.repository.EmployeeRepository;
import com.xminds.employeeManagement.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository repository,
                               DepartmentRepository departmentRepository,
                               ProjectRepository projectRepository,
                               @org.springframework.context.annotation.Lazy PasswordEncoder passwordEncoder) { // Added @Lazy here
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with email: " + username));
    }

    @Override
    @Transactional
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

        employee.setEmail(request.email());
        employee.setPassword(passwordEncoder.encode(request.password()));
        employee.setRole(request.role() != null ? request.role() : "ROLE_USER");

        Employee savedEmployee = repository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @Override
    @Transactional
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

        employee.setEmail(request.email());
        employee.setPassword(passwordEncoder.encode(request.password()));
        employee.setRole(request.role() != null ? request.role() : "ROLE_USER");

        Employee savedEmployee = repository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));
        return mapToResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    @Transactional
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

        if (request.email() != null) employee.setEmail(request.email());
        if (request.password() != null) employee.setPassword(passwordEncoder.encode(request.password()));

        if (employee.getEmployeeProfile() != null) {
            employee.getEmployeeProfile().setAddress(request.address());
            employee.getEmployeeProfile().setPhoneNumber(request.phoneNumber());
            employee.getEmployeeProfile().setDateOfBirth(request.dateOfBirth());
        }
        Employee updated = repository.save(employee);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));
        repository.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getEmployeesBySalaryRange(Double minSalary, Double maxSalary) {
        return repository.findBySalaryBetween(minSalary, maxSalary).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> searchEmployeesByName(String searchText) {
        return repository.findByEmployeeNameContainingIgnoreCase(searchText).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getEmployeesByDeptAndMinSalary(Long deptId, Double minSalary) {
        return repository.findEmployeesByDeptAndMinSalary(deptId, minSalary).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getEmployeesByDepartmentName(String deptName) {
        return repository.findEmployeesByDepartmentName(deptName).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getEmployeesByDepartmentPaginated(Long departmentId, Pageable pageable) {
        return repository.findByDepartmentId(departmentId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getHighSalaryEmployeesPaginated(Double minSalary, Pageable pageable) {
        return repository.findHighSalaryEmployees(minSalary, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional
    public void assignProjectToEmployee(Long employeeId, Long projectId) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + employeeId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id " + projectId));

        employee.addProject(project);
        repository.save(employee);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        EmployeeProfile profile = employee.getEmployeeProfile();

        Set<ProjectResponse> projectDTOs = employee.getProjects().stream()
                .map(p -> new ProjectResponse(p.getProjectId(), p.getProjectName()))
                .collect(Collectors.toSet());

        return new EmployeeResponse(
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getDepartment() != null ? employee.getDepartment().getName() : null,
                employee.getSalary(),profile != null ? profile.getAddress() : null,
                profile != null ? profile.getPhoneNumber() : null,
                profile != null ? profile.getDateOfBirth() : null,
                employee.getDepartment() != null ? employee.getDepartment().getId() : null,
                projectDTOs);
    }
}
