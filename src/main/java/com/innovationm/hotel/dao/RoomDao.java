package com.innovationm.hotel.dao;

import com.innovationm.hotel.model.primary.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDao extends CrudRepository<Room,Long> {
}
