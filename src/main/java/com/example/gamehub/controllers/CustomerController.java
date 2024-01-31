package com.example.gamehub.controllers;

import com.example.gamehub.dtos.CustomerDTO;
import com.example.gamehub.models.Customer;
import com.example.gamehub.models.Games;
import com.example.gamehub.models.Purchase;
import com.example.gamehub.models.Purchase_Game;
import com.example.gamehub.records.CustomerRecord;
import com.example.gamehub.records.PurchaseRecord;
import com.example.gamehub.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public CustomerDTO getCustomer(Authentication authentication) {
        return customerService.getCustomerDTOByEmail(authentication.getName());
    }

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody CustomerRecord customerRecord) throws IOException {
        return customerService.register(customerRecord);
    }

    @PatchMapping
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerRecord customerRecord, Authentication authentication) {
        return customerService.updateCustomer(customerRecord, authentication.getName());
    }

    @PatchMapping("/api/customers/purchase")
    public ResponseEntity<?> addToCart(@RequestBody PurchaseRecord purchaseRecord,
                                                    Authentication authentication){
       return customerService.addToCart(purchaseRecord, authentication.getName());






    }
}


