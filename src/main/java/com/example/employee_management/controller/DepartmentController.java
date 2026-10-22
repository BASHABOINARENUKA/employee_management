package com.example.employee_management.controller;
import com.example.employee_management.dto.ApiResponse;
import com.example.employee_management.dto.DepartmentResponseDTO;
import com.example.employee_management.dto.DepartmentRequestDTO;
import com.example.employee_management.entity.Department;
import org.springframework.http.HttpStatus;
import com.example.employee_management.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.employee_management.dto.DepartmentDetailsResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.employee_management.dto.ApiResponse;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.URI;
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
        URI location = URI.create("/departments/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity <List<DepartmentResponseDTO>> getAllDepartments() {
        return ResponseEntity.ok(
                departmentService.getAllDepartments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentResponseDTO response =departmentService.getDepartmentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<DepartmentResponseDTO>> getDepartments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue="id") String sortBy,@RequestParam(defaultValue="asc") String direction,@RequestParam(required = false) String name) {
        return ResponseEntity.ok(departmentService.getDepartments(page, size,sortBy, direction,name));
    }
    /*@GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getDepartmentById(@PathVariable Long id){
        DepartmentResponseDTO response = departmentService.getDepartmentById(id);
        Map<String ,Object> map = Map.of("message","Department Fetched",
                "data",response);
        return ResponseEntity.ok(map);
    }*/
    /*@GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponseDTO>> getDepartmentById(@PathVariable Long id){
        DepartmentResponseDTO response = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(new ApiResponse<>("Department Fectched ",response));

    }*/
    /*@GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getDepartmentById(@PathVariable Long id){
        DepartmentResponseDTO response = departmentService.getDepartmentById(id);
        Map<String,Object> mp = new LinkedHashMap<>();
        mp.put("id",response.getId());
        mp.put("name",response.getName());
        return ResponseEntity.ok(mp);
    }*/

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