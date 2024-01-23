package com.example.gamehub.repositories;

import com.example.gamehub.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long> {
    boolean existsById (Long id);
}
