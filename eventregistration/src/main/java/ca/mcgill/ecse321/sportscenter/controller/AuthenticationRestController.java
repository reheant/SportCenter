package ca.mcgill.ecse321.sportscenter.controller;

import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportscenter.dto.ErrorDto;
import ca.mcgill.ecse321.sportscenter.dto.LoginRequestDto;
import ca.mcgill.ecse321.sportscenter.dto.LoginResponseDto;
import ca.mcgill.ecse321.sportscenter.service.AccountService;

@CrossOrigin(origins = "*")
@RestController
public class AuthenticationRestController {

    @Autowired
    AccountService service;

    @PostMapping(value = { "/login" })
    public LoginResponseDto login(@RequestBody LoginRequestDto form) throws AuthenticationException {
        int id;
        id = service.authenticate(form.email, form.password, form.userType);
        return new LoginResponseDto(id, form.userType);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto authorized(AuthenticationException e) {
        return new ErrorDto(e);
    }
}
