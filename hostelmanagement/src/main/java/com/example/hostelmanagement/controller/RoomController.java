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
import com.example.hostelmanagement.dto.RoomResponseDto;
import com.example.hostelmanagement.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<RoomResponseDto> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/available")
    public List<RoomResponseDto> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    @GetMapping("/floor/{floor}")
    public List<RoomResponseDto> getRoomsByFloor(@PathVariable Integer floor) {
        return roomService.getRoomsByFloor(floor);
    }

    @GetMapping("/type/{roomType}")
    public List<RoomResponseDto> getRoomsByType(@PathVariable String roomType) {
        return roomService.getRoomsByType(roomType);
    }

    @GetMapping("/occupied")
    public List<RoomResponseDto> getOccupiedRooms() {
        return roomService.getOccupiedRooms();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<RoomResponseDto>> getAllRoomsPaginated(Pageable pageable) {
        Page<RoomResponseDto> rooms = roomService.getAllRoomsPaginated(pageable);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/metadata")
    public ResponseEntity<MetaData> getMetaInfo(Pageable pageable) {
        return ResponseEntity.ok(roomService.getMetaData(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RoomResponseDto>> searchRooms(@RequestParam(required = false) String roomNumber,
                                                            @RequestParam(required = false) String status,
                                                            @RequestParam(required = false) String roomType,
                                                            Pageable pageable) {
        return ResponseEntity.ok(roomService.searchRooms(roomNumber, status, roomType, pageable));
    }

    @GetMapping("/changedefaultindex")
    public ResponseEntity<Page<RoomResponseDto>> changeIndexRooms(@RequestParam(defaultValue = "1") int pageNumber,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        return ResponseEntity.ok(roomService.changeDefaultIndex(pageable));
    }



    @GetMapping("/performance")
    public ResponseEntity<MetaData> getPerformanceMetadata(Pageable pageable) {
        long startTime = System.currentTimeMillis();
        MetaData metadata = roomService.getMetaData(pageable);
        long endTime = System.currentTimeMillis();
        
        metadata.setSort(metadata.getSort() + " | Query Time: " + (endTime - startTime) + "ms");
        return ResponseEntity.ok(metadata);
    }

    @GetMapping("/sorted-nulls")
    public ResponseEntity<Page<RoomResponseDto>> getRoomsSortedWithNulls(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("status").nullsLast()));
        Page<RoomResponseDto> rooms = roomService.getAllRoomsPaginated(pageable);
        return ResponseEntity.ok(rooms);
    }
}