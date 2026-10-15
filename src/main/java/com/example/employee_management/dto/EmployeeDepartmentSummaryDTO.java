package com.example.employee_management.dto;

public class EmployeeDepartmentSummaryDTO {

    private String employeeName;
    private String employeeEmail;
    private String departmentName;
    private String departmentLocation;

    public EmployeeDepartmentSummaryDTO(
            String employeeName,
            String employeeEmail,
            String departmentName,
            String departmentLocation) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.departmentName = departmentName;
        this.departmentLocation = departmentLocation;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getDepartmentLocation() {
        return departmentLocation;
    }
}