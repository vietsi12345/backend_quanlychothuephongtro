package com.doanthuctap.repository;

import com.doanthuctap.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RoomRepository extends JpaRepository <Room,Long>{
    @Query("SELECT MIN(r.price) FROM Room r WHERE r.house.id = :houseId")
    BigDecimal findMinPriceByHouseId(@Param("houseId") Long houseId);

    List<Room>  findByHouseId (Long houseId);
}
