package com.xminds.employeeManagement.service;

import com.xminds.employeeManagement.dto.ProjectRequest;
import com.xminds.employeeManagement.dto.ProjectResponse;
import com.xminds.employeeManagement.entity.Project;
import com.xminds.employeeManagement.entity.Employee;
import com.xminds.employeeManagement.exception.EmployeeNotFoundException;
import com.xminds.employeeManagement.exception.ProjectNotFoundException;
import com.xminds.employeeManagement.repository.EmployeeRepository;
import com.xminds.employeeManagement.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = new Project();
        project.setProjectName(request.projectName());
        Project saved = projectRepository.save(project);
        return new ProjectResponse(saved.getProjectId(), saved.getProjectName());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(project -> new ProjectResponse(project.getProjectId(), project.getProjectName()))
                .toList();
    }

    @Override
    @Transactional
    public void allocateEmployeeToProject(Long projectId, Long employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id " + projectId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + employeeId));

        project.addEmployee(employee);
        projectRepository.save(project);
    }
}
