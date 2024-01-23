package com.example.gamehub.services;

import com.example.gamehub.dtos.CustomerDTO;
import com.example.gamehub.models.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    List<CustomerDTO> getAllCustomersDTOs();
}
