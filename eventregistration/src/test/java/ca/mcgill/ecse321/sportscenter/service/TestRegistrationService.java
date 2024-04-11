package ca.mcgill.ecse321.sportscenter.service;

import java.util.List;
import java.util.ArrayList;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportscenter.dao.SessionRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Registration;
import ca.mcgill.ecse321.sportscenter.model.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class TestRegistrationService {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private RegistrationService registrationService;

    // For Customer
    private static final String firstName = "Jim";
    private static final String lastName = "Bob";
    private static final String email = "jim.bob@gmail.com";
    private static final String password = "Str0ngP4ssword";

    private final Customer customer = new Customer();
    private final Account customerAccount = new Account();
    private final Session session = new Session();
    private final Registration registration = new Registration();

    @BeforeEach
    public void setMockOutput() {
        lenient().when(accountRepository.findAccountByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(email)) {
                customerAccount.setEmail(email);
                customerAccount.setFirstName(firstName);
                customerAccount.setLastName(lastName);
                customerAccount.setPassword(password);
                customer.setAccount(customerAccount);

                return customerAccount;
            } else {
                return null;
            }
        });

        lenient().when(customerRepository.findCustomerByAccountEmail(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(email)) {
                        customerAccount.setEmail(email);
                        customerAccount.setFirstName(firstName);
                        customerAccount.setLastName(lastName);
                        customerAccount.setPassword(password);
                        customer.setAccount(customerAccount);

                        return customer;
                    } else {
                        return null;
                    }
                });

        lenient().when(registrationRepository.findRegistrationByCustomerAccountEmailAndSessionId(anyString(), anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(email) && invocation.getArgument(1).equals(session.getId())) {
                        customerAccount.setEmail(email);
                        customerAccount.setFirstName(firstName);
                        customerAccount.setLastName(lastName);
                        customerAccount.setPassword(password);
                        customer.setAccount(customerAccount);

                        registration.setCustomer(customer);
                        registration.setSession(session);

                        return registration;
                    } else {
                        return null;
                    }
                });

        lenient().when(customerRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            customerAccount.setEmail(email);
            customerAccount.setFirstName(firstName);
            customerAccount.setLastName(lastName);
            customerAccount.setPassword(password);
            customer.setAccount(customerAccount);
            List<Customer> customerList = new ArrayList<>();
            customerList.add(customer);

            return customerList;
        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(registrationRepository.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateRegistration() {

        Session session = new Session();
        Registration registration = null;

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);
        ReflectionTestUtils.setField(registrationService, "emailPassword", "dummyPassword");
        ReflectionTestUtils.setField(registrationService, "sendConfirmationEmail", true);
        try {
            registration = registrationService.register(email, session.getId());
        } catch (Exception error) {
            fail(error.getMessage());
        }

        assertNotNull(registration);
        assertEquals(email, registration.getCustomer().getAccount().getEmail());
        assertEquals(session.getId(), registration.getSession().getId());
    }

    @Test
    public void testCreateRegistrationNullEmail() {
        String null_email = null;
        Session session = new Session();
        Registration registration = null;

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);

        try {
            registration = registrationService.register(null_email, session.getId());
        } catch (Exception error) {
            assertEquals("The customer email cannot be null.", error.getMessage());
            assertEquals(NullPointerException.class, error.getClass());
        }
        assertNull(registration);
    }

    @Test
    public void testCreateRegistrationEmptyEmail() {
        String empty_email = "";
        Session session = new Session();
        Registration registration = null;

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);

        try {
            registration = registrationService.register(empty_email, session.getId());
        } catch (Exception error) {
            assertEquals("The customer email cannot be empty.", error.getMessage());
            assertEquals(IllegalArgumentException.class, error.getClass());
        }
        assertNull(registration);
    }

    @Test
    public void testCreateRegistrationAccountNotFound() {
        String wrong_email = "totallyNotJimBob@gmail.com";
        Session session = new Session();
        Registration registration = null;

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);

        try {
            registration = registrationService.register(wrong_email, session.getId());
        } catch (Exception error) {
            assertEquals("Email is not associated to an account.", error.getMessage());
            assertEquals(IllegalArgumentException.class, error.getClass());
        }
        assertNull(registration);
    }

    @Test
    public void testCreateRegistrationCustomerNotFound() {
        Session session = new Session();
        Registration registration = null;

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);
        lenient().when(customerRepository.findCustomerByAccountEmail(anyString())).thenReturn(null);

        try {
            registration = registrationService.register(email, session.getId());
        } catch (Exception error) {
            assertEquals("Account is not associated to a customer.", error.getMessage());
            assertEquals(IllegalArgumentException.class, error.getClass());
        }
        assertNull(registration);
    }

    @Test
    public void testCreateRegistrationNullSessionId() {
        Integer null_session_id = null;
        Session session = new Session();
        Registration registration = null;

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);

        try {
            registration = registrationService.register(email, null_session_id);
        } catch (Exception error) {
            assertEquals("The session id cannot be null.", error.getMessage());
            assertEquals(NullPointerException.class, error.getClass());
        }
        assertNull(registration);
    }

    @Test
    public void testCreateRegistrationSessionNotFound() {
        Integer wrong_session_id = 257;
        Registration registration = null;

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(null);

        try {
            registration = registrationService.register(email, wrong_session_id);
        } catch (Exception error) {
            assertEquals("Session id is not associated to a session.", error.getMessage());
            assertEquals(IllegalArgumentException.class, error.getClass());
        }
        assertNull(registration);
    }

    @Test
    public void testCreateRegistrationCustomerAlreadyRegisteredForSession() {
        Session session = new Session();
        Registration registration = null;

        Registration preExistingRegistration = new Registration(); // register a first time
        customerAccount.setEmail(email);
        customer.setAccount(customerAccount);
        preExistingRegistration.setCustomer(customer);
        preExistingRegistration.setSession(session);
        List<Registration> previousRegistrations = new ArrayList<>();
        previousRegistrations.add(preExistingRegistration);

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);
        lenient().when(registrationRepository.findAll()).thenReturn(previousRegistrations);

        try {
            registration = registrationService.register(email, session.getId());
        } catch (Exception error) { // fails to register a second time
            assertEquals("Email is already registered for session with that id.", error.getMessage());
            assertEquals(IllegalArgumentException.class, error.getClass());
        }
        assertNull(registration);
    }

    @Test
    public void testUnregisterSuccessful() {
        Session session = new Session();

        Registration preExistingRegistration = new Registration(); // register
        customerAccount.setEmail(email);
        customer.setAccount(customerAccount);
        preExistingRegistration.setCustomer(customer);
        preExistingRegistration.setSession(session);
        List<Registration> previousRegistrations = new ArrayList<>();
        previousRegistrations.add(preExistingRegistration);

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);
        lenient().when(registrationRepository.findAll()).thenReturn(previousRegistrations);

        try {
            Boolean result = registrationService.unregister(email, session.getId());
            assertTrue(result);
        } catch (Exception error) {
            fail(error.getMessage());
        }
    }

    @Test
    public void testUnregisterNullEmail() {
        Session session = new Session();

        Registration preExistingRegistration = new Registration(); // register
        customerAccount.setEmail(email);
        customer.setAccount(customerAccount);
        preExistingRegistration.setCustomer(customer);
        preExistingRegistration.setSession(session);
        List<Registration> previousRegistrations = new ArrayList<>();
        previousRegistrations.add(preExistingRegistration);

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);
        lenient().when(registrationRepository.findAll()).thenReturn(previousRegistrations);

        assertThrows(NullPointerException.class, () -> {
            registrationService.unregister(null, session.getId());
        });
    }

    @Test
    public void testUnregisterNullSessionId() {
        Session session = new Session();

        Registration preExistingRegistration = new Registration(); // register
        customerAccount.setEmail(email);
        customer.setAccount(customerAccount);
        preExistingRegistration.setCustomer(customer);
        preExistingRegistration.setSession(session);
        List<Registration> previousRegistrations = new ArrayList<>();
        previousRegistrations.add(preExistingRegistration);

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);
        lenient().when(registrationRepository.findAll()).thenReturn(previousRegistrations);

        assertThrows(NullPointerException.class, () -> {
            registrationService.unregister(email, null);
        });
    }

    @Test
    public void testUnregisterRegistrationNotFound() {
        Session session = new Session();

        Registration preExistingRegistration = new Registration(); // register
        customerAccount.setEmail(email);
        customer.setAccount(customerAccount);
        preExistingRegistration.setCustomer(customer);
        preExistingRegistration.setSession(session);
        List<Registration> previousRegistrations = new ArrayList<>();
        previousRegistrations.add(preExistingRegistration);

        lenient().when(sessionRepository.findSessionById(anyInt())).thenReturn(session);
        lenient().when(registrationRepository.findAll()).thenReturn(previousRegistrations);

        try {
            Boolean result = registrationService.unregister("youCantSeeMe@gmail.com", 9721);
            assertFalse(result);
        } catch (Exception error) {
            fail(error.getMessage());
        }
    }
}