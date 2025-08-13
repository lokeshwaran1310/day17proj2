package com.example.hostelmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.hostelmanagement.dto.MetaData;
import com.example.hostelmanagement.dto.RoomResponseDto;
import com.example.hostelmanagement.entity.Room;
import com.example.hostelmanagement.exception.RoomNotFoundException;
import com.example.hostelmanagement.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<RoomResponseDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RoomResponseDto> getAvailableRooms() {
        List<RoomResponseDto> rooms = roomRepository.findAll().stream()
                .filter(room -> "AVAILABLE".equalsIgnoreCase(room.getStatus()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException("No available rooms found");
        }
        return rooms;
    }

    public List<RoomResponseDto> getRoomsByFloor(Integer floor) {
        List<RoomResponseDto> rooms = roomRepository.findAll().stream()
                .filter(room -> room.getFloor().equals(floor))
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException("No rooms found on floor: " + floor);
        }
        return rooms;
    }

    public List<RoomResponseDto> getRoomsByType(String roomType) {
        return roomRepository.findAll().stream()
                .filter(room -> room.getRoomType().equalsIgnoreCase(roomType))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RoomResponseDto> getOccupiedRooms() {
        return roomRepository.findAll().stream()
                .filter(room -> room.getStudents() != null && !room.getStudents().isEmpty())
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RoomResponseDto convertToDto(Room room) {
        Integer studentCount = (room.getStudents() != null) ? room.getStudents().size() : 0;
        List<String> studentNames = (room.getStudents() != null) ? 
                room.getStudents().stream().map(student -> student.getName()).collect(Collectors.toList()) : 
                new ArrayList<>();

        return new RoomResponseDto(
                room.getRoomId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getStatus(),
                room.getCapacity(),
                room.getFloor(),
                room.getManagedBy() != null ? room.getManagedBy().getName() : null,
                studentCount,
                studentNames
        );
    }
  

    public Page<RoomResponseDto> getAllRoomsPaginated(Pageable pageable) {
        return roomRepository.findAll(pageable).map(this::convertToDto);
    }

    public MetaData getMetaData(Pageable pageable) {
        Page<Room> page = roomRepository.findAll(pageable);
        return new MetaData(page.getNumber(), page.getSize(), page.getTotalElements(), 
                           page.getTotalPages(), page.getSort().toString(), page.isFirst(), page.isLast());
    }

    public Page<RoomResponseDto> searchRooms(String roomNumber, String status, String roomType, Pageable pageable) {
        return roomRepository.findAll(pageable).stream()
                .filter(room -> (roomNumber == null || room.getRoomNumber().contains(roomNumber)) &&
                               (status == null || room.getStatus().equalsIgnoreCase(status)) &&
                               (roomType == null || room.getRoomType().equalsIgnoreCase(roomType)))
                .collect(Collectors.toList())
                .stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(this::convertToDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), 
                    list -> new org.springframework.data.domain.PageImpl<>(list, pageable, roomRepository.count())));
    }

    public Page<RoomResponseDto> changeDefaultIndex(Pageable pageable) {
        return roomRepository.findAll(pageable).map(this::convertToDto);
    }

}