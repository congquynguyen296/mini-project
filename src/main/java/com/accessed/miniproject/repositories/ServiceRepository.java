package com.accessed.miniproject.repositories;

import com.accessed.miniproject.dto.response.PopularServiceResponse;
import com.accessed.miniproject.dto.response.SearchResponse;
import com.accessed.miniproject.model.Service;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {

    @Query(value = """
        WITH city_services AS (
            SELECT sl.service_id, sl.id AS service_location_id
            FROM tbl_location l
            INNER JOIN tbl_service_location sl ON l.id = sl.location_id
            WHERE l.city = :city
        )
        SELECT 
            s.id,
            s.name,
            s.image,
            COUNT(a.id) AS appointment_count
        FROM city_services cs
        INNER JOIN tbl_service s ON cs.service_id = s.id
        LEFT JOIN tbl_appointment a ON cs.service_location_id = a.service_location_id
        GROUP BY s.id, s.name, s.image
        ORDER BY appointment_count DESC
        LIMIT 6
        """, nativeQuery = true)
    List<PopularServiceResponse> findPopularServiceByCity(String city);


    @Query(value = "SELECT DISTINCT " +
            "    s.name AS dname " +
            "FROM tbl_service s " +
            "JOIN tbl_service_location sl ON s.id = sl.service_id " +
            "JOIN tbl_location l ON sl.location_id = l.id " +
            "WHERE l.city = :city " +
            "AND s.name LIKE CONCAT('%', :keyword, '%') " +
            "AND :keyword IS NOT NULL AND :keyword <> ''",
            nativeQuery = true)
    List<SearchResponse> searchServices(@Param("keyword") String keyword, @Param("city") String city);
}