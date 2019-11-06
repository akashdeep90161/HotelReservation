package com.innovationm.hotel.service;

import com.innovationm.hotel.dao.*;
import com.innovationm.hotel.exception.InvalidDateException;
import com.innovationm.hotel.exception.RoomBookingExceedException;
import com.innovationm.hotel.exception.RoomNotAvailableException;
import com.innovationm.hotel.exception.UpdationLimitExceedException;
import com.innovationm.hotel.helper.enums.BookingStatus;
import com.innovationm.hotel.model.primary.Room;
import com.innovationm.hotel.model.primary.RoomRequest;
import com.innovationm.hotel.model.primary.User;
import com.innovationm.hotel.model.secondary.Location;
import com.innovationm.hotel.model.secondary.RoomFacility;
import com.innovationm.hotel.model.secondary.Temporal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private RoomRequestDao roomRequestDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private RoomFacilityDao roomFacilityDao;
    @Autowired
    private TemporalDao temporalDao;


    @Transactional
    public Room addRoom(Room room)
    {
        Location location=room.getLocation();
        RoomFacility roomFacility=room.getRoomFacility();
        roomFacility=roomFacilityDao.save(roomFacility);

        room.setRoomFacility(roomFacility);
        locationDao.save(location);
        return roomDao.save(room);
    }
    public List<Room> getAllRooms()
    {
        return (List<Room>) roomDao.findAll();
    }
    public Room getRoomById(long id)
    {

        return roomDao.findById(id).get();
    }
    public Room updateRoom(Room room,long id)
    {
        room.setId(id);
        return addRoom(room);

    }
    public void deleteRoom(long id)
    {
        roomDao.deleteById(id);
    }

    @Transactional
    public RoomRequest createRoomRequest(long userId,long roomId,RoomRequest roomRequest) throws ParseException {
        Timestamp start_time=roomRequest.getStartTime();
        String date=start_time.toString().substring(0,10);
        int count=roomRequestDao.countBookRoquestByUserIdAndTime(userId,date);
        System.out.println("count request = "+count);

        /*  Exception Handling for 'Maximum 3 rooms are available for a day.'*/
        if(count>=3)
        {
            throw new RoomBookingExceedException("You can't book more than 3 rooms in a day!");
        }

        /*  Exception Handling for 'Only future dates can be reserved for room.'*/
        if(start_time.getTime()<=(new Timestamp(System.currentTimeMillis()).getTime()))
        {
          throw new InvalidDateException("Only future dates can be reserved for room Please select a valid date");
        }

        /*
        * Checking If already  room has booked for given time period
        * */

        int countAlreadyBookRequest=roomRequestDao.countBookRoquestByRoomIdAndTime(roomId,start_time.getTime(),start_time.getTime()+roomRequest.getDuration());
        if(countAlreadyBookRequest>0)
        {
            throw new RoomNotAvailableException("The requested room is not avilable for given date. Kindly try for another date..");
        }

        User user =new User();
        user.setId(userId);
        roomRequest.setUser(user);

        Room room=new Room();
        room.setId(roomId);
        roomRequest.setRoom(room);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Temporal temporal=new Temporal();
        temporal.setCreated(timestamp);
        temporal.setModified(timestamp);
        temporal=temporalDao.save(temporal);
        roomRequest.setTemporal(temporal);

        roomRequest.setUpdateCount(0);
        roomRequest.setStatus(BookingStatus.Confirm);
        return roomRequestDao.save(roomRequest);
    }
    public List<RoomRequest> getAllRequestByUserId(long userId)
    {
        return (List<RoomRequest>) roomRequestDao.findAllByUserId(userId);
    }

    @Transactional
    public void deleteRoomRequest(long userId, long roomId)
    {
        roomRequestDao.deleteRoomRequestByUserIdAndRoomId(userId,roomId);
    }

    @Transactional
    public RoomRequest updateRoomRequest(long roomRequestId,RoomRequest roomRequest)
    {
        Timestamp start_time=roomRequest.getStartTime();
        RoomRequest prevRequest=roomRequestDao.findById(roomRequestId).get();
        int updateCount=prevRequest.getUpdateCount();
        if(updateCount>=2)
        {
            throw new UpdationLimitExceedException("you can update your booking only two times");
        }
        else {
            updateCount++;
            roomRequest.setUpdateCount(updateCount);
        }
        /*  Exception Handling for 'Only future dates can be reserved for room.'*/
        if(start_time.getTime()<=(new Timestamp(System.currentTimeMillis()).getTime()))
        {
            throw new InvalidDateException("Only future dates can be reserved for room Please select a valid date");
        }

        /*
         * Checking If already  room has booked for given time period
         * */
        if(prevRequest.getRoom().getId()!=roomRequest.getRoom().getId()) {
            int countAlreadyBookRequest = roomRequestDao.countBookRoquestByRoomIdAndTime(roomRequest.getRoom().getId(), start_time.getTime(), start_time.getTime() + roomRequest.getDuration());
            if (countAlreadyBookRequest > 0) {
                throw new RoomNotAvailableException("The requested room is not avilable for given date. Kindly try for another date..");
            }
        }
        Temporal temporal = roomRequest.getTemporal();
        temporal.setModified(new Timestamp(System.currentTimeMillis()));
        roomRequest.setId(roomRequestId);
        return roomRequestDao.save(roomRequest);
    }


}
