package ca.mcgill.ecse321.sportscenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportscenter.service.CourseService;

@CrossOrigin(origins = "*")
@RestController
public class CourseRestController {

    @Autowired
    private CourseService courseService;
    
}
