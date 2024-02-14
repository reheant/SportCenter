package ca.mcgill.ecse321.sportscenter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.sportscenter.model.PaymentMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.PaymentMethodRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;



@SpringBootTest
public class PaymentMethodRepositoryTest {
    

    @Autowired
	private PaymentMethodRepository paymentMethodRepository;
    @Autowired
	private AccountRepository accountRepository;
    @Autowired
	private CustomerRepository customerRepository;

    @AfterEach
	public void clearDatabase() {
		paymentMethodRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();
	}

    @Test
	public void testPersistAndLoadPaymentMethod() {

    
		// Create account.
		int id = 1;
		String firstName = "Muffin";
		String lastName = "Man";
		String email = "Man@gmail.com";
		String password = "123456";
        Account person = new Account(id, firstName, lastName, email, password);
        accountRepository.save(person);

        // Create Customer
        int customerId = 4;
        boolean wantsEmailConfirmation = false; 
        Customer customer = new Customer(customerId, wantsEmailConfirmation, person);
        customerRepository.save(customer);

        

        // Creates Payment Method
        int paymentMethodId = 6;
		String name = "Rehean";

        
        PaymentMethod paymentMethod = new PaymentMethod(paymentMethodId, name , customer);
        paymentMethodRepository.save(paymentMethod);
        
    
        paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElse(null);;

		// Assert that account is not null and has correct attributes.
		assertNotNull(paymentMethod);
		assertEquals(paymentMethod, paymentMethod.getId());
		assertEquals(name, paymentMethod.getName());
        assertEquals(customer, paymentMethod.getCustomer());
    }
}
   
