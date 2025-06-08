package com.accessed.miniproject.repositories;

import com.accessed.miniproject.dto.response.PopularLocationResponse;
import com.accessed.miniproject.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query(value = """
        WITH avg_ratings AS (
            SELECT r.subject_id AS location_id, AVG(r.rate) AS avgRate
            FROM tbl_review r
            JOIN tbl_location l ON r.subject_id = l.id
            WHERE l.city = :city AND r.subject_type = 'BUSINESS'
            GROUP BY r.subject_id
        ),
        appointment_counts AS (
            SELECT l.id AS location_id, COUNT(1) AS appointmentCount
            FROM tbl_appointment a
            JOIN tbl_service_location sl ON a.service_location_id = sl.location_id
            JOIN tbl_location l ON sl.location_id = l.id
            WHERE l.city = :city
            GROUP BY l.id
        ),
        favorite_counts AS (
            SELECT f.subject_id AS location_id, COUNT(1) AS favoriteCount
            FROM tbl_favorite f
            JOIN tbl_location l ON f.subject_id = l.id
            WHERE l.city = :city AND f.subject_type = 'BUSINESS'
            GROUP BY f.subject_id
        )
        SELECT
            l.id AS locationId,
            l.district,
            l.street,
            b.name,
            l.image,
            CAST(COALESCE(ar.avgRate, 0) AS DOUBLE PRECISION) AS avgRate,
            COALESCE(ac.appointmentCount, 0) AS appointmentCount,
            COALESCE(fc.favoriteCount, 0) AS favoriteCount
        FROM tbl_location l
        JOIN tbl_business b ON b.id = l.business_id
        LEFT JOIN avg_ratings ar ON ar.location_id = l.id
        LEFT JOIN appointment_counts ac ON ac.location_id = l.id
        LEFT JOIN favorite_counts fc ON fc.location_id = l.id
        WHERE l.city = :city
        ORDER BY avgRate ASC
        LIMIT 8
    """, nativeQuery = true)
    List<PopularLocationResponse> popularLocations(String city);
}
