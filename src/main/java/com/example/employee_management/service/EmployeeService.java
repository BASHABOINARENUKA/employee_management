package com.example.employee_management.service;
import com.example.employee_management.dto.EmployeeRequestDTO;
import com.example.employee_management.dto.EmployeeResponseDTO;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.mapper.EmployeeMapper;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO request) {
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Department not found with id: " + request.getDepartmentId()));
        Employee employee = EmployeeMapper.toEntity(request, department);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toResponseDTO(savedEmployee);
    }
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return EmployeeMapper.toResponseDTO(employee);
    }
    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> responses = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeResponseDTO response = EmployeeMapper.toResponseDTO(employee);
            responses.add(response);
        }
        return responses;
    }
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Department not found with id: " + request.getDepartmentId()));
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setSalary(request.getSalary());
        employee.setDesignation(request.getDesignation());
        employee.setDepartment(department);
        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toResponseDTO(updatedEmployee);
    }
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    /*public EmployeeResponseDTO getEmployeeForCurrentUser(Long id) {
        // Find the requested employee
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Allow admins to access any employee
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return EmployeeMapper.toResponseDTO(employee);
        }
        // Compare the logged-in identity with the employee's email
        String loggedInEmail = authentication.getName();
        if (!loggedInEmail.equalsIgnoreCase(employee.getFirstName())) {
            throw new AccessDeniedException("You are not allowed to access this employee");
        }
        return EmployeeMapper.toResponseDTO(employee);
    }*/
}
