package ca.mcgill.ecse321.sportscenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportscenter.dto.OwnerDto;
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
		return DtoConverter.convertToDto(owner);

	}

	@GetMapping(value = { "/owners", "/owners/" })
	public ResponseEntity<List<OwnerDto>> getAllOwners() {
		List<OwnerDto> ownerDTOlist = new ArrayList<>();
		try {
			for (Owner a : ownerService.getAllOwners()) {
				ownerDTOlist.add(DtoConverter.convertToDto(a));
			}
			return new ResponseEntity<>(ownerDTOlist, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(Exception e) {
        return e.getMessage();
    }
}