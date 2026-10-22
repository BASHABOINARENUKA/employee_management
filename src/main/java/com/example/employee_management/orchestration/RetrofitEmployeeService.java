package com.example.employee_management.orchestration;

import com.example.employee_management.client.RetrofitApi;
import com.example.employee_management.dto.EmployeeResponseDTO;
import com.example.employee_management.dto.EmployeeRequestDTO;
import com.example.employee_management.dto.EmployeeDepartmentResponseDTO;
import com.example.employee_management.dto.DepartmentDetailsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import java.util.List;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@Service
public class RetrofitEmployeeService {

    private final RetrofitApi retrofitApi;

    public RetrofitEmployeeService(RetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
    }

    public EmployeeResponseDTO getEmployeeById(Long id,String token) throws IOException {
        Call<EmployeeResponseDTO> call = retrofitApi.getEmployee(id,token);
        Response<EmployeeResponseDTO> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body();
        }

        if (response.code() == 404) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + id);
        }

        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Employee API failed with status: " + response.code());
    }
    public List<EmployeeResponseDTO> getAllEmployees(String token) throws IOException{
        Call<List<EmployeeResponseDTO>> call = retrofitApi.getEmployees(token);
        Response<List<EmployeeResponseDTO>> response = call.execute();
        if(response.isSuccessful() && response.body()!=null){
            return response.body();
        }
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"Employee API failed with status:" + response.code());
    }
    public EmployeeResponseDTO createEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO, String token) throws IOException{
        Call <EmployeeResponseDTO> call = retrofitApi.createEmployee(employeeRequestDTO,token);
        Response<EmployeeResponseDTO> response = call.execute();
        if(response.isSuccessful() && response.body()!=null){
            return response.body();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Employee Creation failed with Status:" + response.code());
    }
    public EmployeeResponseDTO updateEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO,Long id, String token) throws IOException{
        Call <EmployeeResponseDTO> call = retrofitApi.updateEmployee(employeeRequestDTO,id,token);
        Response<EmployeeResponseDTO> response = call.execute();
        if(response.isSuccessful() && response.body()!=null){
            return response.body();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Employee Creation failed with Status:" + response.code());
    }

    public void deleteEmployee(Long id, String token) throws IOException {
        Response<Void> response = retrofitApi.deleteEmployee(id, token).execute();
        if (!response.isSuccessful()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Employee API failed with status: " + response.code());
        }
    }

    public EmployeeDepartmentResponseDTO getEmployeeWithDepartment(Long employeeId, String token) throws IOException {
        Response<EmployeeResponseDTO> employeeResponse = retrofitApi.getEmployee(employeeId, token).execute();
        if (!employeeResponse.isSuccessful() || employeeResponse.body() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Employee API failed with status: " + employeeResponse.code());
        }
        EmployeeResponseDTO employee = employeeResponse.body();
        Long departmentId = employee.getDepartmentId();
        Response<DepartmentDetailsResponse> departmentResponse = retrofitApi.getDepartment(departmentId, token).execute();
        if (!departmentResponse.isSuccessful() || departmentResponse.body() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Department API failed with status: " + departmentResponse.code());
        }
        DepartmentDetailsResponse department = departmentResponse.body();
        return new EmployeeDepartmentResponseDTO(employee, department);
    }
}