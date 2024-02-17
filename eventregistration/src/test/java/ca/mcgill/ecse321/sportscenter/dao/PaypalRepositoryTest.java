package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.PayPal;


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
        customerRepository.deleteAll();
        accountRepository.deleteAll();
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
        // Load Customer
        customerRepository.save(customer);


        // Creates Paypal
        String name = "Rehean";
        String paypalEmail = "test@mail.com";
        String paypalPassword = "12345";
        PayPal paypalAccount = new PayPal(name, customer, paypalEmail, paypalPassword);
        // Load Paypal Accout
        paypalRepository.save(paypalAccount);

        PayPal dbPaypalAccount = paypalRepository.findById(paypalAccount.getId()).orElse(null);

        // Assert that paypal is not null and has correct attributes.
        assertNotNull(paypalAccount);
        assertEquals(name, dbPaypalAccount.getName());
        assertEquals(customer.getId(), dbPaypalAccount.getCustomer().getId());
        assertEquals(paypalEmail, dbPaypalAccount.getEmail());
        assertEquals(paypalPassword, dbPaypalAccount.getPassword());
        assertEquals(person.getId(),dbPaypalAccount.getCustomer().getAccount().getId());
		assertEquals(person.getEmail(), dbPaypalAccount.getCustomer().getAccount().getEmail());
		assertEquals(person.getFirstName(), dbPaypalAccount.getCustomer().getAccount().getFirstName());
		assertEquals(person.getLastName(), dbPaypalAccount.getCustomer().getAccount().getLastName());
		assertEquals(person.getPassword(), dbPaypalAccount.getCustomer().getAccount().getPassword());
        assertEquals(customer.getAccount().getId(), dbPaypalAccount.getCustomer().getAccount().getId());
		assertEquals(customer.getAccount().getEmail(), dbPaypalAccount.getCustomer().getAccount().getEmail());
		assertEquals(customer.getAccount().getFirstName(), dbPaypalAccount.getCustomer().getAccount().getFirstName());
		assertEquals(customer.getAccount().getLastName(), dbPaypalAccount.getCustomer().getAccount().getLastName());
		assertEquals(customer.getAccount().getPassword(), dbPaypalAccount.getCustomer().getAccount().getPassword());

    }
}
