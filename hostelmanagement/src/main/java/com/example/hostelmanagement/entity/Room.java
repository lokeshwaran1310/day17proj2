package com.example.hostelmanagement.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;
    
    @Column(unique = true)
    private String roomNumber;
    
    private String roomType;
    private String status;
    private Integer capacity;
    private Integer floor;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff managedBy;

    public Room() {
        
    }

    public Room(String roomNumber, String roomType, String status, Integer capacity, Integer floor) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = status;
        this.capacity = capacity;
        this.floor = floor;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }



    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Staff getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(Staff managedBy) {
        this.managedBy = managedBy;
    }


}
