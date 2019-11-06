package com.innovationm.hotel.model.primary;

import com.innovationm.hotel.model.secondary.Location;
import com.innovationm.hotel.model.secondary.RoomFacility;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "rent can not be null")
    private double rent;
    @NotNull(message = "Room Number can not be null")
  //  @UniqueElements(message = "Room Number must be Unique")
    private int roomNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private RoomFacility roomFacility;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomFacility getRoomFacility() {
        return roomFacility;
    }

    public void setRoomFacility(RoomFacility roomFacility) {
        this.roomFacility = roomFacility;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
