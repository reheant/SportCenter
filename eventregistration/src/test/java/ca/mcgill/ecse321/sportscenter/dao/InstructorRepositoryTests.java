
package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Instructor;

@SpringBootTest
public class InstructorRepositoryTests {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private InstructorRepository instructorRepository;

	@AfterEach
	public void clearDatabase() {
		accountRepository.deleteAll();
		instructorRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadInstructor() {
		// Create account.
		String firstName = "Muffin";
		String lastName = "Man";
		String email = "Man@gmail.com";
		String password = "123456";
		Account account = new Account(firstName, lastName, email, password);


		// Load account
		accountRepository.save(account);

		// Create Instructor
		Instructor instructor = new Instructor(account);
		// Load instructor
		instructorRepository.save(instructor);

		// Read account from database.
		Instructor dbInstructor = instructorRepository.findById(instructor.getId()).orElse(null);

		// Assert that Instructor is not null and has correct attributes.
		assertNotNull(dbInstructor);
		assertEquals(dbInstructor.getAccount().getId(), account.getId());
		assertEquals(account.getId(), dbInstructor.getAccount().getId());
		assertEquals(account.getId(), dbInstructor.getId());
		assertEquals(account.getEmail(), dbInstructor.getAccount().getEmail());
		assertEquals(account.getFirstName(), dbInstructor.getAccount().getFirstName());
		assertEquals(account.getLastName(), dbInstructor.getAccount().getLastName());
		assertEquals(account.getPassword(), dbInstructor.getAccount().getPassword());


	}
}
