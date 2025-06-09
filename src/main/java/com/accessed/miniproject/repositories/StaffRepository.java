package com.accessed.miniproject.repositories;

import com.accessed.miniproject.dto.response.PopularStaffResponse;
import com.accessed.miniproject.dto.response.SearchResponse;
import com.accessed.miniproject.model.Staff;
import com.accessed.miniproject.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    long count();

    @Query(value = """
        WITH review_data AS (
            SELECT\s
                r.subject_id,\s
                AVG(r.rate) AS avgRate
            FROM tbl_review r
            JOIN tbl_staff s ON r.subject_id = s.id
            JOIN tbl_staff_location sl ON s.id = sl.staff_id
            JOIN tbl_location l ON sl.location_id = l.id
            WHERE l.city = :city AND r.subject_type = 'STAFF'
            GROUP BY r.subject_id
        ),
        favorite_data AS (
            SELECT\s
                f.subject_id,\s
                COUNT(f.subject_id) AS favoriteCount
            FROM tbl_favorite f
            JOIN tbl_staff_location sl ON f.subject_id = sl.staff_id
            JOIN tbl_location l ON sl.location_id = l.id
            WHERE l.city = :city
            GROUP BY f.subject_id
        ),
        appointment_data AS (
            SELECT\s
                a.staff_id AS subject_id,\s
                COUNT(*) AS appointmentCount
            FROM tbl_appointment a
            JOIN tbl_staff_location sl ON a.staff_id = sl.staff_id
            JOIN tbl_location l ON sl.location_id = l.id
            WHERE l.city = :city
            GROUP BY a.staff_id
        )
        
        SELECT\s
            s.id AS staff_id,
            s.full_name,
            s.address,
            CAST(COALESCE(rd.avgRate, 0) AS DOUBLE PRECISION) AS avgRate,
            COALESCE(fd.favoriteCount, 0) AS favoriteCount,
            COALESCE(ad.appointmentCount, 0) AS appointmentCount
        FROM tbl_staff s
        JOIN tbl_staff_location sl ON s.id = sl.staff_id
        JOIN tbl_location l ON sl.location_id = l.id
        LEFT JOIN review_data rd ON s.id = rd.subject_id
        LEFT JOIN favorite_data fd ON s.id = fd.subject_id
        LEFT JOIN appointment_data ad ON s.id = ad.subject_id
        WHERE l.city = :city
        GROUP BY s.id, s.full_name, s.address, rd.avgRate, fd.favoriteCount, ad.appointmentCount
        ;
    """, nativeQuery = true)
    List<PopularStaffResponse> popularStaff(String city);

    @Query(value = "SELECT DISTINCT " +
            "    s.id AS did, " +
            "    s.image AS dimage, " +
            "    s.full_name AS dname " +
            "FROM tbl_staff s " +
            "JOIN tbl_staff_location sl ON s.id = sl.staff_id " +
            "JOIN tbl_location l ON sl.location_id = l.id " +
            "WHERE l.city = :city " +
            "AND s.full_name LIKE CONCAT('%', :keyword, '%') " +
            "AND :keyword IS NOT NULL AND :keyword <> ''",
            nativeQuery = true)
    List<SearchResponse> searchStaffs(@Param("keyword") String keyword, @Param("city") String city);
}
