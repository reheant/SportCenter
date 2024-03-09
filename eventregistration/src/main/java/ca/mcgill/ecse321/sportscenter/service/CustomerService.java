package ca.mcgill.ecse321.sportscenter.service;

import java.util.regex.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CardRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.dao.PayPalRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Card;
import ca.mcgill.ecse321.sportscenter.model.Card.PaymentCardType;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.PayPal;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    PayPalRepository payPalRepository;
    @Autowired
    CardRepository cardRepository;

    @Transactional
    public Customer createCustomer(String firstName, String lastName, String email, String password,
            Boolean wantsEmailConfirmation) throws Exception {

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
            throw new IllegalArgumentException(
                    "Invalid password format, password must have at least: one lower case letter, one higher case letter, one digit, one special character and be 8 charcters minimum");
        }

        // if (!isValidPaymentInfo(paymentMethod)){
        // throw new IllegalArgumentException("Invalid payment information");
        // }

        if (accountRepository.findAccountByEmail(email) != null) {
            throw new Exception("Email is already in use");
        }

        Account customerAccount = new Account();
        accountRepository.save(customerAccount);
        Customer customer = new Customer();
        customer.setAccount(customerAccount);
        // paymentMethod.setCustomer(customer);
        customerAccount.setEmail(email);
        customerAccount.setFirstName(firstName);
        customerAccount.setLastName(lastName);
        customerAccount.setPassword(password);
        customer.setWantsEmailConfirmation(wantsEmailConfirmation);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public Instructor promoteCustomerByEmail(String email) throws Exception {
        Account customerAccount = new Account();
        customerAccount = accountRepository.findAccountByEmail(email);
        List<Instructor> existingInstructors = instructorRepository.findAll();
        if (accountRepository.findAccountByEmail(email) == null) {
            throw new Exception("Email is not accociated to an account");
        }

        for (Instructor instructor : existingInstructors) {
            if (instructor.getAccount() == customerAccount) {
                throw new Exception("Person is already an Instructor");
            }
        }

        Instructor instructor = new Instructor();
        instructor.setAccount(customerAccount);
        try {
            instructorRepository.save(instructor);
        } catch (Exception e) {
            throw new Exception("account is already an instructor");
        }

        return instructor;
    }

    @Transactional
    public PayPal setPaypalInformation(String accountName, String customerEmail, String paypalEmail,
            String paypalPassword) throws Exception {
        if (accountRepository.findAccountByEmail(customerEmail) == null) {
            throw new Exception("Email is not accociated to an account");
        }
        Account customerAccount = new Account();
        customerAccount = accountRepository.findAccountByEmail(customerEmail);
        List<Customer> customerList = customerRepository.findAll();
        Customer customer = null;
        for (Customer c : customerList) {
            if (c.getAccount().equals(customerAccount)) {
                customer = c;
            }
        }
        PayPal payPal = new PayPal(accountName, customer, paypalEmail, paypalPassword);
        payPalRepository.save(payPal);
        return payPal;
    }

    @Transactional
    public Card setCardInformation(String accountName, String customerEmail, PaymentCardType paymentCardType, int cardNumber,
      int expirationDate, int ccv){
        Account customerAccount = new Account();
        customerAccount = accountRepository.findAccountByEmail(customerEmail);
        List<Customer> customerList = customerRepository.findAll();
        Customer customer = null;
        for (Customer c : customerList) {
            if (c.getAccount().equals(customerAccount)) {
                customer = c;
            }
        }
        Card card = new Card(accountName, customer, paymentCardType, cardNumber, expirationDate, ccv);
        cardRepository.save(card);
        return card;
      }

    @Transactional public List<Customer> getAllCustomers() {
        return (List<Customer>) (customerRepository.findAll());
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

    // private boolean isValidPaymentInfo(PaymentMethod paymentMethod){
    // if (paymentMethod instanceof PayPal) {
    // return(((PayPal)paymentMethod).getEmail() != null &&
    // ((PayPal)paymentMethod).getPassword() != null );
    // } else {
    // return(String.valueOf(((Card)paymentMethod).getCcv()).length() != 0 &&
    // String.valueOf(((Card)paymentMethod).getNumber()).length() != 0 );
    // }

    // }

}
