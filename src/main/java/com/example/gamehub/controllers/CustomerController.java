package com.example.gamehub.controllers;

import com.example.gamehub.dtos.CustomerDTO;
import com.example.gamehub.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public CustomerDTO getCustomer(Authentication authentication) {
        return customerService.getCustomerDTOByEmail(authentication.getName());
    }
}
