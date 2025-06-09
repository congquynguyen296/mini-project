package com.accessed.miniproject.repositories;

import com.accessed.miniproject.dto.response.PopularLocationResponse;
import com.accessed.miniproject.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query(value = """
        WITH avg_rates AS (
            select l.id as location_id, l.district, l.street, b.name, l.image, cast(coalesce(avg(r.rate), 0) as double precision ) as avgRate from tbl_location l
        	left join tbl_review r on l.id = r.subject_id
        	left join tbl_business b on l.business_id = b.id
        	where l.city = :city
        	group by l.id
        	order by avgRate desc
        	LIMIT 8
        ),
        appointment_counts AS (
            select l.id as location_id, count(1) as appointmentCount from tbl_location l
        	left join tbl_service_location sl on l.id = sl.location_id
        	join tbl_appointment a on a.service_location_id = sl.id
        	where l.city = :city
        	group by l.id
        	order by appointmentCount desc
        	LIMIT 8
        ),
        favorite_counts AS (
            select l.id as location_id, coalesce(count(1), 0) as favoriteCount from tbl_location l
        	join tbl_favorite f on l.id = f.subject_id
        	where l.city = :city
        	group by l.id
        	order by favoriteCount desc
        	LIMIT 8
        )
        
        SELECT\s
            ar.location_id, ar.district, ar.street, ar.name, ar.image,
            COALESCE(ar.avgRate, 0) AS avgRate,
            COALESCE(ac.appointmentCount, 0) AS appointmentCount,
            COALESCE(fc.favoriteCount, 0) AS favoriteCount
        FROM avg_rates ar
        LEFT JOIN appointment_counts ac ON ar.location_id = ac.location_id
        LEFT JOIN favorite_counts fc ON COALESCE(ar.location_id, ac.location_id) = fc.location_id
        order by avgRate desc, appointmentCount desc, favoriteCount desc
        ;
    """, nativeQuery = true)
    List<PopularLocationResponse> popularLocations(String city);
}
