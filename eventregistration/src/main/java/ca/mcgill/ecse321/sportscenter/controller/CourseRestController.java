package ca.mcgill.ecse321.sportscenter.controller;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportscenter.dto.CourseDto;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.service.CourseService;

@CrossOrigin(origins = "*")
@RestController
public class CourseRestController {

    @Autowired
    private CourseService courseService;


    @PostMapping(value = { "/course/{name}", "/course/{name}/" })
    public CourseDto createCourse(@PathVariable("name") String name, @RequestParam(name = "description") String description, @RequestParam(name = "requiresInstructor") Boolean requiresInstructor, @RequestParam(name = "defaultDuration") float defaultDuration, @RequestParam(name = "cost") float cost) throws Exception {
        Course course = courseService.createCourse(name, description, requiresInstructor, defaultDuration,cost);
        return convertToDto(course);
    }

    @PostMapping(value = {"/approve/{name}", "/approve/{name}/" })
    public CourseDto approveCourse(@PathVariable("name") String name, @RequestParam(name = "email") String email) throws Exception {
        Course course = courseService.approveCourse(name, email);
        return convertToDto(course);
    }

    @PostMapping(value = {"/disapprove/{name}", "/disapprove/{name}/" })
    public CourseDto disapproveCourse(@PathVariable("name") String name, @RequestParam(name = "email") String email) throws Exception {
        Course course = courseService.disapproveCourse(name, email);
        return convertToDto(course);
    }

    private CourseDto convertToDto(Course c){
        if (c == null){
            throw new IllegalArgumentException("There is no such customer");
        }
        CourseDto courseDto = new CourseDto(c.getName(), c.getDescription(), c.getCourseStatus(),c.getRequiresInstructor(),c.getDefaultDuration(), c.getCost());
        return courseDto;
    }    
}
