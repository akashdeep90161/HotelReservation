package com.innovationm.hotel.dao;

import com.innovationm.hotel.model.secondary.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationDao extends CrudRepository<Location,Long> {
}