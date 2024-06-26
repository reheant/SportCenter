package ca.mcgill.ecse321.sportscenter.service;


import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.dto.utilities.UserType;
import ca.mcgill.ecse321.sportscenter.model.Account;
import jakarta.transaction.Transactional;

/**
 * AccountService
 */
@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    OwnerRepository ownerRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    InstructorRepository instructorRepo;

    @Transactional
    public int authenticate(String email, String password, UserType type)
            throws AuthenticationException {
        Account a = accountRepo.findAccountByEmail(email);
        if (a == null || !a.getPassword().equals(password)) {
            throw new AuthenticationException("Incorrect login information");
        }

        switch (type) {
            case Owner:
                if (ownerRepo.findByAccount(a) == null) {
                    throw new AuthenticationException("Incorrect login information");
                }
                break;
            case Instructor:
                if (instructorRepo.findByAccount(a) == null) {
                    throw new AuthenticationException("Incorrect login information");
                }
                break;
            case Customer:
                if (customerRepo.findByAccount(a) == null) {
                    throw new AuthenticationException("Incorrect login information");
                }
                break;
        }

        return a.getId();
    }

}
