package ca.mcgill.ecse321.sportscenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ca.mcgill.ecse321.sportscenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportscenter.service.OwnerService;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class SportscenterApplication {

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private OwnerRepository ownerDto;

	public static void main(String[] args) {
		SpringApplication.run(SportscenterApplication.class, args);

	}

	// @PostConstruct
	// public void initializeAdminUser() throws Exception {
	// if (ownerDto.findAll().isEmpty()) {
	// String firstName = "Marc-Antoine";
	// String lastName = "Nadeau";
	// String email = "admin@mail.com";
	// String password = "1234AbC!";
	// ownerService.createOwner(firstName, lastName, email, password);
	// }
	// }
}
