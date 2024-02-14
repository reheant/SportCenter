package ca.mcgill.ecse321.sportscenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportscenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportscenter.dao.CardRepository;
import ca.mcgill.ecse321.sportscenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportscenter.dao.PaymentMethodRepository;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Card;
import ca.mcgill.ecse321.sportscenter.model.Card.PaymentCardType;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.PaymentMethod;


@SpringBootTest
public class CardRepositoryTests {

    @Autowired
	private CardRepository cardRepository;
    @Autowired
	private AccountRepository accountRepository;
    @Autowired
	private CustomerRepository customerRepository;

    @AfterEach
	public void clearDatabase() {
		cardRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();
	}

    @Test
	public void testPersistAndLoadCard() {

    
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

        
        PaymentCardType payment = PaymentCardType.CreditCard;
        

        // Creates Card
        int card_id = 1;
		String name = "Rehean";
        int number = 4324;
        int expirationDate = 2026;
        int ccv = 333;
        Card card = new Card(card_id, name, customer, payment, number, expirationDate, ccv);
        cardRepository.save(card);
        
        //TODO - Implement this in persitence
        card = cardRepository.findCardById(card_id);

		// Assert that account is not null and has correct attributes.
		assertNotNull(card);
		assertEquals(card_id, card.getId());
		assertEquals(name, card.getName());
		assertEquals(number, card.getNumber());
		assertEquals(expirationDate, card.getExpirationDate());
		assertEquals(ccv, card.getCcv());
        assertEquals(payment, card.getPaymentCardType());
    
    }
}
   
