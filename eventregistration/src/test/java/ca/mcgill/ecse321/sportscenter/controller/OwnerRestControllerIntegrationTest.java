package ca.mcgill.ecse321.sportscenter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportscenter.dto.OwnerDto;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Owner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OwnerRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private OwnerRepository ownerRepo;

    private Account createDefaultPerson() {
        return accountRepo.save(new Account("foo", "bar", "foo@bar.com", "password123A!"));
    }
    
    private Owner createDefaultOwner() {
        return ownerRepo.save(new Owner(createDefaultPerson()));
    }

    @BeforeEach
    @AfterEach
    public void clearDb() {
        ownerRepo.deleteAll();
        accountRepo.deleteAll();
    }


    @Test
    public void testCreateOwner() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = "Obi-Wan";
        String lastName = "Kenobi";
        String email = "obi-wan@example.com";
        String password = "Test1234!";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<OwnerDto> response = client.postForEntity("/owner/{firstName}", requestBody, OwnerDto.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        Account ownerAccount = accountRepo.findAccountByEmail(response.getBody().getAccountEmail());
        assertEquals(firstName, ownerAccount.getFirstName(), "Response has correct first name");
        assertEquals(lastName, ownerAccount.getLastName(), "Response has correct last name");
        assertEquals(email, ownerAccount.getEmail(), "Response has correct email");
        assertEquals(password, ownerAccount.getPassword(), "Response has correct password");
    }


    @Test
    public void testCreateOwnerInvalidFirstName() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = "";
        String lastName = "Kenobi";
        String email = "obi-wan@example.com";
        String password = "Test1234!";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<String> response = client.postForEntity("/owner/{firstName}", requestBody, String.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
    
    }

    @Test
    public void testCreateOwnerNullFirstName() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = null;
        String lastName = "Kenobi";
        String email = "obi-wan@example.com";
        String password = "Test1234!";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<String> response = client.postForEntity("/owner/{firstName}", requestBody, String.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
    
    }

    @Test
    public void testCreateOwnerNullLastName() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = "Tony";
        String lastName = null;
        String email = "obi-wan@example.com";
        String password = "Test1234!";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<String> response = client.postForEntity("/owner/{firstName}", requestBody, String.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    
    }

    @Test
    public void testCreateOwnerInvalidLastName() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = "Tony";
        String lastName = "";
        String email = "obi-wan@example.com";
        String password = "Test1234!";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<String> response = client.postForEntity("/owner/{firstName}", requestBody, String.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    
    }

    @Test
    public void testCreateOwnerNullEmail() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = "Marc-Antoine";
        String lastName = "Nadeau";
        String email = null;
        String password = "Test1234!";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<String> response = client.postForEntity("/owner/{firstName}", requestBody, String.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    
    }
    
    @Test
    public void testCreateOwnerInvalidEmail() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = "Obi-Wan";
        String lastName = "Kenobi";
        String email = "@example.com";
        String password = "Test1234!";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<String> response = client.postForEntity("/owner/{firstName}", requestBody, String.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    
    @Test
    public void testCreateOwnerInvalidPassword() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = "Obi-Wan";
        String lastName = "Kenobi";
        String email = "obi-wan@example.com";
        String password = "test1234";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<String> response = client.postForEntity("/owner/{firstName}", requestBody, String.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    
    }
    @Test
    public void testCreateOwnerNullPassword() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = "Obi-Wan";
        String lastName = "Kenobi";
        String email = "obi-wan@example.com";
        String password = null;

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<String> response = client.postForEntity("/owner/{firstName}", requestBody, String.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    
    }

    @Test
    public void testCreateDuplicateOwner() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        Owner owner = createDefaultOwner();

        String firstName = "foo";
        String lastName = "bar";
        String email = "foo@bar.com";
        String password = "password123A!";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<String> response = client.postForEntity("/owner/{firstName}", requestBody, String.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");

    }
}
