package com.xminds.employeeManagement.controller;


import com.xminds.employeeManagement.dto.EmployeeRequest;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import com.xminds.employeeManagement.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

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
}

