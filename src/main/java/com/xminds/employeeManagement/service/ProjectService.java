package com.xminds.employeeManagement.service;

import com.xminds.employeeManagement.dto.ProjectRequest;
import com.xminds.employeeManagement.dto.ProjectResponse;
import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest request);
    List<ProjectResponse> getAllProjects();
    void allocateEmployeeToProject(Long projectId, Long employeeId);
}
