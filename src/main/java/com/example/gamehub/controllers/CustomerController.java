package com.example.gamehub.controllers;

import com.example.gamehub.dtos.CustomerDTO;
import com.example.gamehub.records.CustomerRecord;
import com.example.gamehub.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> register(@RequestBody CustomerRecord customerRecord) {
        return customerService.register(customerRecord);
    }
}
