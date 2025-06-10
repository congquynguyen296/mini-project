package com.accessed.miniproject.repositories;

import com.accessed.miniproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User getUserByUsername(String user);

    Optional<User> findByUsername(String email);

    Optional<User> findByEmail(String email);

    Page<User> findByCity(String city, Pageable pageable);

    Page<User> findByCountry(String country, Pageable pageable);

    Page<User> findByCityAndCountry(String city, String country, Pageable pageable);

}
