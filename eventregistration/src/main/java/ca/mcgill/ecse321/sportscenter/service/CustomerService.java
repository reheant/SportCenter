package ca.mcgill.ecse321.sportscenter.service;

import java.util.regex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(String firstName, String lastName, String email, String password,
            Boolean wantsEmailConfirmation) {


        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }


        if (!isValidName(firstName)) {
            throw new IllegalArgumentException("Invalid first name format");
        }


        if (!isValidName(lastName)) {
            throw new IllegalArgumentException("Invalid last name format");
        }

        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password format, password must have at least: one lower case letter, one higher case letter, one digit, one special character and be 8 charcters minimum");
        }


        Customer customer = new Customer();
        Account customerAccount = customer.getAccount();

        customerAccount.setEmail(email);
        customerAccount.setFirstName(firstName);
        customerAccount.setLastName(lastName);
        customerAccount.setPassword(password);
        customer.setWantsEmailConfirmation(wantsEmailConfirmation);


        customerRepository.save(customer);
        return customer;
    }

    // Respecting RFC 5322 email format (source :
    // https://www.javatpoint.com/java-email-validation#:~:text=To%20validate%20the%20email%20permitted,%5D%2B%24%22%20regular%20expression.)
    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Regex respects basic name formats, including names like "Louis-Phillipe" or
    // "Henry Jr." (allows Hyphens and periods)
    private boolean isValidName(String name) {
        String regex = "^[a-z ,.'-]+$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    // Password requirements: AT LEAST: one upper case letter, one lower case
    // letter, one digit, one special character, minimum 8 character length
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
