package com.example.employee_management.dto;
import jakarta.validation.constraints.NotBlank;

public class DepartmentRequestDTO {

    @NotBlank(message="Name cannot be Empty")
    private String name;
    @NotBlank(message="Location is required")
    private String location;
    public DepartmentRequestDTO() {
    }
    public DepartmentRequestDTO(String name, String location) {
        this.name=name;
        this.location=location;
    }

        public String getName(){
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getLocation() {
            return location;
        }
        public void setLocation(String location) {
            this.location = location;
    }

}
