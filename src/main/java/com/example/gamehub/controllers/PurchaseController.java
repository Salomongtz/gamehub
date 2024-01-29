package com.example.gamehub.controllers;

import com.example.gamehub.records.PurchaseRecord;
import com.example.gamehub.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping()
    @Transactional
    public ResponseEntity<?> purchase(Authentication authentication, @RequestBody List<PurchaseRecord> purchaseRecords) {
        return purchaseService.purchase(authentication,purchaseRecords);
    }

}
