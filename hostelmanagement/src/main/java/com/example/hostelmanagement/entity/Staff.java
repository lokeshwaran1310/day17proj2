package com.example.hostelmanagement.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String email;
    private String role;
    private String contactNumber;
    private String shift;
    
    @OneToMany(mappedBy = "managedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> managedRooms;

    public Staff() {
        
    }

    public Staff(String name, String email, String role, String contactNumber, String shift) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.contactNumber = contactNumber;
        this.shift = shift;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public List<Room> getManagedRooms() {
        return managedRooms;
    }

    public void setManagedRooms(List<Room> managedRooms) {
        this.managedRooms = managedRooms;
    }
}
