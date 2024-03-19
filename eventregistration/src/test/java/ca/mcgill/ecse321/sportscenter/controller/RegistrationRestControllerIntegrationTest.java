package ca.mcgill.ecse321.sportscenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.LocationRepository;
import ca.mcgill.ecse321.sportscenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;

import ca.mcgill.ecse321.sportscenter.dto.RegistrationDto;

import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Location;
import ca.mcgill.ecse321.sportscenter.model.Registration;
import ca.mcgill.ecse321.sportscenter.model.Session;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired 
    private AccountRepository accountRepository;

    @Autowired 
    private CustomerRepository customerRepository;


    private String email = "jimbob@gmail.com";

    private Account account = new Account();
    private Customer customer = new Customer();
    private Location location = new Location();
    private Course course = new Course();
    private Session session = new Session();
    private Registration registration = new Registration();

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        registrationRepository.deleteAll();
        sessionRepository.deleteAll();
        courseRepository.deleteAll();
        locationRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testRegister() {
        createAndSaveClassesForRegistration();
        String urlTemplate = UriComponentsBuilder.fromPath("/registration/")
            .queryParam("email", email)    
            .queryParam("sessionId", session.getId())
            .encode()
            .toUriString();
        ResponseEntity<RegistrationDto> postResponse = client.postForEntity(urlTemplate, null, RegistrationDto.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
    }

    @Test
    public void testRegisterCustomerNotFound() {
        createAndSaveClassesForRegistration();
        String urlTemplate = UriComponentsBuilder.fromPath("/registration/")
            .queryParam("email", "totallyNotJimBob@yahoo.ca")
            .queryParam("sessionId", session.getId())
            .encode()
            .toUriString();
        ResponseEntity<String> postResponse = client.postForEntity(urlTemplate, null, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, postResponse.getStatusCode());
    }

    @Test
    public void testRegisterSessionNotFound() {
        createAndSaveClassesForRegistration();
        String urlTemplate = UriComponentsBuilder.fromPath("/registration/")
            .queryParam("email", email)
            .queryParam("sessionId", 31415)
            .encode()
            .toUriString();
        ResponseEntity<String> postResponse = client.postForEntity(urlTemplate, null, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, postResponse.getStatusCode());
    }

    @Test
    public void testUnregister() {
        createTestRegistration();
        assertTrue(registrationRepository.findById(registration.getId()).isPresent());
        String urlTemplate = UriComponentsBuilder.fromPath("/unregister/")
            .queryParam("email", email)
            .queryParam("sessionId", session.getId())
            .encode()
            .toUriString();
        client.delete(urlTemplate);
        assertFalse(sessionRepository.findById(registration.getId()).isPresent());
    }
    
    private void createTestRegistration() {
        createAndSaveClassesForRegistration();
        registration.setCustomer(customer);
        registration.setSession(session);
        registrationRepository.save(registration);
    }

    private void createAndSaveClassesForRegistration() {
        course.setName("Sample-Course");
        courseRepository.save(course);

        location.setName("Sample-Location");
        locationRepository.save(location);

        session.setCourse(course);
        session.setLocation(location);
        sessionRepository.save(session);

        account.setEmail(email);
        accountRepository.save(account);

        customer.setAccount(account);
        customerRepository.save(customer);
    }

}
