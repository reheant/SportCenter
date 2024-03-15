package ca.mcgill.ecse321.sportscenter.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
//import ca.mcgill.ecse321.sportscenter.dao.customerAccount;
import ca.mcgill.ecse321.sportscenter.dto.CustomerDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach 
    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetCustomer() {
        String email = testCreateCustomer();
        testGetCustomer(email);
    }

    private String testCreateCustomer() {
        String firstName = "Rehean";
        String urlTemplate = UriComponentsBuilder.fromPath("/customer/{firstName}")
            .queryParam("lastName", "Thillai")
            .queryParam("email", "rehean@gmail.com")
            .queryParam("password", "Test1234!")
            .queryParam("wantsEmailConfirmation", true)
            .encode()
            .toUriString();
		ResponseEntity<CustomerDto> response = client.postForEntity(urlTemplate, null, CustomerDto.class, "Rehean");

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
        
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("Rehean", response.getBody().getFirstName(), "Response has correct name");
        assertEquals("Thillai", response.getBody().getLastName());
        assertEquals("rehean@gmail.com", response.getBody().getEmail());
        assertEquals("Test1234!", response.getBody().getPassword());
        assertEquals(true, response.getBody().getWantsEmailConfirmation());
		return response.getBody().getEmail();
	}

    private void testGetCustomer(String email) {
		ResponseEntity<CustomerDto> response = client.getForEntity("/customer/" + email, CustomerDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("Rehean", response.getBody().getFirstName(), "Response has correct name");
        assertEquals("Thillai", response.getBody().getLastName());
        assertEquals("rehean@gmail.com", response.getBody().getEmail());
        assertEquals("Test1234!", response.getBody().getPassword());
        assertEquals(true, response.getBody().getWantsEmailConfirmation());
	}

    // @Test
	// public void testCreateInvalidCustomer() {
	// 	String urlTemplate = UriComponentsBuilder.fromPath("/customer/{firstName}")
    //         .queryParam("lastName", "Thillai")
    //         .queryParam("email", "rehean@gmail.com")
    //         .queryParam("password", "INVALIDPASSWORD")
    //         .queryParam("wantsEmailConfirmation", true)
    //         .encode()
    //         .toUriString();
    //         ResponseEntity<CustomerDto> response = client.postForEntity(urlTemplate, null, CustomerDto.class, "Rehean");

	// 	assertNotNull(response);
	// 	assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
	// }


    @Test
    public void testPromoteCustomer() {
        String email = testCreateCustomer();
        testGetCustomer(email);
    }



}
