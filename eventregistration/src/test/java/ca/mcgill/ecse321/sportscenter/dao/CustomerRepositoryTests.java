package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;

@SpringBootTest
public class CustomerRepositoryTests {
    
	@Autowired
	private AccountRepository accountRepository;

    @Autowired
	private CustomerRepository customerRepository;

	@AfterEach
	public void clearDatabase() {
		accountRepository.deleteAll();
        customerRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadCustomer() {
		// Create account.
		int id = 1;
		String firstName = "Muffin";
		String lastName = "Man";
		String email = "Man@gmail.com";
		String password = "123456";
		Account account = new Account(id, firstName, lastName, email, password);
		

		// Save account
		accountRepository.save(account);

        //Create customer
        int customerID = 3; 
        boolean wantsEmailConfirmation = false;
        Customer customer = new Customer(customerID,wantsEmailConfirmation, account);
        // Save customer
        customerRepository.save(customer);

		// Read account from database.
		customer = customerRepository.findById(id).orElse(null);

		// Assert that account is not null and has correct attributes.
		assertNotNull(account);
		assertEquals(firstName, account.getFirstName());
		assertEquals(lastName, account.getLastName());
		assertEquals(email, account.getEmail());
		assertEquals(password, account.getPassword());
		assertEquals(id, account.getId());
	}
}

