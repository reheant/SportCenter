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
		int id = 1;
		String firstName = "Muffin";
		String lastName = "Man";
		String email = "Man@gmail.com";
		String password = "123456";
        Account person = new Account(id, firstName, lastName, email, password);
        accountRepository.save(person);

        // Create Customer
        int customerId = 4;
        boolean wantsEmailConfirmation = false; 
        Customer customer = new Customer(customerId, wantsEmailConfirmation, person);
        customerRepository.save(customer);

        
        PaymentCardType payment = PaymentCardType.CreditCard;
        

        // Creates Card
        int paypal_id = 1;
		String name = "Rehean";
        String paypalEmail = "test@mail.com";
        String paypalPassword = "12345";

    
        
        PayPal paypalAccount = new PayPal(paypal_id, name, customer, paypalEmail, paypalPassword);

        paypalRepository.save(paypalAccount);
        

        paypalAccount = paypalRepository.findById(paypal_id).orElse(null);

		// Assert that account is not null and has correct attributes.
		assertNotNull(paypalAccount);
		assertEquals(paypal_id, paypalAccount.getId());
		assertEquals(name, paypalAccount.getName());
		assertEquals(customer, paypalAccount.getCustomer());
		assertEquals(paypalEmail, paypalAccount.getEmail());
		assertEquals(paypalPassword, paypalAccount.getPassword());
    }
}
   