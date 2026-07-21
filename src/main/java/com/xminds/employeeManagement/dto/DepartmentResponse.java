
package com.xminds.employeeManagement.dto;

import java.util.List;

public record DepartmentResponse(
        Long departmentId,
        String departmentName,
        List<EmployeeResponse> employees
) {}
