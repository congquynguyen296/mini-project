package com.accessed.miniproject.repositories;

import com.accessed.miniproject.model.Sector;
import com.accessed.miniproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector, String> {
    Page<Sector> findAll(Pageable pageable);
}
