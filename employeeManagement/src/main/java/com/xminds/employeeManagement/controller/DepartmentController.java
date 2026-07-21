package com.xminds.employeeManagement.controller;

import com.xminds.employeeManagement.dto.DepartmentRequest;
import com.xminds.employeeManagement.dto.DepartmentResponse;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import com.xminds.employeeManagement.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse createDepartment(@RequestBody DepartmentRequest request) {
        return departmentService.createDepartment(request);
    }

    @GetMapping
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public DepartmentResponse getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @GetMapping("/{departmentId}/employees")
    public List<EmployeeResponse> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return departmentService.getEmployeesByDepartmentId(departmentId);
    }


}
