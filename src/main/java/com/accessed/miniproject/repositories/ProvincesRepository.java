package com.accessed.miniproject.repositories;

import com.accessed.miniproject.model.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvincesRepository extends JpaRepository<Provinces, String> {

    @Query("SELECT p.image FROM Provinces p WHERE p.cityCode = :cityCode")
    Optional<String> findImageByCity(String cityCode);

    boolean existsByCityCode(String cityCode);
}
