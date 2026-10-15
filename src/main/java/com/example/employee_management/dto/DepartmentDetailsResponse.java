package com.example.employee_management.dto;

public class DepartmentDetailsResponse {
    private Long id;
    private String name;
    private String location;
    private String combined;
    public DepartmentDetailsResponse() {
    }
    public DepartmentDetailsResponse(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.combined = name + " " + location;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCombined() {
        return combined;
    }
}
