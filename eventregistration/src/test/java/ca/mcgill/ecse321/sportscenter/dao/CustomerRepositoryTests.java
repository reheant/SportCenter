package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
		String firstName = "Muffin";
		String lastName = "Man";
		String email = "Man@gmail.com";
		String password = "123456";
		Account account = new Account(firstName, lastName, email, password);

		// Load account
		accountRepository.save(account);

		// Create customer
		boolean wantsEmailConfirmation = false;
		Customer customer = new Customer(wantsEmailConfirmation, account);
		// Load customer
		customerRepository.save(customer);

		// Read account from database.
		Customer dbCustomer = customerRepository.findById(customer.getId()).orElse(null);
		Account dbAccount = dbCustomer.getAccount();

		// Assert that account is not null and has correct attributes.
		assertNotNull(dbCustomer);
		assertEquals(customer.getId(), dbCustomer.getId());
		assertEquals(customer.getWantsEmailConfirmation(), dbCustomer.getWantsEmailConfirmation());
		assertNotNull(dbAccount);
		assertEquals(account.getId(), dbAccount.getId());
		assertEquals(account.getEmail(), dbCustomer.getAccount().getEmail());
		assertEquals(account.getFirstName(), dbCustomer.getAccount().getFirstName());
		assertEquals(account.getLastName(), dbCustomer.getAccount().getLastName());
		assertEquals(account.getPassword(), dbCustomer.getAccount().getPassword());

	}
}

