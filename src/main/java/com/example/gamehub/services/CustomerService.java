package com.example.gamehub.services;

import com.example.gamehub.dtos.CustomerDTO;
import com.example.gamehub.models.Customer;
import com.example.gamehub.records.CustomerRecord;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    List<CustomerDTO> getAllCustomersDTOs();
    Customer getCustomerByEmail(String email);

    CustomerDTO getCustomerDTOByEmail(String email);

    ResponseEntity<?> register(CustomerRecord customerRecord) throws IOException;

    ResponseEntity<String> updateCustomer(CustomerRecord customerRecord, String email);

    ResponseEntity <?> addToCart (String cart, String email);
}
