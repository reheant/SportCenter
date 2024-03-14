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

    /**
     * Registers a customer for a session
     * 
     * @param email the email of the customer to register
     * @param session_id the id of the session for which they want to register
     * @return registration: the resulting registration object
     * @throws Exception if there is an error while creating the registration
     */
    @Transactional
    public Registration register(String email, Integer session_id) throws Exception {
        if (email == null){
            throw new NullPointerException("The customer email cannot be null.");
        } else if (email.isEmpty()){
          throw new IllegalArgumentException("The customer email cannot be empty.");  
        } else if (session_id == null) {
            throw new NullPointerException("The session id cannot be null.");
        }

        Registration registration = new Registration();

        Account customerAccount = accountRepository.findAccountByEmail(email);
        if (customerAccount == null){
            throw new IllegalArgumentException("Email is not associated to an account.");
        }

        List<Customer> existingCustomers = customerRepository.findAll();

        for (Customer customer: existingCustomers) {
            if(customer.getAccount().equals(customerAccount)) {
                registration.setCustomer(customer);
                break;
            }
        }

        Session session = sessionRepository.findSessionById(session_id);

        registration.setSession(session);

        return registrationRepository.save(registration);
    }

    // all failing scenarios
        // no customer found
        // id null
        // no session found
        // customer already registered for session



    // unregister a customer
        // any way this fails?
        // what if less than 24h? can they cancel then?

        
    // get all registrations by customer email
    // get all registrations for a session
}
