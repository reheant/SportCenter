// package ca.mcgill.ecse321.sportscenter.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RestController;

// import ca.mcgill.ecse321.sportscenter.dto.InstructorDto;
// import ca.mcgill.ecse321.sportscenter.model.Account;
// import ca.mcgill.ecse321.sportscenter.model.Instructor;
// import ca.mcgill.ecse321.sportscenter.service.CustomerService;

// @CrossOrigin(origins = "*")
// @RestController
// public class InstructorController {
//     @Autowired
// 	private CustomerService customerService;
    
//     @PostMapping(value = { "/promote/{email}", "/promote/{email}/" })
// 	public InstructorDto promoteCustomerByEmail(@PathVariable("email") String email)
// 			throws Exception {
// 		Instructor instructor = customerService.promoteCustomerByEmail(email);
// 		System.out.println("IN THE POST MAPPING");
// 		return convertToInstructorDto(instructor);

// 	}
	

// 	private InstructorDto convertToInstructorDto(Instructor instructor) {
// 		if (instructor == null) {
// 			throw new IllegalArgumentException("There is no such instructor");
// 		}
// 		Account customerAccount = instructor.getAccount();
// 		InstructorDto instructorDto = new InstructorDto(customerAccount.getFirstName());
// 		System.out.println("IN THE DTO conversion");

// 		return instructorDto;
// 	}

// }