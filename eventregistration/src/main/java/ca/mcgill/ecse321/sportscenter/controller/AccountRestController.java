package ca.mcgill.ecse321.sportscenter.controller;

import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ca.mcgill.ecse321.sportscenter.dto.LoginDto;
import ca.mcgill.ecse321.sportscenter.service.AccountService;

@CrossOrigin(origins = "*")
@RestController
public class AccountRestController {

    @Autowired
    AccountService service;

    @PostMapping(value = {"/login"})
    public int login(@RequestBody LoginDto form) {
        int id;
        try {
            id = service.authenticate(form.email, form.password, form.userType);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        return id;
    }
}
