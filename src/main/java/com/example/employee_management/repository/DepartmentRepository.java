package com.example.employee_management.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.example.employee_management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DepartmentRepository extends JpaRepository<Department, Long>,JpaSpecificationExecutor<Department> {

}