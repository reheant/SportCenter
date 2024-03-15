package ca.mcgill.ecse321.sportscenter.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportscenter.dto.CardDto;
import ca.mcgill.ecse321.sportscenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportscenter.dto.InstructorDto;
import ca.mcgill.ecse321.sportscenter.dto.PaypalDto;
import ca.mcgill.ecse321.sportscenter.model.Card;
import ca.mcgill.ecse321.sportscenter.model.Card.PaymentCardType;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.PayPal;
import ca.mcgill.ecse321.sportscenter.service.CustomerService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@PostMapping(value = { "/customer/{firstName}", "/customer/{firstName}/" })
	public CustomerDto createCustomer(@PathVariable("firstName") String firstName, @RequestParam(name = "lastName") String lastName,
			@RequestParam(name = "email") String email, 
			@RequestParam(name = "password") String password,
			@RequestParam(name = "wantsEmailConfirmation") Boolean wantsEmailConfirmation)
			throws Exception {
		Customer customer = customerService.createCustomer(firstName, lastName, email, password,
				wantsEmailConfirmation);
        CustomerDto customerDto = DtoConverter.convertToDto(customer);
		return customerDto;

	}

	@GetMapping(value = { "/customers", "/customers/" })
	public List<CustomerDto> getAllCustomers(){
		List<CustomerDto> customerDtoList = new ArrayList<>();
		for (Customer c : customerService.getAllCustomers()) {
            CustomerDto customerDto = DtoConverter.convertToDto(c);
			customerDtoList.add(customerDto);
		}
		return customerDtoList;
	}

	@PostMapping(value = { "/promote/{email}", "/promote/{email}/" })
	public InstructorDto promoteCustomerByEmail(@PathVariable("email") String email)
			throws Exception {
		Instructor instructor = customerService.promoteCustomerByEmail(email);
        InstructorDto instructorDto = DtoConverter.convertToDto(instructor);
		return instructorDto;

	}

	@PostMapping(value = { "/paypal/add", "/paypal/add/" })
	public PaypalDto setPaypalInformation(@RequestParam(name = "accountName") String accountName,@RequestParam(name = "customerEmail") String customerEmail,@RequestParam(name = "paypalEmail") String paypalEmail,
	@RequestParam(name = "paypalPassword") String paypalPassword) throws Exception {
		PayPal payPal = customerService.setPaypalInformation(accountName, customerEmail, paypalEmail, paypalPassword);
        PaypalDto paypalDto = DtoConverter.convertToDto(payPal);
		return paypalDto;
	}

	@PostMapping(value = { "/card/add", "/card/add/" })
	public CardDto setCardInformation(@RequestParam(name = "accountName") String accountName, @RequestParam(name = "customerEmail") String customerEmail, @RequestParam(name = "paymentCardType") PaymentCardType paymentCardType, @RequestParam(name = "cardNumber") int cardNumber,
	@RequestParam(name = "expirationDate") int expirationDate, @RequestParam(name = "ccv") int ccv) throws Exception{
		Card card = customerService.setCardInformation(accountName, customerEmail, paymentCardType, cardNumber, expirationDate, ccv);
        CardDto cardDto = DtoConverter.convertToDto(card);
		return cardDto;
	}
}