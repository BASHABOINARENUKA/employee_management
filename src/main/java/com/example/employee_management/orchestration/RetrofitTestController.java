package com.example.employee_management.orchestration;

import com.example.employee_management.dto.EmployeeDepartmentResponseDTO;
import com.example.employee_management.dto.EmployeeResponseDTO;
import com.example.employee_management.orchestration.RetrofitEmployeeService;
import org.springframework.web.bind.annotation.*;
import com.example.employee_management.dto.EmployeeRequestDTO;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.*;
@RestController
@RequestMapping("/test")
public class RetrofitTestController {

    private final RetrofitEmployeeService retrofitEmployeeService;

    public RetrofitTestController(RetrofitEmployeeService retrofitEmployeeService) {
        this.retrofitEmployeeService = retrofitEmployeeService;
    }

    @GetMapping("/employee/{id}")
    public Map<String,Object> getEmployee(@PathVariable Long id,@RequestHeader("Authorization") String token ) throws IOException {
        EmployeeResponseDTO  response=retrofitEmployeeService.getEmployeeById(id,token);
        Map<String,Object> mp = new HashMap<>();
        mp.put("employeeName",response.getFirstName());
        mp.put("employeeSurname",response.getLastName());
        mp.put("employeeEmail",response.getEmail());
        return mp;
    }
    @GetMapping("/employees")
    public List<EmployeeResponseDTO> getEmployees(@RequestHeader("Authorization")  String token) throws IOException{
        return retrofitEmployeeService.getAllEmployees(token);
    }
    @PostMapping("/employees")
    public EmployeeResponseDTO createEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO , @RequestHeader("Authorization") String token) throws IOException{
        return retrofitEmployeeService.createEmployee(employeeRequestDTO,token);
    }
    @PutMapping("/employee/{id}")
    public EmployeeResponseDTO updateEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO,@PathVariable Long id,@RequestHeader("Authorization") String token) throws IOException{
        return retrofitEmployeeService.updateEmployee(employeeRequestDTO,id,token);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id, @RequestHeader("Authorization") String token) throws IOException {
        retrofitEmployeeService.deleteEmployee(id, token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detailscombined/{id}")
    public EmployeeDepartmentResponseDTO getDetails(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) throws IOException {
        return retrofitEmployeeService.getEmployeeWithDepartment(id, token);
    }
}
