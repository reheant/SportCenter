package ca.mcgill.ecse321.sportscenter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.dto.LoginRequestDto;
import ca.mcgill.ecse321.sportscenter.dto.LoginResponseDto;
import ca.mcgill.ecse321.sportscenter.dto.utilities.UserType;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.Owner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestAuthenticationRestController {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private InstructorRepository instructorRepo;

    @Autowired
    private OwnerRepository ownerRepo;

    @AfterEach
    public void clearDb() {
        customerRepo.deleteAll();
        instructorRepo.deleteAll();
        ownerRepo.deleteAll();
        accountRepo.deleteAll();
    }

    private Account createDefaultPerson() {
        return accountRepo.save(new Account("foo", "bar", "foo@bar.com", "password"));
    }

    @Test
    public void successfulCustomerAuth() {
        Account account = createDefaultPerson();
        customerRepo.save(new Customer(false, account));

        ResponseEntity<LoginResponseDto> res = client.postForEntity("/login",
                new LoginRequestDto(account.getEmail(), account.getPassword(), UserType.Customer),
                LoginResponseDto.class);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(account.getId(), res.getBody().getId());
    }

    @Test
    public void successfulInstructorAuth() {
        Account account = createDefaultPerson();
        instructorRepo.save(new Instructor(account));

        ResponseEntity<LoginResponseDto> res = client.postForEntity("/login",
                new LoginRequestDto(account.getEmail(), account.getPassword(), UserType.Instructor),
                LoginResponseDto.class);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(account.getId(), res.getBody().getId());
    }

    @Test
    public void successfulOwnerAuth() {
        Account account = createDefaultPerson();
        ownerRepo.save(new Owner(account));

        ResponseEntity<LoginResponseDto> res = client.postForEntity("/login",
                new LoginRequestDto(account.getEmail(), account.getPassword(), UserType.Owner),
                LoginResponseDto.class);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(account.getId(), res.getBody().getId());
    }

    @Test
    public void incorrectEmailAuth() {
        ResponseEntity<String> res = client.postForEntity("/login",
                new LoginRequestDto("foo@bar.com", "password", UserType.Owner), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void invalidRole() {
        Account account = createDefaultPerson();
        ResponseEntity<String> res = client.postForEntity("/login",
                new LoginRequestDto(account.getEmail(), account.getPassword(), UserType.Owner),
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void incorrectPasswordAuth() {
        Account account = createDefaultPerson();
        customerRepo.save(new Customer(false, account));
        ResponseEntity<String> res = client.postForEntity("/login",
                new LoginRequestDto(account.getEmail(), "wrong password", UserType.Customer),
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }
}
