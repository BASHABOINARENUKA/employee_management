
package com.example.employee_management.orchestration;
import com.example.employee_management.client.RestTemplateClient;
import com.example.employee_management.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import java.io.IOException;

@Service
public class RestTemplateService {

    private final RestTemplateClient restTemplateClient;
    @Value("${services.employee.base-url}")
    private String employeeBaseUrl;
    @Value("${services.department.base-url}")
    private String departmentBaseUrl;
    public RestTemplateService(RestTemplateClient restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
    }

    // GET: Fetch employee and department details
    public EmployeeDepartmentSummaryDTO getEmployeeAndDepartment(Long employeeId, String token) {
        EmployeeResponseDTO employee = restTemplateClient.get(employeeBaseUrl + "/employees/" + employeeId, EmployeeResponseDTO.class, token);
        if (employee == null) {
            throw new IllegalStateException("Employee API returned an empty response");
        }
        DepartmentDetailsResponse department = restTemplateClient.get(departmentBaseUrl + "/departments/" + employee.getDepartmentId(), DepartmentDetailsResponse.class, token);
        if (department == null) {
            throw new IllegalStateException("Department API returned an empty response");
        }
        return new EmployeeDepartmentSummaryDTO(employee.getFirstName(),employee.getEmail(), department.getName(),department.getLocation());
    }

    // POST: Create employee
    public JsonNode createEmployee(JsonNode requestData, String token) throws IOException {
        String url = employeeBaseUrl + "/employees";
        return restTemplateClient.post(url, requestData, JsonNode.class, token);
    }

    // PUT: Update employee
    public JsonNode updateEmployee(Long id, JsonNode requestData, String token) throws IOException {
        String url = employeeBaseUrl + "/employees/" + id;
        return restTemplateClient.put(url, requestData, JsonNode.class, token);
    }

    // DELETE: Delete employee
    public void deleteEmployee(Long id, String token) throws IOException {
        String url = employeeBaseUrl + "/employees/" + id;
        restTemplateClient.delete(url, token);
    }
}

