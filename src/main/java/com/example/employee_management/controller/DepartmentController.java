package com.example.employee_management.controller;
import com.example.employee_management.dto.DepartmentResponseDTO;
import com.example.employee_management.dto.DepartmentRequestDTO;
import org.springframework.http.HttpStatus;
import com.example.employee_management.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.employee_management.dto.DepartmentDetailsResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.*;
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @PostMapping
    public ResponseEntity <DepartmentResponseDTO> saveDepartment(@Valid @RequestBody DepartmentRequestDTO request) {
        DepartmentResponseDTO response =departmentService.saveDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity <List<DepartmentResponseDTO>> getAllDepartments() {
        return ResponseEntity.ok(
                departmentService.getAllDepartments()
        );
    }

    @GetMapping("/{id}")
    public DepartmentDetailsResponse getDepartmentById(@PathVariable Long id) {
        DepartmentResponseDTO response =departmentService.getDepartmentById(id);
        /*Map<String,Object> responseMap = new LinkedHashMap<>();
        responseMap.put("id",response.getId());
        responseMap.put("name",response.getName());
        responseMap.put("location",response.getLocation());
        responseMap.put("combined",response.getName()+" "+ response.getLocation());*/
        return new DepartmentDetailsResponse(response.getId(), response.getName(), response.getLocation());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequestDTO request) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/hello")
    public String hello(){
        return "hi";
    }
}