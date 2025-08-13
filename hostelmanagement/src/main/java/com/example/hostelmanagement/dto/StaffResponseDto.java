package com.example.hostelmanagement.dto;

import java.util.List;

public class StaffResponseDto {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String contactNumber;
    private String shift;
    private List<String> managedRoomNumbers;

    public StaffResponseDto() {
    }

    public StaffResponseDto(Long id, String name, String email, String role, String contactNumber, String shift, List<String> managedRoomNumbers) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.contactNumber = contactNumber;
        this.shift = shift;
        this.managedRoomNumbers = managedRoomNumbers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public List<String> getManagedRoomNumbers() {
        return managedRoomNumbers;
    }

    public void setManagedRoomNumbers(List<String> managedRoomNumbers) {
        this.managedRoomNumbers = managedRoomNumbers;
    }
}