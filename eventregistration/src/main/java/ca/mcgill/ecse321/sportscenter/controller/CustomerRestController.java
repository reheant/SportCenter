package ca.mcgill.ecse321.sportscenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public ResponseEntity<CustomerDto> createCustomer(@PathVariable("firstName") String firstName,
			@RequestParam(name = "lastName") String lastName,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "wantsEmailConfirmation") Boolean wantsEmailConfirmation)
			throws Exception {
		Customer customer = customerService.createCustomer(firstName, lastName, email, password,
				wantsEmailConfirmation);
		CustomerDto customerDto = DtoConverter.convertToDto(customer);
		return new ResponseEntity<>(customerDto, HttpStatus.CREATED);

	}

	@GetMapping(value = { "/customer/{email}", "/customer/{email}/" })
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable(name = "email") String email) throws Exception {
		Customer customer = customerService.getCustomerByEmail(email);
		CustomerDto customerDto = DtoConverter.convertToDto(customer);
		return new ResponseEntity<>(customerDto, HttpStatus.OK);
	}

	@PostMapping(value = { "/promote/{email}", "/promote/{email}/" })
	public ResponseEntity<InstructorDto> promoteCustomerByEmail(@PathVariable("email") String email) throws Exception {
		Instructor instructor = customerService.promoteCustomerByEmail(email);
		InstructorDto instructorDto = DtoConverter.convertToDto(instructor);
		return new ResponseEntity<>(instructorDto, HttpStatus.CREATED);

	}

	@PostMapping(value = { "/demote/{email}", "/demote/{email}/" })
	public ResponseEntity<Boolean> demoteInstructorByEmail(@PathVariable("email") String email) throws Exception {
		Boolean statusBoolean = customerService.demoteInstructorByEmail(email);
		return new ResponseEntity<>(statusBoolean, HttpStatus.CREATED);

	}

	@PostMapping(value = { "/paypal/add", "/paypal/add/" })
	public ResponseEntity<PaypalDto> setPaypalInformation(@RequestParam(name = "accountName") String accountName,
			@RequestParam(name = "customerEmail") String customerEmail,
			@RequestParam(name = "paypalEmail") String paypalEmail,
			@RequestParam(name = "paypalPassword") String paypalPassword) throws Exception {
		PayPal payPal = customerService.setPaypalInformation(accountName, customerEmail, paypalEmail, paypalPassword);
		PaypalDto paypalDto = DtoConverter.convertToDto(payPal);
		return new ResponseEntity<>(paypalDto, HttpStatus.CREATED);
	}

	@PostMapping(value = { "/card/add", "/card/add/" })
	public ResponseEntity<CardDto> setCardInformation(@RequestParam(name = "accountName") String accountName,
			@RequestParam(name = "customerEmail") String customerEmail,
			@RequestParam(name = "paymentCardType") PaymentCardType paymentCardType,
			@RequestParam(name = "cardNumber") int cardNumber,
			@RequestParam(name = "expirationDate") int expirationDate, @RequestParam(name = "ccv") int ccv)
			throws Exception {
		Card card = customerService.setCardInformation(accountName, customerEmail, paymentCardType, cardNumber,
				expirationDate, ccv);
		CardDto cardDto = DtoConverter.convertToDto(card);
		return new ResponseEntity<>(cardDto, HttpStatus.CREATED);
	}


	@GetMapping(value = { "/customers", "/customers/" })
	public ResponseEntity<List<CustomerDto>> getAllCustomers() {
		List<CustomerDto> customerDTOlist = new ArrayList<>();
		try {
			for (Customer a : customerService.getAllCustomers()) {
				customerDTOlist.add(DtoConverter.convertToDto(a));
			}
			return new ResponseEntity<>(customerDTOlist, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = { "/instructors", "/instructors/" })
	public ResponseEntity<List<InstructorDto>> getAllInstructors() {
		List<InstructorDto> instructorDTOlist = new ArrayList<>();
		try {
			for (Instructor a : customerService.getAllInstructors()) {
				instructorDTOlist.add(DtoConverter.convertToDto(a));
			}
			return new ResponseEntity<>(instructorDTOlist, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = {"/delete/{email}", "/delete/{email}/"})
    public void deleteCustomer(@PathVariable String email) throws Exception {
        customerService.deleteCustomer(email);
    }

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String authorized(Exception e) {
		return e.getMessage();
	}

}