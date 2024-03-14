package ca.mcgill.ecse321.sportscenter.service;

import ca.mcgill.ecse321.sportscenter.dao.RegistrationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    
    @Autowired
    RegistrationRepository registrationRepository;

    // register a customer
        // all failing scenarios
    // unregister a customer
        // any way this fails?

        
    // get all registrations by customer email
    // get all registrations for a session


}
