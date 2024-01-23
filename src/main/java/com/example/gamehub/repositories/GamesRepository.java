package com.example.gamehub.repositories;

import com.example.gamehub.models.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Games, Long> {
    boolean existsById (Long id);
    Games findByTitle(String title);
}
