package com.example.gamehub.services.implement;

import com.example.gamehub.dtos.CustomerDTO;
import com.example.gamehub.models.Customer;
import com.example.gamehub.repositories.CustomerRepository;
import com.example.gamehub.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;


public class ClientServiceImplement implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerDTO> getAllCustomersDTOs() {
        return customerRepository.findAll().stream().map(CustomerDTO::new).toList();
    }
}
