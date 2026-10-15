
package com.example.employee_management.orchestration;
import com.example.employee_management.dto.EmployeeDepartmentSummaryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;
import java.io.IOException;

@RestController
@RequestMapping("/api/orchestration/resttemplate")
public class RestTemplateController {

    private final RestTemplateService orchestrationService;
    public RestTemplateController(RestTemplateService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    // GET: Employee and department details
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDepartmentSummaryDTO> getEmployeeAndDepartment(@PathVariable Long employeeId, @RequestHeader("Authorization") String authorization) {
        String token = extractToken(authorization);
        EmployeeDepartmentSummaryDTO response = orchestrationService.getEmployeeAndDepartment(employeeId, token);
        return ResponseEntity.ok(response);
    }

    // POST: Create employee
    @PostMapping("/employees")
    public ResponseEntity<JsonNode> createEmployee(@RequestBody JsonNode requestData, @RequestHeader("Authorization") String authorization) throws IOException {
        String token = extractToken(authorization);
        JsonNode response = orchestrationService.createEmployee(requestData, token);
        return ResponseEntity.ok(response);
    }

    // PUT: Update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<JsonNode> updateEmployee(@PathVariable Long id, @RequestBody JsonNode requestData, @RequestHeader("Authorization") String authorization) throws IOException {
        String token = extractToken(authorization);
        JsonNode response = orchestrationService.updateEmployee(id, requestData, token);
        return ResponseEntity.ok(response);
    }

    // DELETE: Delete employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id, @RequestHeader("Authorization") String authorization) throws IOException {
        String token = extractToken(authorization);
        orchestrationService.deleteEmployee(id, token);
        return ResponseEntity.noContent().build();
    }

    private String extractToken(String authorization) {
        return authorization.replaceFirst("(?i)^Bearer\\s+", "");
    }
}

