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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        for (PurchaseRecord purchaseRecord : purchaseRecords) {
            Games games = gamesRepository.findByTitle(purchaseRecord.title());
            totalAmount += games.getPrice() - (games.getPrice() * games.getDiscount());
            Purchase_Game purchaseGame = new Purchase_Game(purchaseRecord.amount());

            games.addPurchaseGame(purchaseGame);
            purchaseGames.add(purchaseGame);
        }

        Purchase purchase = new Purchase(LocalDate.now(), totalAmount, customer, purchaseGames);

        purchaseRepository.save(purchase);
        return null;
    }
}
