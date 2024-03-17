package ca.mcgill.ecse321.sportscenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import ca.mcgill.ecse321.sportscenter.dto.OwnerDto;
import ca.mcgill.ecse321.sportscenter.model.Account;
import ca.mcgill.ecse321.sportscenter.model.Owner;
import ca.mcgill.ecse321.sportscenter.service.OwnerService;

@CrossOrigin(origins = "*")
@RestController
public class OwnerRestController {

	@Autowired
	private OwnerService ownerService;

	@PostMapping(value = { "/owner/{firstName}", "/owner/{firstName}/" })
	public OwnerDto createOwner(@PathVariable("firstName") String firstName, @RequestParam(name = "lastName") String lastName, @RequestParam(name = "email") String email,  @RequestParam(name = "password") String password) throws Exception {

		Owner owner = ownerService.createOwner(firstName, lastName, email, password);
		return convertOwnerToDto(owner);

	}
	

    private OwnerDto convertOwnerToDto(Owner o) {
		if (o == null) {
			throw new IllegalArgumentException("There is no such owner");
		}
		Account ownerAccount = o.getAccount();
		OwnerDto ownerDto = new OwnerDto(ownerAccount.getFirstName(), ownerAccount.getLastName(),ownerAccount.getEmail(), ownerAccount.getPassword());
		return ownerDto;
	}

	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(Exception e) {
        return e.getMessage();
    }
}