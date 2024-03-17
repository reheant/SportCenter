package ca.mcgill.ecse321.sportscenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private String demoCustomerEmail = "jimbob@gmail.com";

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
        Integer sessionId = createAndSaveClassesForRegistration();
        String urlTemplate = UriComponentsBuilder.fromPath("/registration/" + demoCustomerEmail)
                .queryParam("sessionId", sessionId)
                .encode()
                .toUriString();
        ResponseEntity<RegistrationDto> postResponse = client.postForEntity(urlTemplate, null, RegistrationDto.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
    }
    

    private Integer createAndSaveClassesForRegistration() {
        Course course = new Course();
        course.setName("Sample-Course");
        courseRepository.save(course);
        Location location = new Location();
        location.setName("Sample-Location");
        locationRepository.save(location);
        Session session = new Session();
        session.setCourse(course);
        session.setLocation(location);
        sessionRepository.save(session);

        Account account = new Account();
        account.setEmail(demoCustomerEmail);
        accountRepository.save(account);
        Customer customer = new Customer();
        customer.setAccount(account);
        customerRepository.save(customer);

        return session.getId();
    }

}
