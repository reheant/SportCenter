package ca.mcgill.ecse321.sportscenter.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Owner;



@ExtendWith(MockitoExtension.class)
public class TestOwnerService {

    @Mock
    private OwnerRepository ownerRepository;
    @Mock 
    private AccountRepository accountRepository;

    @InjectMocks
    private OwnerService courseService; 


    // For Owner
    private static final String firstName = "Rehean";
    private static final String lastName = "Thillai";
    private static final String email = "rehean.thillai@gmail.com";
    private static final String password = "Test1234!";

    private final Owner owner = new Owner();
    private final Account ownerAccount = new Account();

    

    @BeforeEach
    public void setMockOutput() {
        lenient().when(accountRepository.findAccountByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(email)) {
                ownerAccount.setEmail(email);
                ownerAccount.setFirstName(firstName);
                ownerAccount.setLastName(lastName);
                ownerAccount.setPassword(password);
                owner.setAccount(ownerAccount);
            
                return ownerAccount;
            } else {
                return null;
            }
        });
    
        lenient().when(ownerRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            ownerAccount.setEmail(email);
            ownerAccount.setFirstName(firstName);
            ownerAccount.setLastName(lastName);
            ownerAccount.setPassword(password);
            owner.setAccount(ownerAccount);
            List<Owner> ownerList = new ArrayList<>();
            ownerList.add(owner);
            return ownerList;

        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(ownerRepository.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);

    }

    @Test
    public void testCreateOwner(){

        String ownerFirstName = "Rehean";
        String ownerLastName = "Thillai";
        String ownerEmail = "reh@gmail.com";
        String ownerPassword = "Test1234!";
        Owner owner = null;
        Account ownerAccount = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);
            ownerAccount = owner.getAccount();
        } catch (Exception error) {
            fail(error.getMessage());
        }

        assertNotNull(ownerAccount);
        assertNotNull(owner);
        assertNotNull(accountRepository.findAccountByEmail(email));
        assertEquals(ownerFirstName, ownerAccount.getFirstName());
        assertEquals(ownerLastName, ownerAccount.getLastName());
        assertEquals(ownerEmail, ownerAccount.getEmail());
        assertEquals(ownerPassword, ownerAccount.getPassword());

    }
    
    @Test
    public void testCreateOwnerFirstNameNull(){

        String ownerFirstName = null;
        String ownerLastName = "Thillai";
        String ownerEmail = "reh@gmail.com";
        String ownerPassword = "Test1234!";
        Owner owner = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);

        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(owner);
    }
    @Test
    public void testCreateOwnerLastNameNull(){

        String ownerFirstName = "rehean";
        String ownerLastName = null;
        String ownerEmail = "reh@gmail.com";
        String ownerPassword = "Test1234!";
        Owner owner = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);

        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(owner);
    }
    @Test
    public void testCreateOwnerEmailNull(){

        String ownerFirstName = "rehean";
        String ownerLastName = "Thillai";
        String ownerEmail = null;
        String ownerPassword = "Test1234!";
        Owner owner = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);

        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(owner);
    }
    @Test
    public void testCreateOwnerPasswordNull(){

        String ownerFirstName = "rehean";
        String ownerLastName = "Thillai";
        String ownerEmail = "reh@gmail.com";
        String ownerPassword = null;
        Owner owner = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);

        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(owner);
    }

    @Test
    public void testCreateOwnerNulls(){

        String ownerFirstName = null;
        String ownerLastName = null;
        String ownerEmail = null;
        String ownerPassword = null;
        Owner owner = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);

        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(owner);
    }

    @Test
    public void testCreateDuplicateOwner() {
        String ownerFirstName = "Rehean";
        String ownerLastName = "Thillai";
        String ownerEmail = "rehean.thillai@gmail.com";
        String ownerPassword = "Test1234!";
        Owner owner = null;
        Account ownerAccount = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);
            ownerAccount = owner.getAccount();
        } catch (Exception error) {
            assertEquals("Email is already in use", error.getMessage());
        }
        assertNull(owner);
    }

    @Test
    public void testInvalidFirstName() {
        String ownerFirstName = "Sarasvatha12";
        String ownerLastName = "Thillai";
        String ownerEmail = "Sarasvatha@gmail.com";
        String ownerPassword = "Test1234!";
        Owner owner = null;
        Account ownerAccount = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);
            ownerAccount = owner.getAccount();
        } catch (Exception error) {
            assertEquals("Invalid first name format", error.getMessage());
        }
        assertNull(owner);
    }

    @Test
    public void testInvalidLastName() {
        String ownerFirstName = "Sarasvatha";
        String ownerLastName = "Thillai123#";
        String ownerEmail = "Sarasvatha@gmail.com";
        String ownerPassword = "Test1234!";
        Owner owner = null;
        Account ownerAccount = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);
            ownerAccount = owner.getAccount();
        } catch (Exception error) {
            assertEquals("Invalid last name format", error.getMessage());
        }
        assertNull(owner);
    }

    @Test
    public void testInvalidEmailFormat() {
        String ownerFirstName = "ray";
        String ownerLastName = "Thillai";
        String ownerEmail = "ray thillai@gmail";
        String ownerPassword = "Test1234!";
        Owner owner = null;
        Account ownerAccount = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);
            ownerAccount = owner.getAccount();
        } catch (Exception error) {
            assertEquals("Invalid email format", error.getMessage());
        }
        assertNull(owner);
    }

    @Test
    public void testInvalidPassword() {
        String ownerFirstName = "ray";
        String ownerLastName = "Thillai";
        String ownerEmail = "raythillai@gmail";
        String ownerPassword = "Chickenmaster123";
        Owner owner = null;
        Account ownerAccount = null;

        try {
            owner = courseService.createOwner(ownerFirstName, ownerLastName, ownerEmail, ownerPassword);
            ownerAccount = owner.getAccount();
        } catch (Exception error) {
            assertEquals( "Invalid password format, password must have at least: one lower case letter, one higher case letter, one digit, one special character and be 8 charcters minimum", error.getMessage());
        }
        assertNull(owner);
    }
}
