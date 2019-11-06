package com.innovationm.hotel.dao;

import com.innovationm.hotel.model.secondary.RoomFacility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFacilityDao extends CrudRepository<RoomFacility,Long> {
}
