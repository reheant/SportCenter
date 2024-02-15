package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.AccountRole;
import ca.mcgill.ecse321.sportscenter.model.Customer;

@SpringBootTest
public class AccountRepositoryTests {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerRepository customerRepository;

	@AfterEach
	public void clearDatabase() {
		accountRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadAccount() {
		// Create account.

		String firstName = "Muffin";
		String lastName = "Man";
		String email = "Man@gmail.com";
		String password = "123456";
		Account account = new Account(firstName, lastName, email, password);

		// Save account
		accountRepository.save(account);

		// Read account from database.
		account = accountRepository.findById(account.getId()).orElse(null);

		// Assert that account is not null and has correct attributes.
		assertNotNull(account);
		assertEquals(firstName, account.getFirstName());
		assertEquals(lastName, account.getLastName());
		assertEquals(email, account.getEmail());
		assertEquals(password, account.getPassword());
	}

	public void relation() {
		// Create account.
		int id = 1;
		String firstName = "Muffin";
		String lastName = "Man";
		String email = "Man@gmail.com";
		String password = "123456";

		Account account = new Account(firstName, lastName, email, password);
		accountRepository.save(account);

		// customerRepository.save(new Customer(0, false, account));
		// Save account

		// Read account from database.
		account = accountRepository.findById(account.getId()).orElse(null);

		// Assert that account is not null and has correct attributes.
		assertFalse(((Customer) account.getRoles().get(0)).getWantsEmailConfirmation());
	}
}
