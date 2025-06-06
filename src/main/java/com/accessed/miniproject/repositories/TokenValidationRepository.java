package com.accessed.miniproject.repositories;

import com.accessed.miniproject.model.TokenValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenValidationRepository extends JpaRepository<TokenValidation, String> {
}
