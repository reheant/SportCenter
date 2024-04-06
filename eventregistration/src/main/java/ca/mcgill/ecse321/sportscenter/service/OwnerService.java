package ca.mcgill.ecse321.sportscenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Owner;


@Service
public class OwnerService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired 
    OwnerRepository ownerRepository;
    
    
    /**
    * Creates a new owner with the provided details.
    *
    * @param firstName the first name of the owner
    * @param lastName the last name of the owner
    * @param email the email address of the owner
    * @param password the password for the owner's account
    * @return the newly created owner
    * @throws Exception if there is an error creating the owner
    */
    @Transactional
    public Owner createOwner(String firstName, String lastName, String email, String password) throws Exception {        
    
        if (firstName == null|| lastName==null || email==null || password==null){
            throw new Exception("Please ensure all fields are complete and none are empty");
        }
        if (!isValidEmail(email)) {
            throw new Exception("Invalid email format");
        }

        if (!isValidName(firstName)) {
            throw new Exception("Invalid first name format");
        }

        if (!isValidName(lastName)) {
            throw new Exception("Invalid last name format");
        }

        if (!isValidPassword(password)) {
            throw new Exception("Invalid password format, password must have at least: one lower case letter, one higher case letter, one digit, one special character and be 8 characters minimum");
        }

        if (accountRepository.findAccountByEmail(email) != null) {
            throw new Exception("Email is already in use");
        }
        // Create Account
        Account ownerAccount = new Account();
        ownerAccount.setEmail(email);
        ownerAccount.setFirstName(firstName);
        ownerAccount.setLastName(lastName);
        ownerAccount.setPassword(password);
        accountRepository.save(ownerAccount);
        
        // Create Owner
        Owner owner = new Owner();
        owner.setAccount(ownerAccount); 
        ownerRepository.save(owner);
        return owner;
    }

    /**
     * Retrieves a list of all instructors.
     *
     * @return List of all instructors.
     * @throws Exception If an error occurs while retrieving the instructors.
     */
    @Transactional
    public List<Owner> getAllOwners() throws Exception {
        List<Owner> owners = new ArrayList<>();
        ownerRepository.findAll().forEach(owners::add);
        return owners;
    }

    /** Helper Method
     * Respecting RFC 5322 email format (source : https://www.javatpoint.com/java-email-validation#:~:text=To%20validate%20the%20email%20permitted,%5D%2B%24%22%20regular%20expression.)
     * 
     * @param email the email to verify
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    /** Helper Method
     * Regex respects basic name formats, including names like "Louis-Phillipe" or "Henry Jr." (allows Hyphens and periods)
     * 
     * @param name the name to verify
     * @return true if the name is valid, false otherwise
     */
    private boolean isValidName(String name) {
        String regex = "^[a-z ,.'-]+$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /** Helper Method
     * Password requirements: AT LEAST: one upper case letter, one lower case letter, one digit, one special character, minimum 8 character length
     * 
     * @param password the password to verify
     * @return true if the password is valid, false otherwise
     */
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
