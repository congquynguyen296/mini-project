package com.accessed.miniproject.repositories;

import com.accessed.miniproject.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    int countLocationByCity(String city);
}
