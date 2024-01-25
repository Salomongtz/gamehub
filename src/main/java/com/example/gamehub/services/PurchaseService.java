package com.example.gamehub.services;

import com.example.gamehub.records.PurchaseRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PurchaseService {
    ResponseEntity<?> purchase(Authentication authentication,List<PurchaseRecord> purchaseRecords);
}
