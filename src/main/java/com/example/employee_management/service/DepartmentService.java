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
import java.util.Set;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    public DepartmentResponseDTO  saveDepartment(DepartmentRequestDTO request) {
        Department department = DepartmentMapper.toEntity(request);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.toResponseDTO(savedDepartment);
    }
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    public Page<DepartmentResponseDTO> getDepartments(int page, int size ,String sortBy, String direction,String name) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("Page size must be between 1 and 100");
        }
        if(!Set.of("id","name","location").contains(sortBy)){
            throw new IllegalArgumentException("Invalid sort field");
        }
        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Direction must be asc or desc");
        }
        Pageable pageable = PageRequest.of(page, size,Sort.by(sortDirection, sortBy));
        Specification<Department> specification = (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("name")), name.trim().toLowerCase());
        };
        Page<Department> depts = departmentRepository.findAll(specification,pageable);

        return depts.map(department -> DepartmentMapper.toResponseDTO(department)
        );
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
