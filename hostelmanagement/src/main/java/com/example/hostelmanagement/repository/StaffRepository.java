package com.example.hostelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hostelmanagement.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff,Long>{
    
}
