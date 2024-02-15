package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.PayPalRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.PaymentMethodRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Card;
import ca.mcgill.ecse321.sportscenter.model.Card.PaymentCardType;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.PayPal;
import ca.mcgill.ecse321.sportscenter.model.PaymentMethod;


@SpringBootTest
public class PaypalRepositoryTest {

    @Autowired
    private PayPalRepository paypalRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void clearDatabase() {
        paypalRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadPayPal() {
        // Create account.
        String firstName = "Muffin";
        String lastName = "Man";
        String email = "Man@gmail.com";
        String password = "123456";
        Account person = new Account(firstName, lastName, email, password);
        accountRepository.save(person);

        // Create Customer
        boolean wantsEmailConfirmation = false;
        Customer customer = new Customer(wantsEmailConfirmation, person);
        customerRepository.save(customer);


        // Creates Card
        String name = "Rehean";
        String paypalEmail = "test@mail.com";
        String paypalPassword = "12345";


        PayPal paypalAccount = new PayPal(name, customer, paypalEmail, paypalPassword);

        paypalRepository.save(paypalAccount);

        PayPal dbPaypalAccount = paypalRepository.findById(paypalAccount.getId()).orElse(null);

        // Assert that account is not null and has correct attributes.
        assertNotNull(paypalAccount);
        assertEquals(name, dbPaypalAccount.getName());
        assertEquals(customer.getId(), dbPaypalAccount.getCustomer().getId());
        assertEquals(paypalEmail, dbPaypalAccount.getEmail());
        assertEquals(paypalPassword, dbPaypalAccount.getPassword());
    }
}
