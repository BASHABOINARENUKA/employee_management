package com.example.employee_management.dto;

public class EmployeeDepartmentResponseDTO {
    private EmployeeResponseDTO employee;
    private DepartmentDetailsResponse department;

    public EmployeeDepartmentResponseDTO(EmployeeResponseDTO employee, DepartmentDetailsResponse department) {
        this.employee = employee;
        this.department = department;
    }

    public EmployeeResponseDTO getEmployee() {
        return employee;
    }

    public DepartmentDetailsResponse getDepartment() {
        return department;
    }
}
