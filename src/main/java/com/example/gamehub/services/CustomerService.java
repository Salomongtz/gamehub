package com.example.gamehub.services;

import com.example.gamehub.dtos.CustomerDTO;
import com.example.gamehub.models.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    List<CustomerDTO> getAllCustomersDTOs();
    Customer getCustomerByEmail(String email);

    CustomerDTO getCustomerDTOByEmail(String email);

    ResponseEntity<?> register(String name, String lastName, String email, String password);
}