package ca.mcgill.ecse321.sportscenter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CardRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.dao.PayPalRepository;
import ca.mcgill.ecse321.sportscenter.dto.CardDto;
import ca.mcgill.ecse321.sportscenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportscenter.dto.InstructorDto;
import ca.mcgill.ecse321.sportscenter.dto.PaypalDto;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Instructor;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerRestControllerIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private PayPalRepository payPalRepository;

    @Autowired
    private CardRepository cardRepository;

    @AfterEach
    public void clearDatabase() {
        payPalRepository.deleteAll();
        cardRepository.deleteAll();
        instructorRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    private Customer createDefaultCustomer(){
        Account account = new Account("Rehean", "Thillai", "thillai@gmail.com", "Test1234!");
        Customer customer = new Customer(true, account);
        accountRepository.save(account);
        customerRepository.save(customer);
        return customer;
    }

    private Instructor createDefaultInstructor(){
        Account account = new Account("Rehean", "Thillai", "thillai@gmail.com", "Test1234!");
        Customer customer = new Customer(true, account);
        Instructor instructor = new Instructor(account);
        accountRepository.save(account);
        customerRepository.save(customer);
        instructorRepository.save(instructor);
        return instructor;
    }

    @Test
    public void testCreateAndGetCustomer() {
        String email = testCreateCustomer();
        testGetCustomer(email);
    }
    @Test
    private String testCreateCustomer() {
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
        Account customerAccount = accountRepository.findAccountByEmail(response.getBody().getAccountEmail());
		assertEquals("Rehean", customerAccount.getFirstName(), "Response has correct name");
        assertEquals("Thillai", customerAccount.getLastName());
        assertEquals("rehean@gmail.com", customerAccount.getEmail());
        assertEquals("Test1234!", customerAccount.getPassword());
        assertEquals(true, response.getBody().getWantsEmailConfirmation());
		return customerAccount.getEmail();
	}
    @Test
    private void testGetCustomer(String email) {
		ResponseEntity<CustomerDto> response = client.getForEntity("/customer/" + email, CustomerDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
        Account customerAccount = accountRepository.findAccountByEmail(response.getBody().getAccountEmail());
		assertEquals("Rehean", customerAccount.getFirstName(), "Response has correct name");
        assertEquals("Thillai", customerAccount.getLastName());
        assertEquals("rehean@gmail.com", customerAccount.getEmail());
        assertEquals("Test1234!", customerAccount.getPassword());
        assertEquals(true, response.getBody().getWantsEmailConfirmation());
	}

    @Test
    public void testGetCustomerNonExisting() {
        String email = "nobody@gmail.com";
		ResponseEntity<String> response = client.getForEntity("/customer/" + email, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
	}

    @Test
	public void testCreateInvalidCustomer() {
		String urlTemplate = UriComponentsBuilder.fromPath("/customer/{firstName}")
            .queryParam("lastName", "Thillai")
            .queryParam("email", "rehean@gmail.com")
            .queryParam("password", "INVALIDPASSWORD")
            .queryParam("wantsEmailConfirmation", true)
            .encode()
            .toUriString();
            ResponseEntity<String> response = client.postForEntity(urlTemplate, null, String.class, "Rehean");

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
	}

    @Test
	public void testCreateExistingCustomer() {
        Customer customer = createDefaultCustomer();
		String urlTemplate = UriComponentsBuilder.fromPath("/customer/{firstName}")
            .queryParam("lastName", "Thillai")
            .queryParam("email", customer.getAccount().getEmail())
            .queryParam("password", "INVALIDPASSWORD")
            .queryParam("wantsEmailConfirmation", true)
            .encode()
            .toUriString();
            ResponseEntity<String> response = client.postForEntity(urlTemplate, null, String.class, "Rehean");

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
	}


    @Test
    public void testPromoteCustomer() {
        Customer customer = createDefaultCustomer();
        String urlTemplate = UriComponentsBuilder.fromPath("/promote/{email}")
            .encode()
            .toUriString();
		ResponseEntity<InstructorDto> response = client.postForEntity(urlTemplate, null, InstructorDto.class, "thillai@gmail.com");
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
        Account customerAccount = accountRepository.findAccountByEmail(response.getBody().getAccountEmail());
		assertNotNull(response.getBody(), "Response has body");
        assertEquals(customerAccount.getFirstName(), customer.getAccount().getFirstName());
		
    }
    

    @Test
	public void testPromoteAlreadyInstructor() {
        Instructor instructor  = createDefaultInstructor();
        String urlTemplate = UriComponentsBuilder.fromPath("/promote/{email}")
        .encode()
        .toUriString();
        ResponseEntity<String> response = client.postForEntity(urlTemplate, null, String.class, instructor.getAccount().getEmail());

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
	}

    @Test
	public void testPromoteNonExistingCustomer() {
        String urlTemplate = UriComponentsBuilder.fromPath("/promote/{email}")
        .encode()
        .toUriString();
        ResponseEntity<String> response = client.postForEntity(urlTemplate, null, String.class, "random@gmail.com");

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
	}



    @Test
    public void addPayPal() {
        Customer customer = createDefaultCustomer();
        String urlTemplate = UriComponentsBuilder.fromPath("/paypal/add")
            .queryParam("accountName", "my paypal")
            .queryParam("customerEmail", customer.getAccount().getEmail())
            .queryParam("paypalEmail", "random@gmail.com")
            .queryParam("paypalPassword", "passwordWOW")
            .encode()
            .toUriString();
		ResponseEntity<PaypalDto> response = client.postForEntity(urlTemplate, null, PaypalDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");

		assertEquals(customer.getAccount().getEmail(), response.getBody().getCustomerAccountEmail(), "Response has correct name");
        }

     @Test
    public void addInvalidPayPal() {
        String urlTemplate = UriComponentsBuilder.fromPath("/paypal/add")
            .queryParam("accountName", "my paypal")
            .queryParam("customerEmail", "invalidEmail@gmail.com")
            .queryParam("paypalEmail", "random@gmail.com")
            .queryParam("paypalPassword", "passwordWOW")
            .encode()
            .toUriString();
		ResponseEntity<String> response = client.postForEntity(urlTemplate, null, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
        }

    @Test
    public void addCard() {
        Customer customer = createDefaultCustomer();
        String urlTemplate = UriComponentsBuilder.fromPath("/card/add")
            .queryParam("accountName", "my debit card")
            .queryParam("customerEmail", customer.getAccount().getEmail())
            .queryParam("paymentCardType", "DebitCard")
            .queryParam("cardNumber", "123456789")
            .queryParam("expirationDate", "1223")
            .queryParam("ccv", "123")
            .encode()
            .toUriString();
		ResponseEntity<CardDto> response = client.postForEntity(urlTemplate, null, CardDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals(customer.getAccount().getEmail(), response.getBody().getCustomerAccountEmail(), "Response has correct name");
        }

    @Test
    public void addInvalidCard() {
        String urlTemplate = UriComponentsBuilder.fromPath("/card/add")
            .queryParam("accountName", "my debit card")
            .queryParam("customerEmail", "invalid@gmail.com")
            .queryParam("paymentCardType", "DebitCard")
            .queryParam("cardNumber", "123456789")
            .queryParam("expirationDate", "1223")
            .queryParam("ccv", "123")
            .encode()
            .toUriString();
		ResponseEntity<String> response = client.postForEntity(urlTemplate, null, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
        }

}
