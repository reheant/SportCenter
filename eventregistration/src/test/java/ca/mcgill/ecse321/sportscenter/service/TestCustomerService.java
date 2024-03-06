package ca.mcgill.ecse321.sportscenter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {
    @Mock
    private CustomerRepository customerDAO;

    @InjectMocks
    private CustomerService service;

    private static final String firstName = "Rehean";
    private static final String lastName = "Thillai";
    private static final String email = "rehean.thillai@gmail.com";
    private static final String password = "Test1234!";
    private static final boolean wantsEmailConfirmation = false;


    private final Customer customer = new Customer();
    private final Account customerAccount = new Account();


    @BeforeEach
    public void setMockOutput() {
        lenient().when(customerDAO.findCustomerByEmail(email)).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(email)) {
                customerAccount.setEmail(email);
                customerAccount.setFirstName(firstName);
                customerAccount.setLastName(lastName);
                customerAccount.setPassword(password);
                customer.setAccount(customerAccount);
                customer.setWantsEmailConfirmation(wantsEmailConfirmation);
                return customer;
            } else {
                return null;
            }
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(customerDAO.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);

    }

    @Test
    public void createCustomer() {

        String customerFirstName = "Rehean";
        String customerLastName = "Thillai";
        String customerEmail = "rehean.thillai@gmail.com";
        String customerPassword = "Test1234!";
        boolean customerWantsEmailConfirmation = false;

        try {
            service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
        } catch (Exception error) {
            fail(error.getMessage());
        }

    }

}