package com.example.gamehub.services.implement;

import com.example.gamehub.records.PurchaseRecord;
import com.example.gamehub.models.Customer;
import com.example.gamehub.models.Games;
import com.example.gamehub.models.Purchase;
import com.example.gamehub.models.Purchase_Game;
import com.example.gamehub.repositories.CustomerRepository;
import com.example.gamehub.repositories.GamesRepository;
import com.example.gamehub.repositories.PurchaseRepository;
import com.example.gamehub.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImplement implements PurchaseService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    GamesRepository gamesRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Override
    public ResponseEntity<?> purchase(Authentication authentication, List<PurchaseRecord> purchaseRecords) {
        Customer customer = customerRepository.findByEmail(authentication.getName());
        List<Purchase_Game> purchaseGames = new ArrayList<>();
        double totalAmount = 0d;

        if(customer == null){
            return ResponseEntity.badRequest().body(customer+"Customer not found");
        }

        for (PurchaseRecord purchaseRecord : purchaseRecords) {
            Games game = gamesRepository.findByTitle(purchaseRecord.title());

            if (game == null) {
                return ResponseEntity.badRequest().body(game+"Game not found");
            }

            if(game.getPrice() < 0){
                return ResponseEntity.badRequest().body(game+"Price not negative");
            }

            if(game.getStock() <= 0){
                return ResponseEntity.badRequest().body(game+"Stock not equals 0");
            }

            totalAmount += game.getPrice() - (game.getPrice() * game.getDiscount());
            Purchase_Game purchaseGame = new Purchase_Game(purchaseRecord.amount());
            game.setSales(game.getSales() + purchaseRecord.amount());
            game.setStock(game.getStock() - purchaseRecord.amount());
            game.addPurchaseGame(purchaseGame);
            purchaseGames.add(purchaseGame);
        }
        Purchase purchase = new Purchase(LocalDate.now(), totalAmount, customer, purchaseGames);
        purchaseRepository.save(purchase);
        return ResponseEntity.ok( "Purchase successful" );
    }
}
