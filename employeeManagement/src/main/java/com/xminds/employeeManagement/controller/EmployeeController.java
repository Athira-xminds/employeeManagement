package com.xminds.employeeManagement.controller;

import com.xminds.employeeManagement.dto.EmployeeRequest;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import com.xminds.employeeManagement.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;


import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping("/employee/create")
    public EmployeeResponse save(@RequestBody EmployeeRequest request) {
        return service.save(request);
    }

    @GetMapping("/employee/{id}")
    public EmployeeResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }
    @GetMapping("/employees")
    public List<EmployeeResponse> getAll() {
        return service.findAll();
    }
    @PutMapping("/employee/{id}")
    public EmployeeResponse update(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        return service.update(id, request);
    }
    @DeleteMapping("employee/{id}")
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


}
