package ca.mcgill.ecse321.sportscenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public RegistrationDto register(@PathVariable("email") String email, @RequestParam(name = "sessionId") Integer sessionId) throws Exception{
        Registration registration = registrationService.register(email, sessionId);
        return DtoConverter.convertToDto(registration);
    }

    @DeleteMapping(value = { "/unregister/{email}", "/unregister/{email}/" })
    public void unregister(@PathVariable("email") String email, @RequestParam(name = "sessionId") Integer sessionId) throws Exception{
        registrationService.unregister(email, sessionId);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(Exception e) {
        return e.getMessage();
    }
}
