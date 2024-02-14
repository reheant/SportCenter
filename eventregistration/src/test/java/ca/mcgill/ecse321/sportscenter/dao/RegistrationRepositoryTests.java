package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
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
import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportscenter.dao.LocationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;


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
		courseRepository.deleteAll();
		customerRepository.deleteAll();
		accountRepository.deleteAll();
		sessionRepository.deleteAll();
		locationRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadRegistration() {
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


		//create a location
        int locationID = 4 ;
        String name =  "Ana" ;
        int capacity = 44;
        
        Time openingTime = Time.valueOf("08:00:00");
		Time closingTime = Time.valueOf("12:00:00");

        Location location = new Location(locationID, name, capacity, openingTime, closingTime);
        locationRepository.save(location);

		//create a course
        int courseID = 23;
        String courseName = "boring class";
        String description = "this class is boring";
        boolean isApproved = true;
        boolean requiresInstructor = true;
        float duration = 60;
        float cost = 23;  
		//load course
        Course course = new Course(courseID, courseName, description, isApproved, requiresInstructor, duration, cost);
        courseRepository.save(course);

        //create a session
        int sessionID = 3;
        Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("12:00:00");

        Session session = new Session(sessionID, startTime, endTime, course, location);
        // load session
        sessionRepository.save(session);


		// Create and persist course.
		
		int registrationID = 23;
		// Create registration.
		Registration registration = new Registration(registrationID, customer, session);
		// Save registration.
		registrationRepository.save(registration);


		// Read registration from database.
		registration = registrationRepository.findById(registrationID).orElse(null);

		assertNotNull(registration);
		assertNotNull(registration.getId());
		assertEquals(customer.getAccount().getFirstName(), registration.getCustomer().getAccount().getFirstName());
		assertEquals(customer.getAccount().getLastName(), registration.getCustomer().getAccount().getLastName());
		assertEquals(customer.getAccount().getEmail(), registration.getCustomer().getAccount().getEmail());
		assertEquals(customer.getAccount().getId(), registration.getCustomer().getAccount().getId());
		assertEquals(customer.getAccount().getPassword(), registration.getCustomer().getAccount().getPassword());
		assertEquals(customer.getAccount().getRoles(), registration.getCustomer().getAccount().getRoles());
		assertEquals(course.getName(), registration.getSession().getCourse().getName());
		assertEquals(course.getCost(), registration.getSession().getCourse().getCost());
		assertEquals(course.getId(), registration.getSession().getCourse().getId());
		assertEquals(course.getDescription(), registration.getSession().getCourse().getDescription());
		assertEquals(course.getIsApproved(), registration.getSession().getCourse().getIsApproved());
		assertEquals(course.getName(), registration.getSession().getCourse().getName());
		assertEquals(course.getRequiresInstructor(), registration.getSession().getCourse().getRequiresInstructor());
		assertEquals(location.getClosingTime(), registration.getSession().getLocation().getClosingTime());
		assertEquals(location.getOpeningTime(), registration.getSession().getLocation().getOpeningTime());
		assertEquals(location.getSessions(), registration.getSession().getLocation().getSessions());
		assertEquals(location.getId(), registration.getSession().getLocation().getId());
		assertEquals(location.getName(), registration.getSession().getLocation().getName());
		assertEquals(location.getCapacity(), registration.getSession().getLocation().getCapacity());
	}
}
