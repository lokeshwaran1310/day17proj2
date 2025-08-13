package com.example.hostelmanagement.dto;

import java.util.List;

public class RoomResponseDto {

    private Long roomId;
    private String roomNumber;
    private String roomType;
    private String status;
    private Integer capacity;
    private Integer floor;
    private String managedByName;
    private Integer studentCount;
    private List<String> studentNames;

    public RoomResponseDto() {
    }

    public RoomResponseDto(Long roomId, String roomNumber, String roomType, String status, Integer capacity, Integer floor, String managedByName, Integer studentCount, List<String> studentNames) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = status;
        this.capacity = capacity;
        this.floor = floor;
        this.managedByName = managedByName;
        this.studentCount = studentCount;
        this.studentNames = studentNames;
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

    public String getManagedByName() {
        return managedByName;
    }

    public void setManagedByName(String managedByName) {
        this.managedByName = managedByName;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
    }
}