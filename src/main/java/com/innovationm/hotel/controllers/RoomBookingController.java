package com.innovationm.hotel.controllers;

import com.innovationm.hotel.model.primary.RoomRequest;
import com.innovationm.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class RoomBookingController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/bookRoom")
    public ResponseEntity<RoomRequest> bookRoom(@Valid @RequestBody RoomRequest roomRequest, @RequestParam("roomId") long roomId,@RequestParam("userId") long userId ) throws ParseException {
        RoomRequest roomRequest1=roomService.createRoomRequest(userId,roomId,roomRequest);
        return ResponseEntity.ok(roomRequest1);
    }
  //  @GetMapping("/getBookedRoomList"+"/{userId}")
    @GetMapping("/getBookedRoomList")
    public ResponseEntity<List<RoomRequest>> getRoomRequestList(@RequestParam("userId") long userId)
    {
        List<RoomRequest> roomRequests=roomService.getAllRequestByUserId(userId);
        return ResponseEntity.ok(roomRequests);
    }
    @DeleteMapping("/cancelRoomBooking")
    public ResponseEntity<String> cancelBookedRoom(@RequestParam("roomId") long roomId,@RequestParam("userId") long userId)
    {
        roomService.deleteRoomRequest(userId,roomId);
        return ResponseEntity.ok("Your booking is canceld succesfully");
    }
    @PutMapping("/updateBooking")
    public ResponseEntity<RoomRequest> updateRoomBooking(@RequestParam("roomRequestId") long id,@RequestBody RoomRequest roomRequest)
    {
        RoomRequest roomRequest1=roomService.updateRoomRequest(id,roomRequest);
        return ResponseEntity.ok(roomRequest1);
    }
}
