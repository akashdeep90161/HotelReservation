package com.innovationm.hotel.dao;

import com.innovationm.hotel.model.secondary.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporalDao extends CrudRepository<Temporal,Long>{}