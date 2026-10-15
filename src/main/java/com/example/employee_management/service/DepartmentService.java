package com.example.employee_management.service;
import com.example.employee_management.dto.DepartmentRequestDTO;
import com.example.employee_management.dto.DepartmentResponseDTO;
import com.example.employee_management.entity.Department;
import com.example.employee_management.mapper.DepartmentMapper;
import com.example.employee_management.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.ArrayList;
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentResponseDTO  saveDepartment(DepartmentRequestDTO request) {
        Department department = DepartmentMapper.toEntity(request);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.toResponseDTO(savedDepartment);
    }
    public List<DepartmentResponseDTO> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDTO> responses = new ArrayList<>();
        for(Department department :departments){
            String name =department.getName() + " " + department.getLocation();
            DepartmentResponseDTO response = DepartmentMapper.toResponseDTO(department);
            response.setName(name);
            responses.add(response);
        }
        return responses;

    }

    public DepartmentResponseDTO getDepartmentById(Long id) {
        Department department= departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found with id: " + id));
        return DepartmentMapper.toResponseDTO(department);
    }

    public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO request) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Department not found with id: " + id));
        department.setName(request.getName());
        department.setLocation(request.getLocation());
        departmentRepository.save(department);
        return DepartmentMapper.toResponseDTO(department);
    }

    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Department not found with id: " + id));
        departmentRepository.delete(department);
    }
}
