package com.example.employee_management.service;
import com.example.employee_management.dto.EmployeeRequestDTO;
import com.example.employee_management.dto.EmployeeResponseDTO;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.DepartmentNotFoundException;
import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.mapper.EmployeeMapper;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO request) {
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + request.getDepartmentId()));
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
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + request.getDepartmentId()));
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
}
