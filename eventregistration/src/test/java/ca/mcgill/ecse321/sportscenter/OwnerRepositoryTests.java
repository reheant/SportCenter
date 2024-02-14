package ca.mcgill.ecse321.sportscenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
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
		accountRepository.deleteAll();
        ownerRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadAccount() {
		// Create account.
		int id = 1;
		String firstName = "Muffin";
		String lastName = "Man";
		String email = "Man@gmail.com";
		String password = "123456";
		Account account = new Account(id, firstName, lastName, email, password);
		

		// Save account
		accountRepository.save(account);

        //Create Owner
        int ownerID = 3; 
        Owner owner = new Owner(ownerID, account);
        // Save owner
        ownerRepository.save(owner);

		// Read account from database.
		owner = ownerRepository.findById(id).orElse(null);

		// Assert that account is not null and has correct attributes.
		assertNotNull(account);
		assertEquals(firstName, account.getFirstName());
		assertEquals(lastName, account.getLastName());
		assertEquals(email, account.getEmail());
		assertEquals(password, account.getPassword());
		assertEquals(id, account.getId());
	}
}

