package com.example.employee_management.config;
import com.example.employee_management.client.RetrofitApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Value("${services.employee.base-url}")
    private String employeeBaseUrl;

    @Bean
    public Retrofit retrofit() {
        Gson gson = new GsonBuilder().create();
        return new Retrofit.Builder().baseUrl(employeeBaseUrl).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    @Bean
    public RetrofitApi retrofitApi(Retrofit retrofit) {
        return retrofit.create(RetrofitApi.class);
    }
}
