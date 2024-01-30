package com.example.gamehub.services.implement;

import com.example.gamehub.dtos.CustomerDTO;
import com.example.gamehub.models.Customer;
import com.example.gamehub.records.CustomerRecord;
import com.example.gamehub.repositories.CustomerRepository;
import com.example.gamehub.services.CustomerService;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sendgrid.*;
import java.io.IOException;

import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;


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
    public ResponseEntity<?> register(CustomerRecord customerRecord) throws IOException {
        sendEmail();
        ResponseEntity<String> BAD_REQUEST = runVerifications(customerRecord.firstName(), customerRecord.lastName(),
                customerRecord.email(), customerRecord.password());
        if (BAD_REQUEST != null) {
            return BAD_REQUEST;
        }
        if (getCustomerByEmail(customerRecord.email()) != null) {
            return new ResponseEntity<>(customerRecord.email() + " is already in use", HttpStatus.FORBIDDEN);
        }
        Customer customer = new Customer(customerRecord.firstName(), customerRecord.lastName(),
                customerRecord.email(), passwordEncoder.encode(customerRecord.password()));
        customerRepository.save(customer);

        return new ResponseEntity<>(customer + "\nCreated successfully!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateCustomer(CustomerRecord customerRecord, String email) {
        Customer customer = getCustomerByEmail(email);
        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
        ResponseEntity<String> BAD_REQUEST = runVerifications(customerRecord.firstName(), customerRecord.lastName(),
                customerRecord.email(), customerRecord.password());
        if (BAD_REQUEST != null) {
            return BAD_REQUEST;
        }
        String currentFirstName = customer.getFirstName();
        String currentLastName = customer.getLastName();
        String currentPassword = customer.getPassword();
        String currentEmail = customer.getEmail();
        StringBuilder message = new StringBuilder();
        if (!currentFirstName.equals(customerRecord.firstName())) {
            customer.setFirstName(customerRecord.firstName());
            message.append("First name changed from ").append(currentFirstName).append(" to ").append(customerRecord.firstName()).append("\n");
        }
        if (!currentLastName.equals(customerRecord.lastName())) {
            customer.setLastName(customerRecord.lastName());
            message.append("Last name changed from ").append(currentLastName).append(" to ").append(customerRecord.lastName()).append("\n");
        }
        if (!currentEmail.equals(customerRecord.email())) {
            customer.setEmail(customerRecord.email());
            message.append("Email changed from ").append(currentEmail).append(" to ").append(customerRecord.email()).append("\n");
        }
        if (!currentPassword.equals(passwordEncoder.encode(customerRecord.password()))) {
            customer.setPassword(passwordEncoder.encode(customerRecord.password()));
            message.append("Password changed from ").append(currentPassword).append(" to ").append(customerRecord.password()).append("\n");
        }
        customerRepository.save(customer);
        return new ResponseEntity<>(message.toString(), HttpStatus.OK);
    }

    public void sendEmail() throws IOException {
        Dotenv dotenv = Dotenv.configure().load();
        Email from = new Email("rokkuman10@gmail.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("alvarosop23@gmail.com");
        Content content = new Content("text/plain", "hola s");
        Mail mail = new Mail(from, subject, to, content);

        String apiKey = dotenv.get("SENDGRID_API_KEY");

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
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
                    "letter, one number and one special character (!@$%^&-+=()) and should be at least 8 characters " +
                    "long",
                    HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}
