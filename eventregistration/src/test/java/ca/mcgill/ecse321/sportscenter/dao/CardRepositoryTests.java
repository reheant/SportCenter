package ca.mcgill.ecse321.sportscenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Card;
import ca.mcgill.ecse321.sportscenter.model.Card.PaymentCardType;
import ca.mcgill.ecse321.sportscenter.model.Customer;



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
        String firstName = "Muffin";
        String lastName = "Man";
        String email = "Man@gmail.com";
        String password = "123456";
        Account person = new Account(firstName, lastName, email, password);
        // Load Account
        accountRepository.save(person);

        // Create Customer
        boolean wantsEmailConfirmation = false;
        Customer customer = new Customer(wantsEmailConfirmation, person);
        // Load Customer
        customerRepository.save(customer);

        PaymentCardType payment = PaymentCardType.CreditCard;

        // Creates Card
        String name = "Rehean";
        int number = 4324;
        int expirationDate = 2026;
        int ccv = 333;
        Card card = new Card(name, customer, payment, number, expirationDate, ccv);
        // Load Card
        cardRepository.save(card);

        card = cardRepository.findById(card.getId()).orElse(null);

        // Assert that card is not null and has correct attributes.
        assertNotNull(card);
        assertEquals(name, card.getName());
        assertEquals(number, card.getNumber());
        assertEquals(customer.getId(), card.getCustomer().getId());
        assertEquals(expirationDate, card.getExpirationDate());
        assertEquals(ccv, card.getCcv());
        assertEquals(payment, card.getPaymentCardType());
        assertEquals(person.getId(),card.getCustomer().getAccount().getId());
		assertEquals(person.getEmail(), card.getCustomer().getAccount().getEmail());
		assertEquals(person.getFirstName(), card.getCustomer().getAccount().getFirstName());
		assertEquals(person.getLastName(), card.getCustomer().getAccount().getLastName());
		assertEquals(person.getPassword(), card.getCustomer().getAccount().getPassword());
        assertEquals(customer.getAccount().getId(),card.getCustomer().getAccount().getId());
		assertEquals(customer.getAccount().getEmail(), card.getCustomer().getAccount().getEmail());
		assertEquals(customer.getAccount().getFirstName(), card.getCustomer().getAccount().getFirstName());
		assertEquals(customer.getAccount().getLastName(), card.getCustomer().getAccount().getLastName());
		assertEquals(customer.getAccount().getPassword(), card.getCustomer().getAccount().getPassword());
    }
}

