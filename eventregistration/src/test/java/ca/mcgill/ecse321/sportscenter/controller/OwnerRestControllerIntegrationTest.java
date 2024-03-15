package ca.mcgill.ecse321.sportscenter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hibernate.usertype.UserType;
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

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.dto.OwnerDto;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.Owner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OwnerRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private OwnerRepository ownerRepo;

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
        assertEquals(firstName, response.getBody().getFirstName(), "Response has correct first name");
        assertEquals(lastName, response.getBody().getLastName(), "Response has correct last name");
        assertEquals(email, response.getBody().getEmail(), "Response has correct email");
        assertEquals(password, response.getBody().getPassword(), "Response has correct password");
    }


    @Test
    public void testCreateOwnerWrongPassword() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = "Obi-Wan";
        String lastName = "Kenobi";
        String email = "obi-wan@example.com";
        String password = "test1234";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<OwnerDto> response = client.postForEntity("/owner/{firstName}", requestBody, OwnerDto.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    
    }
    @Test
    public void testCreateOwnerWrongEmail() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        String firstName = "Obi-Wan";
        String lastName = "Kenobi";
        String email = "@example.com";
        String password = "Test1234!";

        requestBody.add("lastName", lastName);
        requestBody.add("email", email);
        requestBody.add("password", password);

        ResponseEntity<OwnerDto> response = client.postForEntity("/owner/{firstName}", requestBody, OwnerDto.class, firstName);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    
    }
}
