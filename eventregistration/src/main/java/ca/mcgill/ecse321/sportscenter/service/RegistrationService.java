package ca.mcgill.ecse321.sportscenter.service;

import java.util.List;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Registration;
import ca.mcgill.ecse321.sportscenter.model.Session;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    
    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RegistrationRepository registrationRepository;

    // register a customer

    @Transactional
    public Registration register(String email, Integer session_id) throws Exception {
        Registration registration = new Registration();

        Account customerAccount = accountRepository.findAccountByEmail(email);
        List<Customer> existingCustomers = customerRepository.findAll();

        for (Customer customer: existingCustomers) {
            if(customer.getAccount().equals(customerAccount)) {
                registration.setCustomer(customer);
                break;
            }
        }

        Session session = sessionRepository.findSessionById(session_id);

        registration.setSession(session);

        return registration;
    }

        // all failing scenarios
    // unregister a customer
        // any way this fails?

        
    // get all registrations by customer email
    // get all registrations for a session


}
