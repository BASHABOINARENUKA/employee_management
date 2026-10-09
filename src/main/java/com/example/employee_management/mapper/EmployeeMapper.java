package com.example.employee_management.mapper;
import com.example.employee_management.dto.EmployeeRequestDTO;
import com.example.employee_management.dto.EmployeeResponseDTO;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeRequestDTO request, Department department) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setSalary(request.getSalary());
        employee.setDesignation(request.getDesignation());
        employee.setDepartment(department);
        return employee;
    }

    public static EmployeeResponseDTO toResponseDTO(Employee employee) {

        EmployeeResponseDTO response = new EmployeeResponseDTO();

        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setPhoneNumber(employee.getPhoneNumber());
        response.setSalary(employee.getSalary());
        response.setDesignation(employee.getDesignation());
        response.setDepartmentId(employee.getDepartment().getId());
        response.setDepartmentName(employee.getDepartment().getName());
        return response;
    }
}
