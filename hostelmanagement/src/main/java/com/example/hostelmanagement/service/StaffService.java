package com.example.hostelmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.hostelmanagement.dto.MetaData;
import com.example.hostelmanagement.dto.StaffResponseDto;
import com.example.hostelmanagement.entity.Staff;
import com.example.hostelmanagement.exception.StaffNotFoundException;
import com.example.hostelmanagement.repository.StaffRepository;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public List<StaffResponseDto> getAllStaff() {
        return staffRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<StaffResponseDto> getStaffByRole(String role) {
        List<StaffResponseDto> staff = staffRepository.findAll().stream()
                .filter(s -> s.getRole().equalsIgnoreCase(role))
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        if (staff.isEmpty()) {
            throw new StaffNotFoundException("No staff found with role: " + role);
        }
        return staff;
    }

    public List<StaffResponseDto> getStaffByShift(String shift) {
        List<StaffResponseDto> staff = staffRepository.findAll().stream()
                .filter(s -> s.getShift().equalsIgnoreCase(shift))
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        if (staff.isEmpty()) {
            throw new StaffNotFoundException("No staff found with shift: " + shift);
        }
        return staff;
    }

    public List<StaffResponseDto> getStaffWithRooms() {
        return staffRepository.findAll().stream()
                .filter(staff -> staff.getManagedRooms() != null && !staff.getManagedRooms().isEmpty())
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<StaffResponseDto> getStaffWithoutRooms() {
        return staffRepository.findAll().stream()
                .filter(staff -> staff.getManagedRooms() == null || staff.getManagedRooms().isEmpty())
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Page<StaffResponseDto> getAllStaffPaginated(Pageable pageable) {
        return staffRepository.findAll(pageable).map(this::convertToDto);
    }

    public MetaData getMetaData(Pageable pageable) {
        Page<Staff> page = staffRepository.findAll(pageable);
        return new MetaData(page.getNumber(), page.getSize(), page.getTotalElements(), 
                           page.getTotalPages(), page.getSort().toString(), page.isFirst(), page.isLast());
    }

    public Page<StaffResponseDto> searchStaff(String name, String role, String shift, Pageable pageable) {
        return staffRepository.findAll(pageable).stream()
                .filter(staff -> (name == null || staff.getName().contains(name)) &&
                                (role == null || staff.getRole().equalsIgnoreCase(role)) &&
                                (shift == null || staff.getShift().equalsIgnoreCase(shift)))
                .collect(Collectors.toList())
                .stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(this::convertToDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), 
                    list -> new org.springframework.data.domain.PageImpl<>(list, pageable, staffRepository.count())));
    }

    public Page<StaffResponseDto> changeDefaultIndex(Pageable pageable) {
        return staffRepository.findAll(pageable).map(this::convertToDto);
    }

    private StaffResponseDto convertToDto(Staff staff) {
        List<String> managedRoomNumbers = (staff.getManagedRooms() != null) ? 
                staff.getManagedRooms().stream().map(room -> room.getRoomNumber()).collect(Collectors.toList()) : 
                new ArrayList<>();

        return new StaffResponseDto(
                staff.getId(),
                staff.getName(),
                staff.getEmail(),
                staff.getRole(),
                staff.getContactNumber(),
                staff.getShift(),
                managedRoomNumbers
        );
    }
}