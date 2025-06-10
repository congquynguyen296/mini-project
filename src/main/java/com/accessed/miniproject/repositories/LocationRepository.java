package com.accessed.miniproject.repositories;

import com.accessed.miniproject.dto.response.SearchResponse;
import com.accessed.miniproject.model.Location;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    int countLocationByCity(String city);

    @Query(value = "SELECT DISTINCT " +
            "    b.name AS dname " +
            "FROM tbl_business b " +
            "LEFT JOIN tbl_location l ON b.id = l.business_id " +
            "WHERE l.city = :city " +
            "AND b.name LIKE CONCAT('%', :keyword, '%') " +
            "AND :keyword IS NOT NULL AND :keyword <> ''",
            nativeQuery = true)
    List<SearchResponse> searchBusinesses(@Param("keyword") String keyword, @Param("city") String city);
}
