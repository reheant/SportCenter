package ca.mcgill.ecse321.sportscenter.controller;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportscenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportscenter.dto.InstructorDto;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Customer;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.model.PaymentMethod;
import ca.mcgill.ecse321.sportscenter.service.CustomerService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerController {

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
		return convertToDto(customer);

	}

	@GetMapping(value = { "/customers", "/customers/" })
	public List<CustomerDto> getAllCustomers(){
		List<CustomerDto> customerDtoList = new ArrayList<>();
		for (Customer c : customerService.getAllCustomers()) {
			customerDtoList.add(convertToDto(c));
		}
		return customerDtoList;
	}

	@PostMapping(value = { "/promote/{email}", "/promote/{email}/" })
	public InstructorDto promoteCustomerByEmail(@PathVariable("email") String email)
			throws Exception {
		Instructor instructor = customerService.promoteCustomerByEmail(email);
		return convertToInstructorDto(instructor);

	}
	

	private InstructorDto convertToInstructorDto(Instructor instructor) {
		if (instructor == null) {
			throw new IllegalArgumentException("There is no such instructor");
		}
		Account customerAccount = instructor.getAccount();
		InstructorDto instructorDto = new InstructorDto(customerAccount.getFirstName());

		return instructorDto;
	}

	private CustomerDto convertToDto(Customer c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such customer");
		}
		Account customerAccount = c.getAccount();
		CustomerDto customerDto = new CustomerDto(customerAccount.getFirstName(), customerAccount.getLastName(),
				customerAccount.getEmail(), customerAccount.getPassword(), c.getWantsEmailConfirmation());
		return customerDto;
	}

}