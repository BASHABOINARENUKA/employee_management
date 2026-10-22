package com.example.employee_management.orchestration;
import com.example.employee_management.dto.DepartmentRequestDTO;
import com.example.employee_management.dto.DepartmentResponseDTO;
import tools.jackson.databind.JsonNode;
import com.example.employee_management.dto.EmployeeDepartmentResponseDTO;
import com.example.employee_management.dto.EmployeeDepartmentSummaryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/orchestration/okhttp")
public class OkHttpController {

    private final OkHttpService okHttpService;

    public OkHttpController(OkHttpService okHttpService) {
        this.okHttpService = okHttpService;
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Map<String,Object>> getEmployeeAndDepartment(@PathVariable Long employeeId, @RequestHeader("Authorization") String authorization) throws IOException {
        String token = authorization.replaceFirst("(?i)^Bearer\\s+", "");
        EmployeeDepartmentSummaryDTO response =okHttpService.getEmployeeAndDepartment(employeeId, token);
        Map<String,Object> mp = new LinkedHashMap<>();
        mp.put("employeeName",response.getEmployeeName());
        mp.put("departmentName",response.getDepartmentName());
        return ResponseEntity.ok(mp);

    }

    @PostMapping("/employees")
    public ResponseEntity<JsonNode> createEmployee(@RequestBody JsonNode requestData, @RequestHeader("Authorization") String authorization) throws IOException {
        String token = authorization.replaceFirst("(?i)^Bearer\\s+", "");
        return ResponseEntity.ok(okHttpService.createEmployee(requestData, token));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id, @RequestHeader("Authorization") String authorization) throws IOException {
        String token = authorization.replaceFirst("(?i)^Bearer\\s+", "");
        okHttpService.deleteEmployee(id, token);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<JsonNode> updateEmployee(@PathVariable Long id, @RequestBody JsonNode requestData, @RequestHeader("Authorization") String authorization) throws IOException {
        String token = authorization.replaceFirst("(?i)^Bearer\\s+", "");
        JsonNode response = okHttpService.updateEmployee(id, requestData, token);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/departments")
    public ResponseEntity<DepartmentResponseDTO> createDepartemnt(@RequestBody DepartmentRequestDTO requestData, @RequestHeader("Authorization") String authorization) throws IOException{
        String token = authorization.replaceFirst("(?i)^Bearer\\s+","");
        DepartmentResponseDTO response = okHttpService.createDepartement(requestData,token);
        return ResponseEntity.ok(response);
    }

}
