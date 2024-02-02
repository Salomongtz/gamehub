package com.example.gamehub.services;

import com.example.gamehub.records.PurchaseRecord;
import com.lowagie.text.DocumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface PurchaseService {
    ResponseEntity<?> purchase(Authentication authentication, List<PurchaseRecord> purchaseRecords) throws IOException;

    ByteArrayOutputStream getPDFReceipt(String email, Long purchaseId) throws DocumentException, IOException;
}
