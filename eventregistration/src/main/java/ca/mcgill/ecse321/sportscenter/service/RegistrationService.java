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
    public Registration register(String email, Integer session_id) throws IllegalArgumentException, NullPointerException {
        if (email == null){
            throw new NullPointerException("The customer email cannot be null.");
        } else if (email.isEmpty()){
          throw new IllegalArgumentException("The customer email cannot be empty.");  
        } else if (session_id == null) {
            throw new NullPointerException("The session id cannot be null.");
        }

        List<Registration> otherRegistrations = registrationRepository.findAll();
        for (Registration otherRegistration: otherRegistrations) {
            if (otherRegistration.getCustomer().getAccount().getEmail().equals(email) && otherRegistration.getSession().getId() == session_id){
                throw new IllegalArgumentException("Email is already registered for session with that id.");
            }
        }

        Registration registration = new Registration();

        Account customerAccount = accountRepository.findAccountByEmail(email);
        if (customerAccount == null){
            throw new IllegalArgumentException("Email is not associated to an account.");
        }
        
        Customer customer = customerRepository.findCustomerByAccountEmail(email);
        
        if (customer == null){
            throw new IllegalArgumentException("Account is not associated to a customer.");
        }

        registration.setCustomer(customer);

        Session session = sessionRepository.findSessionById(session_id);

        if (session == null){
            throw new IllegalArgumentException("Session id is not associated to a session.");
        } else {
            registration.setSession(session);
        }

        return registrationRepository.save(registration);
    }


    /**
     * Unregisters a customer for a session
     * 
     * @param email the email of the account associated to the customer.
     * @param session_id the id of the session the customer is registered to.
     * @return Boolean: true if a matching registration was found and successfully deleted. False Otherwise.
     */
    @Transactional
    public Boolean unregister(String email, Integer session_id) throws NullPointerException{
        if (email == null) {
            throw new NullPointerException("Email cannot be null.");
        } else if (session_id == null) {
            throw new NullPointerException("Session id cannot be null.");
        }

        Registration registration = registrationRepository.findRegistrationByCustomerAccountEmailAndSessionId(email, session_id);

        if (registration == null) {
            throw new IllegalArgumentException("No matching registration was found");
        }

        registrationRepository.deleteById(registration.getId());
        return true;
    }

    @Transactional
    public Registration getRegistration(String email, Integer session_id) throws IllegalArgumentException, NullPointerException {
        if (email == null){
            throw new NullPointerException("The customer email cannot be null.");
        } else if (email.isEmpty()){
          throw new IllegalArgumentException("The customer email cannot be empty.");  
        } else if (session_id == null) {
            throw new NullPointerException("The session id cannot be null.");
        }

        Registration registration = registrationRepository.findRegistrationByCustomerAccountEmailAndSessionId(email, session_id);
        return registration;
    }
}
