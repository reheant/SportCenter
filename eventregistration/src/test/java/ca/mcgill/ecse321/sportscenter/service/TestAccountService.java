package ca.mcgill.ecse321.sportscenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import javax.naming.AuthenticationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.dto.utilities.UserType;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.Owner;

@ExtendWith(MockitoExtension.class)
public class TestAccountService {
    @Mock
    private AccountRepository accountDao;

    @Mock
    private OwnerRepository ownerDao;

    @Mock
    private CustomerRepository customerDao;

    @Mock
    private InstructorRepository instructorDao;

    @InjectMocks
    private AccountService service;

    @AfterEach
    public void resetMocks() {
        Mockito.reset(accountDao);
        Mockito.reset(ownerDao);
        Mockito.reset(customerDao);
        Mockito.reset(instructorDao);
    }

    @Test
    public void testSuccessfulAuthOwner() {
        final int id = 1;
        final String email = "foo@bar.com";
        final String password = "password";
        final Account testAccount = new Account("foo", "bar", email, password);
        testAccount.setId(id);

        when(accountDao.findByEmail(email)).thenAnswer(InvocationOnMock -> testAccount);
        when(ownerDao.findByAccount(testAccount)).thenAnswer(InvocationOnMock -> new Owner());

        int testId = 0;
        try {
            testId = service.authenticate(email, password, UserType.Owner);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertEquals(id, testId);
    }

    @Test
    public void testSuccessfulAuthCustomer() {
        final int id = 1;
        final String email = "foo@bar.com";
        final String password = "password";
        final Account testAccount = new Account("foo", "bar", email, password);
        testAccount.setId(id);

        when(accountDao.findByEmail(email)).thenAnswer(InvocationOnMock -> testAccount);
        when(customerDao.findByAccount(testAccount)).thenAnswer(InvocationOnMock -> new Customer());

        int testId = 0;
        try {
            testId = service.authenticate(email, password, UserType.Customer);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertEquals(id, testId);
    }

    @Test
    public void testSuccessfulAuthInstructor() {
        final int id = 1;
        final String email = "foo@bar.com";
        final String password = "password";
        final Account testAccount = new Account("foo", "bar", email, password);
        testAccount.setId(id);

        when(accountDao.findByEmail(email)).thenAnswer(InvocationOnMock -> testAccount);
        when(instructorDao.findByAccount(testAccount))
                .thenAnswer(InvocationOnMock -> new Instructor());

        int testId = 0;

        try {
            testId = service.authenticate(email, password, UserType.Instructor);
        } catch (Exception e) {
            fail("Unexpected exception");
        }

        assertEquals(id, testId);
    }

    @Test
    public void testWrongPassword() {
        final int id = 1;
        final String email = "foo@bar.com";
        final String password = "password";
        final Account testAccount = new Account("foo", "bar", email, password);
        testAccount.setId(id);

        when(accountDao.findByEmail(email)).thenAnswer(InvocationOnMock -> testAccount);

        assertThrows(AuthenticationException.class,
                () -> service.authenticate(email, "wrong password", UserType.Instructor));
    }

    @Test
    public void testWrongEmail() {
        final String wrongEmail = "wrong@email.com";

        when(accountDao.findByEmail(wrongEmail)).thenAnswer(InvocationOnMock -> null);

        assertThrows(AuthenticationException.class,
                () -> service.authenticate(wrongEmail, "", UserType.Instructor));
    }

    @Test
    public void testWrongRole() {
        final int id = 1;
        final String email = "foo@bar.com";
        final String password = "password";
        final Account testAccount = new Account("foo", "bar", email, password);
        testAccount.setId(id);

        when(accountDao.findByEmail(email)).thenAnswer(InvocationOnMock -> testAccount);
        when(instructorDao.findByAccount(testAccount)).thenAnswer(InvocationOnMock -> null);

        assertThrows(AuthenticationException.class,
                () -> service.authenticate(email, password, UserType.Instructor));
    }
}
