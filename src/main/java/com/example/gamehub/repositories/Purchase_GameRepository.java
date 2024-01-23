package com.example.gamehub.repositories;

import com.example.gamehub.models.Purchase_Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Purchase_GameRepository extends JpaRepository<Purchase_Game, Long> {

    boolean existsById (Long id);
}
