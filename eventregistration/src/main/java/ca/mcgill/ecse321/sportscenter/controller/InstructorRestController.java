package ca.mcgill.ecse321.sportscenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportscenter.dto.InstructorDto;
import ca.mcgill.ecse321.sportscenter.model.Instructor;
import ca.mcgill.ecse321.sportscenter.service.InstructorService;

@CrossOrigin(origins = "*")
@RestController
public class InstructorRestController {
    @Autowired
    private InstructorService service;

    @GetMapping(value = { "/instructor/{email}", "/instructor/{email}/" })
    public ResponseEntity<InstructorDto> getInstructor(@PathVariable(name = "email") String email) throws Exception {
        Instructor instructor = service.get(email);
        InstructorDto dto = DtoConverter.convertToDto(instructor);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = { "/instructor", "/instructor/" })
    public ResponseEntity<HttpStatus> updateCustomer(@RequestBody InstructorDto instructor) throws Exception {
        service.update(instructor.getFirstName(), instructor.getLastName(), instructor.getAccountEmail(),
                instructor.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(Exception e) {
        return e.getMessage();
    }
}
