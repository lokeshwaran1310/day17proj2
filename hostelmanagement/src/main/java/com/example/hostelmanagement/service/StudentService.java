package com.example.hostelmanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.hostelmanagement.dto.MetaData;
import com.example.hostelmanagement.dto.StudentResponseDto;
import com.example.hostelmanagement.entity.Student;
import com.example.hostelmanagement.exception.StudentNotFoundException;
import com.example.hostelmanagement.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<StudentResponseDto> getStudentsByDepartment(String department) {
        List<StudentResponseDto> students = studentRepository.findAll().stream()
                .filter(student -> student.getDepartment().equalsIgnoreCase(department))
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        if (students.isEmpty()) {
            throw new StudentNotFoundException("No students found in department: " + department);
        }
        return students;
    }

    public List<StudentResponseDto> getStudentsByYear(Integer year) {
        List<StudentResponseDto> students = studentRepository.findAll().stream()
                .filter(student -> student.getYear().equals(year))
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        if (students.isEmpty()) {
            throw new StudentNotFoundException("No students found in year: " + year);
        }
        return students;
    }

    public List<StudentResponseDto> getStudentsWithoutRoom() {
        return studentRepository.findAll().stream()
                .filter(student -> student.getRoom() == null)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Page<StudentResponseDto> getAllStudentsPaginated(Pageable pageable) {
        Page<StudentResponseDto> students = studentRepository.findAll(pageable).map(this::convertToDto);
        if (students.isEmpty()) {
            throw new StudentNotFoundException("No students found for the requested page");
        }
        return students;
    }

    public MetaData getMetaData(Pageable pageable) {
        Page<Student> page = studentRepository.findAll(pageable);
        return new MetaData(page.getNumber(), page.getSize(), page.getTotalElements(), 
                           page.getTotalPages(), page.getSort().toString(), page.isFirst(), page.isLast());
    }

    public Page<StudentResponseDto> searchStudents(String name, String department, String rollNumber, Pageable pageable) {
        Page<StudentResponseDto> results = studentRepository.findAll(pageable).stream()
                .filter(student -> (name == null || student.getName().contains(name)) &&
                                  (department == null || student.getDepartment().equalsIgnoreCase(department)) &&
                                  (rollNumber == null || student.getRollNumber().contains(rollNumber)))
                .collect(Collectors.toList())
                .stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(this::convertToDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), 
                    list -> new org.springframework.data.domain.PageImpl<>(list, pageable, studentRepository.count())));
        
        if (results.isEmpty()) {
            throw new StudentNotFoundException("No students found matching search criteria");
        }
        return results;
    }

    public Page<StudentResponseDto> changeDefaultIndex(Pageable pageable) {
        return studentRepository.findAll(pageable).map(this::convertToDto);
    }

    private StudentResponseDto convertToDto(Student student) {
        return new StudentResponseDto(
                student.getId(),
                student.getRollNumber(),
                student.getName(),
                student.getEmail(),
                student.getDepartment(),
                student.getContactNumber(),
                student.getYear(),
                student.getRoom() != null ? student.getRoom().getRoomNumber() : null
        );
    }
}