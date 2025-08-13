package com.example.hostelmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hostelmanagement.dto.MetaData;
import com.example.hostelmanagement.dto.StaffResponseDto;
import com.example.hostelmanagement.service.StaffService;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping
    public List<StaffResponseDto> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping("/role/{role}")
    public List<StaffResponseDto> getStaffByRole(@PathVariable String role) {
        return staffService.getStaffByRole(role);
    }

    @GetMapping("/shift/{shift}")
    public List<StaffResponseDto> getStaffByShift(@PathVariable String shift) {
        return staffService.getStaffByShift(shift);
    }

    @GetMapping("/with-rooms")
    public List<StaffResponseDto> getStaffWithRooms() {
        return staffService.getStaffWithRooms();
    }

    @GetMapping("/without-rooms")
    public List<StaffResponseDto> getStaffWithoutRooms() {
        return staffService.getStaffWithoutRooms();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<StaffResponseDto>> getAllStaffPaginated(Pageable pageable) {
        Page<StaffResponseDto> staff = staffService.getAllStaffPaginated(pageable);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/metadata")
    public ResponseEntity<MetaData> getMetaInfo(Pageable pageable) {
        return ResponseEntity.ok(staffService.getMetaData(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StaffResponseDto>> searchStaff(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String role,
                                                             @RequestParam(required = false) String shift,
                                                             Pageable pageable) {
        return ResponseEntity.ok(staffService.searchStaff(name, role, shift, pageable));
    }

    @GetMapping("/changedefaultindex")
    public ResponseEntity<Page<StaffResponseDto>> changeIndexStaff(@RequestParam(defaultValue = "1") int pageNumber,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return ResponseEntity.ok(staffService.changeDefaultIndex(pageable));
    }

    @GetMapping("/performance")
    public ResponseEntity<MetaData> getPerformanceMetadata(Pageable pageable) {
        long startTime = System.currentTimeMillis();
        MetaData metadata = staffService.getMetaData(pageable);
        long endTime = System.currentTimeMillis();
        metadata.setSort(metadata.getSort() + " | Query Time: " + (endTime - startTime) + "ms");
        return ResponseEntity.ok(metadata);
    }

    @GetMapping("/sorted-nulls")
    public ResponseEntity<Page<StaffResponseDto>> getStaffSortedWithNulls(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("shift").nullsLast()));
        Page<StaffResponseDto> staff = staffService.getAllStaffPaginated(pageable);
        return ResponseEntity.ok(staff);
    }
}