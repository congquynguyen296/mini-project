package com.accessed.miniproject.repositories;

import com.accessed.miniproject.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, String> {
    Favorite getFavoriteBySubjectId(String id);
}
