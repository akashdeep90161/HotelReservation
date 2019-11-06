package com.innovationm.hotel.dao;

import com.innovationm.hotel.model.primary.RoomRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface RoomRequestDao extends CrudRepository<RoomRequest,Long> {

   // @Query(value = "select count(r.id) from room_request r where r.user_id=:userId ")
    @Query(nativeQuery = true, value = "select count(*) from room_request where user_id=:userId and cast(start_time as Date)=:bookingDate")
    public int countBookRoquestByUserIdAndTime(@Param("userId") long userId, @Param("bookingDate") String bookingDate );

    @Query(nativeQuery = true, value = "select count(*) from room_request where\n" +
            " room_id=:roomId and (((UNIX_TIMESTAMP(start_time)*1000)<=:startTime and \n" +
            "(((UNIX_TIMESTAMP(start_time)*1000) + duration)>=:startTime )) or\n" +
            " ((UNIX_TIMESTAMP(start_time)*1000)<=:endTime and \n" +
            "(((UNIX_TIMESTAMP(start_time)*1000) + duration)>=:endTime )))")
    public int countBookRoquestByRoomIdAndTime(@Param("roomId") long roomId, @Param("startTime")long startTime,@Param("endTime") long endTime);

    public List<RoomRequest> findAllByUserId(long userId);
    public void deleteRoomRequestByUserIdAndRoomId(long userId, long roomId);

    /* #desc room_request;
select count(*) from room_request where user_id=28 and cast(start_time as Date)=cast(now() as date);

*/
}
