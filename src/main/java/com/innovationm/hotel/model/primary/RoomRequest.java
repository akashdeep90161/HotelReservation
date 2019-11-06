package com.innovationm.hotel.model.primary;

import com.innovationm.hotel.helper.enums.BookingStatus;
import com.innovationm.hotel.model.secondary.Temporal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
public class RoomRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.MERGE)
    private User user;
    @OneToOne(cascade = CascadeType.MERGE)

    private Room room;
    @OneToOne(cascade = CascadeType.ALL)
    //@NotNull
    private Temporal temporal;
    @NotNull(message = "You need to specify your start time")
    private Timestamp startTime;
    @NotNull(message = "You need to provide a duration for staying in room")
    private long duration;
    private BookingStatus status;

    private int updateCount;

    public int getUpdateCount() {
        return updateCount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Temporal getTemporal() {
        return temporal;
    }

    public void setTemporal(Temporal temporal) {
        this.temporal = temporal;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
