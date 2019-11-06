package com.innovationm.hotel.dao;

import com.innovationm.hotel.model.primary.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User,Long> {

    public Optional<User> findByEmail(String email);

   /* @Query(value = "select case when count(u.id) > 0 then true else false end as email_exists from user_secret u where u.email =:email",
            nativeQuery = true)

    public long doesEmailExist(@Param("email") String email);*/
}
