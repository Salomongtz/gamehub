package com.example.gamehub.services.implement;

import com.example.gamehub.dtos.CustomerDTO;
import com.example.gamehub.models.Customer;
import com.example.gamehub.records.CustomerRecord;
import com.example.gamehub.repositories.CustomerRepository;
import com.example.gamehub.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.gamehub.utils.Utils.passwordValidator;

@Service
public class CustomerServiceImplement implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerDTO> getAllCustomersDTOs() {
        return customerRepository.findAll().stream().map(CustomerDTO::new).toList();
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public CustomerDTO getCustomerDTOByEmail(String email) {
        return new CustomerDTO(getCustomerByEmail(email));
    }

    @Override
    public ResponseEntity<?> register(CustomerRecord customerRecord) {
        ResponseEntity<String> BAD_REQUEST = runVerifications(customerRecord.firstName(), customerRecord.lastName(), customerRecord.email(), customerRecord.password());
        if (BAD_REQUEST != null) {
            return BAD_REQUEST;
        }
        Customer customer = new Customer(customerRecord.firstName(), customerRecord.lastName(), customerRecord.email(), passwordEncoder.encode(customerRecord.password()));
        customerRepository.save(customer);
        return new ResponseEntity<>(customer + "\nCreated successfully!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateCustomer(CustomerRecord customerRecord, String email) {
        Customer customer = getCustomerByEmail(email);
        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
        ResponseEntity<String> BAD_REQUEST = runVerifications(customerRecord.firstName(), customerRecord.lastName(), customerRecord.email(), customerRecord.password());
        if (BAD_REQUEST != null) {
            return BAD_REQUEST;
        }
        customer.setFirstName(customerRecord.firstName());
        customer.setLastName(customerRecord.lastName());
        customer.setEmail(customerRecord.email());
        customer.setPassword(passwordEncoder.encode(customerRecord.password()));
        customerRepository.save(customer);
        return new ResponseEntity<>(customer + "\nUpdated successfully!", HttpStatus.OK);
    }

    private ResponseEntity<String> runVerifications(String firstName, String lastName, String email, String password) {
        if (firstName.isBlank()) {
            return new ResponseEntity<>("Missing NAME data", HttpStatus.BAD_REQUEST);
        } else if (lastName.isBlank()) {
            return new ResponseEntity<>("Missing LAST NAME data", HttpStatus.BAD_REQUEST);
        } else if (email.isBlank()) {
            return new ResponseEntity<>("Missing EMAIL data", HttpStatus.BAD_REQUEST);
        } else if (password.isBlank()) {
            return new ResponseEntity<>("Missing PASSWORD data", HttpStatus.BAD_REQUEST);
        }
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@$%^&-+=()])(?=\\S+$).{8,20}$";

        if (!password.matches(regex)) {
            return new ResponseEntity<>("Password should have at least one uppercase letter, one lowercase " +
                    "letter, one number and one special character (!@$%^&-+=()) and should be at least 8 characters long",
                    HttpStatus.BAD_REQUEST);
        }
        if (getCustomerByEmail(email) != null) {
            return new ResponseEntity<>(email + " already in use", HttpStatus.FORBIDDEN);
        }
        return null;
    }
}
