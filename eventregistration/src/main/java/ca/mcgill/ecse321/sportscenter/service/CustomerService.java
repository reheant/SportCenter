package ca.mcgill.ecse321.sportscenter.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CardRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorAssignmentRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.dao.PayPalRepository;
import ca.mcgill.ecse321.sportscenter.dao.PaymentMethodRepository;
import ca.mcgill.ecse321.sportscenter.dao.RegistrationRepository;
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
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    InstructorAssignmentRepository instructorAssignmentRepository;

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
    public Customer createCustomer(String firstName, String lastName, String email, String password,
            Boolean wantsEmailConfirmation) throws Exception {

        if (firstName == null || lastName == null || email == null || password == null
                || wantsEmailConfirmation == null) {
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

        if (accountRepository.findAccountByEmail(email) != null) {
            throw new Exception("Email is already in use");
        }

        Account customerAccount = new Account();
        accountRepository.save(customerAccount);
        Customer customer = new Customer();
        customer.setAccount(customerAccount);
        customerAccount.setEmail(email);
        customerAccount.setFirstName(firstName);
        customerAccount.setLastName(lastName);
        customerAccount.setPassword(password);
        customer.setWantsEmailConfirmation(wantsEmailConfirmation);
        customerRepository.save(customer);
        return customer;
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
    public Account updateCustomer(String firstName, String lastName, String email, String password,
            Boolean wantsEmailConfirmation) throws Exception {

        if (firstName == null || lastName == null || email == null || password == null
                || wantsEmailConfirmation == null) {
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

        Account customerAccount = accountRepository.findAccountByEmail(email);
        customerAccount.setEmail(email);
        customerAccount.setFirstName(firstName);
        customerAccount.setLastName(lastName);
        customerAccount.setPassword(password);
        accountRepository.save(customerAccount);
        Customer c = customerRepository.findByAccount(customerAccount);
        c.setWantsEmailConfirmation(wantsEmailConfirmation);
        customerRepository.save(c);
        return customerAccount;
    }

    /**
     * Promotes a customer to instructor from their email.
     *
     * @param email email of customer (String)
     * @return The created instructor
     * @throws Exception if email is null, not accociated to an account or if
     *                   customer is already an instructor
     */
    @Transactional
    public Instructor promoteCustomerByEmail(String email) throws Exception {
        if (email == null) {
            throw new Exception("email is null");
        }
        Account customerAccount = new Account();
        customerAccount = accountRepository.findAccountByEmail(email);
        List<Instructor> existingInstructors = instructorRepository.findAll();
        if (accountRepository.findAccountByEmail(email) == null) {
            throw new Exception("Email is not accociated to an account");
        }
        for (Instructor instructor : existingInstructors) {
            if (instructor.getAccount().equals(customerAccount)) {
                throw new Exception("Person is already an Instructor");
            }
        }
        Instructor instructor = new Instructor();
        instructor.setAccount(customerAccount);
        instructorRepository.save(instructor);
        return instructor;
    }

    @Transactional
    public boolean demoteInstructorByEmail(String email) throws Exception {
        if (email == null) {
            throw new Exception("Email is null");
        }

        Account customerAccount = accountRepository.findAccountByEmail(email);
        if (customerAccount == null) {
            throw new Exception("Email is not associated with any account");
        }

        Instructor instructor = instructorRepository.findByAccount(customerAccount);
        if (instructor == null) {
            throw new Exception("No instructor found for the given email");
        }

        instructorRepository.delete(instructor);

        return true;
    }

    /**
     * Adds Paypal information for customer.
     *
     * @param accountName    name of account (String)
     * @param customerEmail  customer's email (String)
     * @param paypalEmail    customer's paypal account email (String)
     * @param paypalPassword customer's paypal account password (String)
     * @return The created paypal payment information.
     * @throws Exception if any fields are null or if email is not accociated with
     *                   an account.
     */
    @Transactional
    public PayPal setPaypalInformation(String accountName, String customerEmail, String paypalEmail,
            String paypalPassword) throws Exception {

        if (accountName == null) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        if (customerEmail == null) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        if (paypalEmail == null) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        if (paypalPassword == null) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }
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

    /**
     * Adds card information for customer.
     *
     * @param accountName     name of account (String)
     * @param customerEmail   customer's email (String)
     * @param paymentCardType customer's paymentCardType (PaymentCardType)
     * @param cardNumber      customer's card number (int)
     * @param expirationDate  customer's expiration date (int)
     * @param ccv             customer's card ccv (int)
     * @return The created card.
     * @throws Exception if any fields are null or if email is not accociated with
     *                   an account.
     */
    @Transactional
    public Card setCardInformation(String accountName, String customerEmail, PaymentCardType paymentCardType,
            int cardNumber,
            int expirationDate, int ccv) throws Exception {
        if (accountName == null) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        if (customerEmail == null) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        if (paymentCardType == null) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        if (cardNumber <= 0) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        if (expirationDate <= 0) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }

        if (ccv <= 0) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }
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
        Card card = new Card(accountName, customer, paymentCardType, cardNumber, expirationDate, ccv);
        cardRepository.save(card);
        return card;
    }

    /**
     * Gets customer by email.
     *
     * @return List of customers
     * @throws Exception
     */
    @Transactional
    public Customer getCustomerByEmail(String email) throws Exception {
        if (email == null) {
            throw new Exception("Please ensure all fields are complete and none are empty");
        }
        if (customerRepository.findCustomerByAccountEmail(email) == null) {
            throw new Exception("Email is not accociated to an account");
        }
        return customerRepository.findCustomerByAccountEmail(email);
    }

    /**
     * Retrieves a list of all customers.
     *
     * @return List of all customers.
     * @throws Exception If an error occurs while retrieving the customers.
     */
    @Transactional
    public List<Customer> getAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    /**
     * Retrieves a list of all instructors.
     *
     * @return List of all instructors.
     * @throws Exception If an error occurs while retrieving the instructors.
     */
    @Transactional
    public List<Instructor> getAllInstructors() throws Exception {
        List<Instructor> instructors = new ArrayList<>();
        instructorRepository.findAll().forEach(instructors::add);
        return instructors;
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

    /**
     * Deletes a customer account from the repository based on the given ID.
     *
     * @param email The email of the customer to be deleted.
     */
    @Transactional
    public void deleteCustomer(String email) {
        Account account = accountRepository.findAccountByEmail(email);

        customerRepository.findByAccountId(account.getId()).ifPresent(customer -> {
            registrationRepository.findAllByCustomerId(customer.getId()).forEach(registration -> {
                registrationRepository.deleteById(registration.getId());
            });

            paymentMethodRepository.findAllByCustomerId(customer.getId()).forEach(paymentMethod -> {
                paymentMethodRepository.deleteById(paymentMethod.getId());
            });

            customerRepository.deleteById(customer.getId());
        });

        instructorRepository.findByAccountId(account.getId()).ifPresent(instructor -> {
            instructorAssignmentRepository.findAllByInstructorId(instructor.getId())
                    .forEach(assignment -> instructorAssignmentRepository.deleteById(assignment.getId()));

            instructorRepository.deleteById(instructor.getId());
        });

        accountRepository.deleteById(account.getId());
    }

}
