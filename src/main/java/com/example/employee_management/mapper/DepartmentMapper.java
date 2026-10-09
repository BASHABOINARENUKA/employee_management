package com.example.employee_management.mapper;
import com.example.employee_management.entity.Department;
import com.example.employee_management.dto.DepartmentResponseDTO;
import com.example.employee_management.dto.DepartmentRequestDTO;

public class DepartmentMapper {

    public static Department toEntity(DepartmentRequestDTO request){
        Department department = new Department();
        department.setName(request.getName());
        department.setLocation(request.getLocation());
        return department;

    }
    public static DepartmentResponseDTO toResponseDTO(Department department) {
        DepartmentResponseDTO response = new DepartmentResponseDTO();
        response.setId(department.getId());
        response.setName(department.getName());
        response.setLocation(department.getLocation());
        return response;
    }
}
