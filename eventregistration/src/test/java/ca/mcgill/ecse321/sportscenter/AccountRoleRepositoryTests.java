package ca.mcgill.ecse321.sportscenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;

@SpringBootTest
public class AccountRoleRepositoryTests {
    
	@Autowired
	private AccountRepository accountRoleRepository;

	@AfterEach
	public void clearDatabase() {
		accountRoleRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadAccount() {
		// Create account.
		int id = 1;
		AccountRole accountRole = new AccountRole(id); 

		// Save account
		accountRoleRepository.save(accountRole);

		// Read account from database.
		accountRole = accountRoleRepository.findById(id).orElse(null);

		// Assert that account is not null and has correct attributes.
		assertNotNull(accountRole);
		assertEquals(id, accountRole.getId());
	}
}