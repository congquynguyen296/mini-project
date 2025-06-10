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
    Staff getStaffById(String id);

    @Query("SELECT COUNT(distinct (s.id)) FROM Staff s " +
            "JOIN StaffLocation sl ON s.id = sl.staff.id " +
            "WHERE sl.location.city = :city")
    long countAllStaffByCity(String city);

    @Query(value = """
        WITH avg_rates AS (
             select distinct s.id as staff_id, s.full_name, s.address, s.image, avg(r.rate) as avgRate from tbl_staff s
         	left join tbl_staff_location sl on s.id = sl.staff_id
         	join tbl_location l on l.id = sl.location_id
         	join tbl_review r on s.id = r.subject_id
         	where l.city = :city
         	group by s.id
         	order by avgRate desc
         	LIMIT 8
         ),
         appointment_counts AS (
             select s.id as staff_id, count(1) as appointmentCount from tbl_staff s
         	left join tbl_staff_location sl on s.id = sl.staff_id
         	join tbl_location l on l.id = sl.location_id
         	join tbl_appointment a on s.id = a.staff_id
         	where l.city = :city
         	group by s.id
         	order by appointmentCount desc
         	LIMIT 8
         ),
         favorite_counts AS (
             select s.id as staff_id, count(1) as favoriteCount from tbl_staff s
         	left join tbl_staff_location sl on s.id = sl.staff_id
         	join tbl_location l on l.id = sl.location_id
         	join tbl_favorite f on s.id = f.subject_id
         	where l.city = :city
         	group by s.id
         	order by favoriteCount desc
         	LIMIT 8
         )
         
         SELECT\s
             ar.staff_id, ar.full_name, ar.address, ar.image,
             cast(ar.avgRate as double precision) as avgRate,
             coalesce(ac.appointmentCount, 0) as appointmentCount,
             coalesce(fc.favoriteCount, 0) as favoriteCount
         FROM avg_rates ar
         LEFT JOIN appointment_counts ac ON ar.staff_id = ac.staff_id
         LEFT JOIN favorite_counts fc ON COALESCE(ar.staff_id, ac.staff_id) = fc.staff_id
         order by avgRate desc, appointmentCount desc, favoriteCount desc
         ;
    """, nativeQuery = true)
    List<PopularStaffResponse> popularStaff(String city);

    @Query(value = "SELECT DISTINCT " +
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
