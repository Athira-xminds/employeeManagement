package com.xminds.employeeManagement.controller;
import com.xminds.employeeManagement.dto.EmployeeResponse;
import com.xminds.employeeManagement.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/employee/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        EmployeeService service = null;
        return service.findById(id);
    }

}