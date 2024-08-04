package com.doanthuctap.repository;

import com.doanthuctap.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseRepository extends JpaRepository<House,Long> {
    @Query("SELECT DISTINCT h.address.city FROM House h WHERE h.address.city IS NOT NULL")
    List<String> findAllDistinctCities();

    // lấy list house thuộc 1 thành phố
    List<House> findByAddress_City(String city);

    // search theo tên
    @Query("SELECT h FROM House h WHERE h.name LIKE %:name%")
    List<House> findByNameContaining(@Param("name") String name);
}
