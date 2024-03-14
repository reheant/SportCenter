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
import static org.junit.jupiter.api.Assertions.assertTrue;
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


    @BeforeEach
    public void setMockOutput() {
        lenient().when(accountRepository.findAccountByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(email)) {
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
        try {
            registration = registrationService.register(email, session.getId());
        } catch (Exception error){
            fail(error.getMessage());
        }

        assertNotNull(registration);
        assertEquals(email, registration.getCustomer().getAccount().getEmail());
        assertEquals(session.getId(), registration.getSession().getId());        
    }

}
