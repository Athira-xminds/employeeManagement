package com.xminds.employeeManagement.repository;

import com.xminds.employeeManagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> findEmployeesWithSalaryGreaterThan(@Param("salary") Double salary);

    List<Employee> findBySalaryBetween(Double minSalary, Double maxSalary);

    List<Employee> findByEmployeeNameContainingIgnoreCase(String searchText);

    @Query("SELECT e FROM Employee e WHERE e.department.id = :deptId AND e.salary > :minSalary")
    List<Employee> findEmployeesByDeptAndMinSalary(@Param("deptId") Long deptId,
                                                   @Param("minSalary") Double minSalary);
    @Query("SELECT e FROM Employee e JOIN e.department d WHERE d.name = :deptName")
    List<Employee> findEmployeesByDepartmentName(@Param("deptName") String deptName);

    @Query("SELECT e FROM Employee e WHERE e.salary > :minSalary")
    Page<Employee> findHighSalaryEmployees(@Param("minSalary") Double minSalary, Pageable pageable);
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

}
