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
import ca.mcgill.ecse321.sportscenter.dao.CardRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportscenter.dao.PayPalRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Card;
import ca.mcgill.ecse321.sportscenter.model.Card.PaymentCardType;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.PayPal;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {
    @Mock
    private CustomerRepository customerDAO;
    @Mock
    private AccountRepository accountDAO;
    @Mock
    private InstructorRepository instructorDAO;
    @Mock
    private PayPalRepository payPalRepository;
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CustomerService service;

    private static final String firstName = "Rehean";
    private static final String lastName = "Thillai";
    private static final String email = "rehean.thillai@gmail.com";
    private static final String password = "Test1234!";
    private static final boolean wantsEmailConfirmation = false;
    private List<Instructor> instructorList = new ArrayList<>();
    private final Customer customer = new Customer();
    private final Account customerAccount = new Account();

    @BeforeEach
    public void setMockOutput() {
        lenient().when(accountDAO.findAccountByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(email)) {
                customerAccount.setEmail(email);
                customerAccount.setFirstName(firstName);
                customerAccount.setLastName(lastName);
                customerAccount.setPassword(password);
                customer.setAccount(customerAccount);
                customer.setWantsEmailConfirmation(wantsEmailConfirmation);
                return customerAccount;
            } else {
                return null;
            }
        });
    
        lenient().when(customerDAO.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            customerAccount.setEmail(email);
            customerAccount.setFirstName(firstName);
            customerAccount.setLastName(lastName);
            customerAccount.setPassword(password);
            customer.setAccount(customerAccount);
            customer.setWantsEmailConfirmation(wantsEmailConfirmation);
            List<Customer> customerList = new ArrayList<>();
            customerList.add(customer);
            return customerList;

        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(customerDAO.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);

    }

    // create customer tests
    @Test
    public void testCreateCustomer() {

        String customerFirstName = "Rehean";
        String customerLastName = "Thillai";
        String customerEmail = "reh@gmail.com";
        String customerPassword = "Test1234!";
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;
        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            System.out.println("HERE");
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            fail(error.getMessage());
        }

        assertNotNull(customerAccount);
        assertNotNull(customer);
        assertEquals(customerFirstName, customerAccount.getFirstName());
        assertEquals(customerLastName, customerAccount.getLastName());
        assertEquals(customerEmail, customerAccount.getEmail());
        assertEquals(customerPassword, customerAccount.getPassword());
        assertEquals(customerWantsEmailConfirmation, customer.getWantsEmailConfirmation());
    }

    @Test
    public void testCreateCustomerNameNull() {
        String customerFirstName = null;
        String customerLastName = "Thillai";
        String customerEmail = "reh@gmail.com";
        String customerPassword = "Test1234!";
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;

        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(customer);
    }

    @Test
    public void testCreateCustomerLastNameNull() {
        String customerFirstName = "Rehean";
        String customerLastName = null;
        String customerEmail = "reh@gmail.com";
        String customerPassword = "Test1234!";
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;

        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(customer);
    }

    @Test
    public void testCreateCustomerEmailNull() {
        String customerFirstName = "Rehean";
        String customerLastName = "thillai";
        String customerEmail = null;
        String customerPassword = "Test1234!";
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;

        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(customer);
    }

    @Test
    public void testCreateCustomerPasswordNull() {
        String customerFirstName = "Rehean";
        String customerLastName = "thillai";
        String customerEmail = "reh@gmail.com";
        String customerPassword = null;
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;

        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(customer);
    }

    @Test
    public void testCreateCustomerNulls() {
        String customerFirstName = null;
        String customerLastName = null;
        String customerEmail = "reh@gmail.com";
        String customerPassword = null;
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;

        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(customer);
    }

    @Test
    public void testCreateDuplicateCustomer() {
        String customerFirstName = "Rehean";
        String customerLastName = "Thillai";
        String customerEmail = "rehean.thillai@gmail.com";
        String customerPassword = "Test1234!";
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;

        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            assertEquals("Email is already in use", error.getMessage());
        }
        assertNull(customer);
    }

    @Test
    public void testInvalidFirstName() {
        String customerFirstName = "Sarasvatha12";
        String customerLastName = "Thillai";
        String customerEmail = "Sarasvatha@gmail.com";
        String customerPassword = "Test1234!";
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;

        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            assertEquals("Invalid first name format", error.getMessage());
        }
        assertNull(customer);
    }

    @Test
    public void testInvalidLastName() {
        String customerFirstName = "Sarasvatha";
        String customerLastName = "Thillai123#";
        String customerEmail = "Sarasvatha@gmail.com";
        String customerPassword = "Test1234!";
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;

        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            assertEquals("Invalid last name format", error.getMessage());
        }
        assertNull(customer);
    }

    @Test
    public void testInvalidEmailFormat() {
        String customerFirstName = "ray";
        String customerLastName = "Thillai";
        String customerEmail = "ray thillai@gmail";
        String customerPassword = "Test1234!";
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;

        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            assertEquals("Invalid email format", error.getMessage());
        }
        assertNull(customer);
    }

    @Test
    public void testInvalidPassword() {
        String customerFirstName = "ray";
        String customerLastName = "Thillai";
        String customerEmail = "raythillai@gmail";
        String customerPassword = "Chickenmaster123";
        boolean customerWantsEmailConfirmation = false;
        Customer customer = null;
        Account customerAccount = null;

        try {
            customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
                    customerWantsEmailConfirmation);
            customerAccount = customer.getAccount();
        } catch (Exception error) {
            assertEquals(
                    "Invalid password format, password must have at least: one lower case letter, one higher case letter, one digit, one special character and be 8 charcters minimum",
                    error.getMessage());
        }
        assertNull(customer);
    }

    // promote customer tests
    @Test
    public void testPromotingCustomer() {
        String customerEmail = "rehean.thillai@gmail.com";
        Instructor instructor = null;
        try {
            instructorList.clear();
            instructor = service.promoteCustomerByEmail(customerEmail);
        } catch (Exception error) {
            fail(error.getMessage());
        }

        assertNotNull(instructor);
        assertEquals(customerAccount, instructor.getAccount()); // comparing instructor acc to the acc created in mock
    }
    @Test
    public void testPromotingNullCustomer() {
        String customerEmail = null;
        Instructor instructor = null;
        try {
            instructor = service.promoteCustomerByEmail(customerEmail);
        } catch (Exception error) {
            assertEquals("email is null", error.getMessage());
        }

        assertNull(instructor);
        
    }

    @Test
    public void testPromotingInexistantEmail() {
        String customerEmail = "nobody@gmail.com";
        Instructor instructor = null;
        try {
            instructor = service.promoteCustomerByEmail(customerEmail);
        } catch (Exception error) {
            assertEquals("Email is not accociated to an account", error.getMessage());
        }
        assertNull(instructor);
    }

    @Test
    public void testPromotingInstructor() {
        String customerEmail = "rehean.thillai@gmail.com";
        Instructor i = null;
        try {
            i = service.promoteCustomerByEmail(customerEmail);
            i = service.promoteCustomerByEmail(customerEmail);
        } catch (Exception error) {
            assertEquals("Person is already an Instructor", error.getMessage());
        }
        assertEquals(customerAccount.getEmail(), customerEmail );
    }

    // paypall tests
    @Test
    public void testPaypalCreation() {
        String accountName = "Rehean's Paypall";
        String customerEmail = "rehean.thillai@gmail.com";
        String paypalEmail = "rehean1@gmail.com";
        String paypalPassword = "123123";
        PayPal payPal = null;
        try {
            payPal = service.setPaypalInformation(accountName, customerEmail, paypalEmail, paypalPassword);
        } catch (Exception error) {
            fail(error.getMessage());
        }

        assertNotNull(payPal);
        assertEquals(email, payPal.getCustomer().getAccount().getEmail());
    }

    @Test
    public void testNullPaypalCreation() {
        String accountName = null;
        String customerEmail = "rehean.thillai@gmail.com";
        String paypalEmail = "rehean1@gmail.com";
        String paypalPassword = null;
        PayPal payPal = null;
        try {
            payPal = service.setPaypalInformation(accountName, customerEmail, paypalEmail, paypalPassword);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(payPal);
    }

    @Test
    public void testNullAccountNamePaypalCreation() {
        String accountName = null;
        String customerEmail = "rehean.thillai@gmail.com";
        String paypalEmail = "rehean1@gmail.com";
        String paypalPassword = "wedwd1233";
        PayPal payPal = null;
        try {
            payPal = service.setPaypalInformation(accountName, customerEmail, paypalEmail, paypalPassword);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(payPal);
    }

    @Test
    public void testNullCustomerEmailPaypalCreation() {
        String accountName = "my paypal";
        String customerEmail = null;
        String paypalEmail = "rehean1@gmail.com";
        String paypalPassword = "wedwd1233";
        PayPal payPal = null;
        try {
            payPal = service.setPaypalInformation(accountName, customerEmail, paypalEmail, paypalPassword);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(payPal);
    }

    @Test
    public void testNullPayPalEmailPaypalCreation() {
        String accountName = "my paypal";
        String customerEmail = "rehean.thillai@gmail.com";
        String paypalEmail = null;
        String paypalPassword = "wedwd1233";
        PayPal payPal = null;
        try {
            payPal = service.setPaypalInformation(accountName, customerEmail, paypalEmail, paypalPassword);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(payPal);
    }

    @Test
    public void testNullPayPalPasswordPaypalCreation() {
        String accountName = "my paypal";
        String customerEmail = "rehean.thillai@gmail.com";
        String paypalEmail = "rehean@gmail.com";
        String paypalPassword = null;
        PayPal payPal = null;
        try {
            payPal = service.setPaypalInformation(accountName, customerEmail, paypalEmail, paypalPassword);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(payPal);
    }

    @Test
    public void testPaypalWrongEmail() {
        String accountName = "Rehean's Paypall";
        String customerEmail = "nobody@gmail.com";
        String paypalEmail = "rehean1@gmail.com";
        String paypalPassword = "123123";
        PayPal payPal = null;
        try {
            payPal = service.setPaypalInformation(accountName, customerEmail, paypalEmail, paypalPassword);
        } catch (Exception error) {
            assertEquals("Email is not accociated to an account", error.getMessage());
        }

        assertNull(payPal);
    }

    // Card tests
    @Test
    public void testCardCreation() {
        String accountName = "rehean's debit card";
        String customerEmail = "rehean.thillai@gmail.com";
        PaymentCardType paymentCardType = PaymentCardType.DebitCard;
        int cardNumber = 12356789;
        int expirationDate = 1225;
        int ccv = 123;
        Card card = null;
        try {
            card = service.setCardInformation(accountName, customerEmail, paymentCardType, cardNumber, expirationDate,
                    ccv);
        } catch (Exception error) {
            fail(error.getMessage());
        }
        assertNotNull(card);
        assertEquals(customerEmail, card.getCustomer().getAccount().getEmail());
    }

    @Test
    public void testNullNameCardCreation() {
        String accountName = null;
        String customerEmail = "rehean.thillai@gmail.com";
        PaymentCardType paymentCardType = PaymentCardType.DebitCard;
        int cardNumber = 12356789;
        int expirationDate = 1225;
        int ccv = 123;
        Card card = null;
        try {
            card = service.setCardInformation(accountName, customerEmail, paymentCardType, cardNumber, expirationDate,
                    ccv);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(card);

    }
    @Test
    public void testNullEmailCardCreation() {
        String accountName = "Rehean's debit";
        String customerEmail = null;
        PaymentCardType paymentCardType = PaymentCardType.DebitCard;
        int cardNumber = 12356789;
        int expirationDate = 1225;
        int ccv = 123;
        Card card = null;
        try {
            card = service.setCardInformation(accountName, customerEmail, paymentCardType, cardNumber, expirationDate,
                    ccv);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(card);

    }

    @Test
    public void testNullPaymentTypeCardCreation() {
        String accountName = "Rehean's debit";
        String customerEmail = "rehean.thillai@gmail.com";
        PaymentCardType paymentCardType = null;
        int cardNumber = 12356789;
        int expirationDate = 1225;
        int ccv = 123;
        Card card = null;
        try {
            card = service.setCardInformation(accountName, customerEmail, paymentCardType, cardNumber, expirationDate,
                    ccv);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(card);

    }
    

    @Test
    public void testBadCardNumberCreation() {
        String accountName = "Rehean's debit";
        String customerEmail = "rehean.thillai@gmail.com";
        PaymentCardType paymentCardType = PaymentCardType.DebitCard;
        int cardNumber = 0;
        int expirationDate = 1225;
        int ccv = 123;
        Card card = null;
        try {
            card = service.setCardInformation(accountName, customerEmail, paymentCardType, cardNumber, expirationDate,
                    ccv);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(card);

    }

    @Test
    public void testBadExpirationDateCreation() {
        String accountName = "Rehean's debit";
        String customerEmail = "rehean.thillai@gmail.com";
        PaymentCardType paymentCardType = PaymentCardType.DebitCard;
        int cardNumber = 12356789;
        int expirationDate = 0;
        int ccv = 123;
        Card card = null;
        try {
            card = service.setCardInformation(accountName, customerEmail, paymentCardType, cardNumber, expirationDate,
                    ccv);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(card);

    }

    @Test
    public void testBadccvCreation() {
        String accountName = "Rehean's debit";
        String customerEmail = "rehean.thillai@gmail.com";
        PaymentCardType paymentCardType = PaymentCardType.DebitCard;
        int cardNumber = 12356789;
        int expirationDate = 1225;
        int ccv = 0;
        Card card = null;
        try {
            card = service.setCardInformation(accountName, customerEmail, paymentCardType, cardNumber, expirationDate,
                    ccv);
        } catch (Exception error) {
            assertEquals("Please ensure all fields are complete and none are empty", error.getMessage());
        }
        assertNull(card);

    }

    @Test
    public void testInvalidEmailCard() {
        String accountName = "rehean's debit card";
        String customerEmail = "r@gmail.com";
        PaymentCardType paymentCardType = PaymentCardType.DebitCard;
        int cardNumber = 12356789;
        int expirationDate = 1225;
        int ccv = 123;
        Card card = null;
        try {
            card = service.setCardInformation(accountName, customerEmail, paymentCardType, cardNumber, expirationDate,
                    ccv);
        } catch (Exception error) {
            assertEquals("Email is not accociated to an account", error.getMessage());
        }
        assertNull(card);
    }

}