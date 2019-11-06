package com.innovationm.hotel.controllers;

import com.innovationm.hotel.model.primary.Room;
import com.innovationm.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/addRoom")
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room)
    {
        Room room1=roomService.addRoom(room);
        return ResponseEntity.ok(room1);
    }
    @GetMapping("/getAllRooms")
    public ResponseEntity<List<Room>> getAllRooms()
    {
        List<Room> rooms=roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
    @PutMapping("/updateRoomDetails"+"/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable("id") long id,@Valid @RequestBody Room room)
    {
        Room room1=roomService.updateRoom(room,id);
        return ResponseEntity.ok(room1);
    }


}
