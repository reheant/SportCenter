package ca.mcgill.ecse321.sportscenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportscenter.dto.RegistrationDto;
import ca.mcgill.ecse321.sportscenter.model.Registration;
import ca.mcgill.ecse321.sportscenter.service.RegistrationService;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationRestController {
    
    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = { "/registration/{email}", "registration/{email}/" })
    public RegistrationDto register(@PathVariable("email") String email, @RequestParam(name = "session_id") Integer session_id) throws Exception{
        Registration registration = registrationService.register(email, session_id);
        return DtoConverter.convertToDto(registration);
    }
}
