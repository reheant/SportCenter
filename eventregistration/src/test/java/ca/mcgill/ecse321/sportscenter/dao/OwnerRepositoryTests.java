package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Owner;

@SpringBootTest
public class OwnerRepositoryTests {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@AfterEach
	public void clearDatabase() {

		ownerRepository.deleteAll();
		accountRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadOwner() {
		// Create account.
		String firstName = "Muffin";
		String lastName = "Man";
		String email = "Man@gmail.com";
		String password = "123456";
		Account account = new Account(firstName, lastName, email, password);

		// Load account
		accountRepository.save(account);

		// Create Owner
		Owner owner = new Owner(account);
		// Load owner
		ownerRepository.save(owner);

		// Read account from database.
		Owner dbOwner = ownerRepository.findById(owner.getId()).orElse(null);

		// Assert that Owner is not null and has correct attributes.
		assertNotNull(dbOwner);
		assertEquals(account.getId(), dbOwner.getAccount().getId());
		assertEquals(account.getEmail(), dbOwner.getAccount().getEmail());
		assertEquals(account.getFirstName(), dbOwner.getAccount().getFirstName());
		assertEquals(account.getLastName(), dbOwner.getAccount().getLastName());
		assertEquals(account.getPassword(), dbOwner.getAccount().getPassword());

	}
}

