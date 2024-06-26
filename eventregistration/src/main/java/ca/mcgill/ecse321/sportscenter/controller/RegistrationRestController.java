package ca.mcgill.ecse321.sportscenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;

import ca.mcgill.ecse321.sportscenter.dto.RegistrationDto;
import ca.mcgill.ecse321.sportscenter.model.Registration;
import ca.mcgill.ecse321.sportscenter.service.RegistrationService;

import javax.mail.MessagingException;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationRestController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping(value = { "/registration", "/registration/" })
    public RegistrationDto getRegistration(@RequestParam(name = "email") String email,
                                           @RequestParam(name = "sessionId") Integer sessionId) throws IllegalArgumentException, NullPointerException {
        Registration registration = registrationService.getRegistration(email, sessionId);
        return DtoConverter.convertToDto(registration);
    }

    @PostMapping(value = { "/registration", "/registration/" })
    public RegistrationDto register(@RequestParam(name = "email") String email,
                                    @RequestParam(name = "sessionId") Integer sessionId) throws IllegalArgumentException, NullPointerException, MessagingException {
        Registration registration = registrationService.register(email, sessionId);
        return DtoConverter.convertToDto(registration);
    }

    @DeleteMapping(value = { "/unregister", "/unregister/" })
    public void unregister(@RequestParam(name = "email") String email,
                           @RequestParam(name = "sessionId") Integer sessionId) throws NullPointerException {
        registrationService.unregister(email, sessionId);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(NullPointerException e) {
        return e.getMessage();
    }
}
