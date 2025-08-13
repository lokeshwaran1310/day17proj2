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
import com.example.hostelmanagement.dto.StudentResponseDto;
import com.example.hostelmanagement.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentResponseDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/department/{department}")
    public List<StudentResponseDto> getStudentsByDepartment(@PathVariable String department) {
        return studentService.getStudentsByDepartment(department);
    }

    @GetMapping("/year/{year}")
    public List<StudentResponseDto> getStudentsByYear(@PathVariable Integer year) {
        return studentService.getStudentsByYear(year);
    }

    @GetMapping("/without-room")
    public List<StudentResponseDto> getStudentsWithoutRoom() {
        return studentService.getStudentsWithoutRoom();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<StudentResponseDto>> getAllStudentsPaginated(Pageable pageable) {
        Page<StudentResponseDto> students = studentService.getAllStudentsPaginated(pageable);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/metadata")
    public ResponseEntity<MetaData> getMetaInfo(Pageable pageable) {
        return ResponseEntity.ok(studentService.getMetaData(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StudentResponseDto>> searchStudents(@RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) String department,
                                                                  @RequestParam(required = false) String rollNumber,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(studentService.searchStudents(name, department, rollNumber, pageable));
    }

    @GetMapping("/changedefaultindex")
    public ResponseEntity<Page<StudentResponseDto>> changeIndexStudents(@RequestParam(defaultValue = "1") int pageNumber,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return ResponseEntity.ok(studentService.changeDefaultIndex(pageable));
    }



    @GetMapping("/performance")
    public ResponseEntity<MetaData> getPerformanceMetadata(Pageable pageable) {
        long startTime = System.currentTimeMillis();
        MetaData metadata = studentService.getMetaData(pageable);
        long endTime = System.currentTimeMillis();
        metadata.setSort(metadata.getSort() + " | Query Time: " + (endTime - startTime) + "ms");
        return ResponseEntity.ok(metadata);
    }

    @GetMapping("/sorted-nulls")
    public ResponseEntity<Page<StudentResponseDto>> getStudentsSortedWithNulls(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("email").nullsLast()));
        Page<StudentResponseDto> students = studentService.getAllStudentsPaginated(pageable);
        return ResponseEntity.ok(students);
    }
}