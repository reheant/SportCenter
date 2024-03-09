// package ca.mcgill.ecse321.sportscenter.service;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.invocation.InvocationOnMock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.mockito.stubbing.Answer;

// import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
// import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
// import ca.mcgill.ecse321.sportscenter.model.Account;
// import ca.mcgill.ecse321.sportscenter.model.Customer;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.fail;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.lenient;

// @ExtendWith(MockitoExtension.class)
// public class TestCustomerService {
//     @Mock
//     private CustomerRepository customerDAO;
//     @Mock
//     private AccountRepository accountDAO;

//     @InjectMocks
//     private CustomerService service;

//     private static final String firstName = "Rehean";
//     private static final String lastName = "Thillai";
//     private static final String email = "rehean.thillai@gmail.com";
//     private static final String password = "Test1234!";
//     private static final boolean wantsEmailConfirmation = false;

//     private final Customer customer = new Customer();
//     private final Account customerAccount = new Account();

//     @BeforeEach
//     public void setMockOutput() {
//         lenient().when(accountDAO.findAccountByEmail(email)).thenAnswer((InvocationOnMock invocation) -> {
//             if (invocation.getArgument(0).equals(email)) {
//                 customerAccount.setEmail(email);
//                 customerAccount.setFirstName(firstName);
//                 customerAccount.setLastName(lastName);
//                 customerAccount.setPassword(password);
//                 customer.setAccount(customerAccount);
//                 customer.setWantsEmailConfirmation(wantsEmailConfirmation);
//                 return customer;
//             } else {
//                 return null;
//             }
//         });
//         Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
//             return invocation.getArgument(0);
//         };
//         lenient().when(customerDAO.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);

//     }

//     @Test
//     public void testCreateCustomer() {

//         String customerFirstName = "Rehean";
//         String customerLastName = "Thillai";
//         String customerEmail = "rehean.thillai@gmail.com";
//         String customerPassword = "Test1234!";
//         boolean customerWantsEmailConfirmation = false;
//         Customer customer = null;
//         Account customerAccount = null;
//         try {
//             customer = service.createCustomer(customerFirstName, customerLastName, customerEmail, customerPassword,
//                     customerWantsEmailConfirmation);
//             customerAccount = customer.getAccount();
//         } catch (Exception error) {
//             fail(error.getMessage());
//         }

//         assertNotNull(customerAccount);
//         assertNotNull(customer);
//         assertEquals(customerFirstName, customerAccount.getFirstName());
//         assertEquals(customerLastName, customerAccount.getLastName());
//         assertEquals(customerEmail, customerAccount.getEmail());
//         assertEquals(customerPassword, customerAccount.getPassword());
//         assertEquals(customerWantsEmailConfirmation, customer.getWantsEmailConfirmation());
//     }

// }