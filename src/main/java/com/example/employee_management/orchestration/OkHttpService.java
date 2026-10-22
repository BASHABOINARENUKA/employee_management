package com.example.employee_management.orchestration;
import com.example.employee_management.dto.*;
import retrofit2.http.Header;
import tools.jackson.databind.JsonNode;
import com.example.employee_management.client.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class OkHttpService {

    private final ApiClient apiClient;

    @Value("${services.employee.base-url}")
    private String employeeBaseUrl;

    @Value("${services.department.base-url}")
    private String departmentBaseUrl;

    @Value("${services.employee.get-by-id-path}")
    private String employeePath;

    @Value("${services.department.get-by-id-path}")
    private String departmentPath;

    public OkHttpService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public EmployeeDepartmentSummaryDTO getEmployeeAndDepartment(Long employeeId,String token) throws IOException {

        String employeeUrl = employeeBaseUrl + employeePath.replace("{id}", employeeId.toString());
        EmployeeResponseDTO employee = apiClient.get(employeeUrl, EmployeeResponseDTO.class,token);
        Long departmentId = employee.getDepartmentId();
        if (departmentId == null) {
            throw new IllegalStateException("Employee has no department assigned");
        }

        String departmentUrl = departmentBaseUrl + departmentPath.replace("{id}", departmentId.toString());
        DepartmentDetailsResponse department = apiClient.get(departmentUrl, DepartmentDetailsResponse.class,token);
        return new EmployeeDepartmentSummaryDTO(employee.getFirstName(), employee.getEmail(),department.getName(),department.getLocation());
    }

    public JsonNode createEmployee(JsonNode requestData, String token) throws IOException {
        String url = employeeBaseUrl + "/employees";
        return apiClient.post(url, requestData, JsonNode.class, token);
    }

    public void deleteEmployee(Long id, String token) throws IOException {
        String url = employeeBaseUrl + "/employees/" + id;
        apiClient.delete(url, token);
    }

    public JsonNode updateEmployee(Long id, JsonNode requestData, String token) throws IOException {
        String url = employeeBaseUrl + "/employees/" + id;
        return apiClient.put(url, requestData, JsonNode.class, token);
    }
    public DepartmentResponseDTO createDepartement(DepartmentRequestDTO request, String token) throws IOException {
        String url = departmentBaseUrl + "/departments";
        return apiClient.post(url, request, DepartmentResponseDTO.class,token);
    }

}
