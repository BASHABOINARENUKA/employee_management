package com.example.employee_management.client;
import com.example.employee_management.dto.DepartmentDetailsResponse;
import com.example.employee_management.dto.EmployeeRequestDTO;
import com.example.employee_management.dto.EmployeeResponseDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
public interface RetrofitApi {

    @GET("employees/{id}")
    Call<EmployeeResponseDTO> getEmployee(@Path("id") Long id,@Header("Authorization") String token);
    @GET("employees")
    Call<List<EmployeeResponseDTO>> getEmployees (@Header("Authorization") String token);
    @POST("employees")
    Call <EmployeeResponseDTO> createEmployee (@Body EmployeeRequestDTO employeeRequestDTO , @Header("Authorization") String token);
   @PUT("employees/{id}")
    Call <EmployeeResponseDTO> updateEmployee (@Body EmployeeRequestDTO employeeRequestDTO , @Path("id") Long id,@Header("Authorization") String token);
    @DELETE("employees/{id}")
    Call <Void> deleteEmployee ( @Path("id") Long id,@Header("Authorization") String token);
    @GET("departments/{id}")
    Call<DepartmentDetailsResponse> getDepartment(@Path("id") Long id, @Header("Authorization") String token);

}