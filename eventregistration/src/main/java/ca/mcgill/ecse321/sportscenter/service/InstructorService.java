package ca.mcgill.ecse321.sportscenter.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import jakarta.transaction.Transactional;

@Service
public class InstructorService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    InstructorRepository instructorRepository;

    @Transactional
    public Instructor get(String email) {
        return instructorRepository.findInstructorByAccountEmail(email);
    }

    /**
     * Creates a customer and sets relevant information.
     *
     * @param firstName              first name of customer (String)
     * @param lastName               last name of customer (String)
     * @param email                  email of customer (String)
     * @param passowrd               password of customer (String)
     * @param wantsEmailConfirmation customer's email confirmation status (Bool)
     * @return The created customer
     * @throws Exception if name, email or password is invalid format or if an
     *                   account already exists with the email.
     */
    @Transactional
    public Account update(String firstName, String lastName, String email, String password) throws Exception {

        if (firstName == null || lastName == null || email == null || password == null) {
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
            throw new Exception(
                    "Invalid password format, password must have at least: one lower case letter, one higher case letter, one digit, one special character and be 8 charcters minimum");
        }

        if (accountRepository.findAccountByEmail(email) == null) {
            throw new Exception("Email not found");
        }

        Account instructorAccount = accountRepository.findAccountByEmail(email);
        instructorAccount.setEmail(email);
        instructorAccount.setFirstName(firstName);
        instructorAccount.setLastName(lastName);
        instructorAccount.setPassword(password);
        accountRepository.save(instructorAccount);
        return instructorAccount;
    }

    /**
     * Helper Method
     * Respecting RFC 5322 email format (source :
     * https://www.javatpoint.com/java-email-validation#:~:text=To%20validate%20the%20email%20permitted,%5D%2B%24%22%20regular%20expression.)
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

    /**
     * Helper Method
     * Regex respects basic name formats, including names like "Louis-Phillipe" or
     * "Henry Jr." (allows Hyphens and periods)
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

    /**
     * Helper Method
     * Password requirements: AT LEAST: one upper case letter, one lower case
     * letter, one digit, one special character, minimum 8 character length
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
