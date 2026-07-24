package com.xminds.employeeManagement.controller;

import com.xminds.employeeManagement.dto.EmployeeRequest;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import com.xminds.employeeManagement.service.EmployeeService;
import com.xminds.employeeManagement.service.ProjectService; // Injected for allocation logic
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService service;
    private final ProjectService projectService;

    public EmployeeController(EmployeeService service, ProjectService projectService) {
        this.service = service;
        this.projectService = projectService;
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse save(@RequestBody EmployeeRequest request) {
        return service.save(request);
    }

    @GetMapping("/employees/{id}")
    public EmployeeResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/employees")
    public List<EmployeeResponse> getAll() {
        return service.findAll();
    }

    @PutMapping("/employees/{id}")
    public EmployeeResponse update(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/employees/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Employee deleted successfully";
    }

    @PostMapping("/departments/{departmentId}/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(
            @PathVariable Long departmentId,
            @RequestBody EmployeeRequest request) {
        return service.createEmployee(departmentId, request);
    }

    @GetMapping("/employees/salary-range")
    public List<EmployeeResponse> getEmployeesBySalaryRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        return service.getEmployeesBySalaryRange(min, max);
    }

    @GetMapping("/employees/search")
    public List<EmployeeResponse> searchEmployeesByName(@RequestParam String name) {
        return service.searchEmployeesByName(name);
    }

    @GetMapping("/employees/filter")
    public List<EmployeeResponse> getEmployeesByDeptAndMinSalary(
            @RequestParam Long deptId,
            @RequestParam Double minSalary) {
        return service.getEmployeesByDeptAndMinSalary(deptId, minSalary);
    }

    @GetMapping("/employees/by-department")
    public List<EmployeeResponse> getEmployeesByDepartmentName(@RequestParam String deptName) {
        return service.getEmployeesByDepartmentName(deptName);
    }

    @GetMapping("/employees/department/{departmentId}/page")
    public Page<EmployeeResponse> getEmployeesByDeptPaginated(
            @PathVariable Long departmentId,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return service.getEmployeesByDepartmentPaginated(departmentId, pageable);
    }

    @GetMapping("/employees/high-salary/page")
    public Page<EmployeeResponse> getHighSalaryEmployeesPaginated(
            @RequestParam Double minSalary,
            @PageableDefault(page = 0, size = 5, sort = "salary") Pageable pageable) {
        return service.getHighSalaryEmployeesPaginated(minSalary, pageable);
    }

    @PostMapping("/employees/{employeeId}/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public String assignProjectToEmployee(@PathVariable Long employeeId, @PathVariable Long projectId) {
        service.assignProjectToEmployee(employeeId, projectId);
        return "Project successfully assigned to Employee.";
    }

    @PostMapping("/projects/{projectId}/employees/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public String allocateEmployeeToProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        projectService.allocateEmployeeToProject(projectId, employeeId);
        return "Employee successfully allocated to Project.";
    }
}
