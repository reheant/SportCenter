package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Location;
import ca.mcgill.ecse321.sportscenter.model.Registration;
import ca.mcgill.ecse321.sportscenter.model.Session;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;

@SpringBootTest
public class RegistrationRepositoryTests {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private RegistrationRepository registrationRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private SessionRepository sessionRepository;

	@AfterEach
	public void clearDatabase() {
		registrationRepository.deleteAll();
		sessionRepository.deleteAll();
		locationRepository.deleteAll();
		customerRepository.deleteAll();
		accountRepository.deleteAll();
		courseRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadRegistration() {

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

		// create a location
		String name = "Ana";
		int capacity = 44;
		Time openingTime = Time.valueOf("08:00:00");
		Time closingTime = Time.valueOf("12:00:00");
		Location location = new Location(name, capacity, openingTime, closingTime);
		// Load location
		locationRepository.save(location);

		// create a course
		String courseName = "boring class";
		String description = "this class is boring";
		CourseStatus isApproved = CourseStatus.Approved;
		boolean requiresInstructor = true;
		float duration = 60;
		float cost = 23;
		// load course
		Course course = new Course(courseName, description, isApproved, requiresInstructor, duration, cost);
		courseRepository.save(course);

		// create a session
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("12:00:00");
		Session session = new Session(startTime, endTime, course, location);
		// load session
		sessionRepository.save(session);

		// Create registration.
		Registration registration = new Registration(customer, session);
		// Save registration.
		registrationRepository.save(registration);

		// Read registration from database.
		registration = registrationRepository.findById(registration.getId()).orElse(null);
		// Assert that registration is not null and has correct attributes.
		assertNotNull(registration);
		assertEquals(session.getId(), registration.getSession().getId());
		assertEquals(customer.getId(), registration.getCustomer().getId());
		assertEquals(customer.getAccount().getFirstName(), registration.getCustomer().getAccount().getFirstName());
		assertEquals(customer.getAccount().getLastName(), registration.getCustomer().getAccount().getLastName());
		assertEquals(customer.getAccount().getEmail(), registration.getCustomer().getAccount().getEmail());
		assertEquals(customer.getAccount().getId(), registration.getCustomer().getAccount().getId());
		assertEquals(customer.getAccount().getPassword(), registration.getCustomer().getAccount().getPassword());
		assertEquals(course.getName(), registration.getSession().getCourse().getName());
		assertEquals(course.getCost(), registration.getSession().getCourse().getCost());
		assertEquals(course.getDescription(), registration.getSession().getCourse().getDescription());
		assertEquals(course.getIsApproved(), registration.getSession().getCourse().getIsApproved());
		assertEquals(course.getRequiresInstructor(), registration.getSession().getCourse().getRequiresInstructor());
		assertEquals(location.getClosingTime(), registration.getSession().getLocation().getClosingTime());
		assertEquals(location.getOpeningTime(), registration.getSession().getLocation().getOpeningTime());
		assertEquals(location.getId(), registration.getSession().getLocation().getId());
		assertEquals(location.getName(), registration.getSession().getLocation().getName());
		assertEquals(location.getCapacity(), registration.getSession().getLocation().getCapacity());
		assertEquals(session.getEndTime(), registration.getSession().getEndTime());
	}
}
