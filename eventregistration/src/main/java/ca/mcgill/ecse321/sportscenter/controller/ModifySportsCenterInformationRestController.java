package ca.mcgill.ecse321.sportscenter.controller;

import ca.mcgill.ecse321.sportscenter.dto.*;
import ca.mcgill.ecse321.sportscenter.service.ModifySportsCenterInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.sportscenter.dto.*;
import ca.mcgill.ecse321.sportscenter.model.*;
import ca.mcgill.ecse321.sportscenter.service.ModifySportsCenterInformationService;
import ca.mcgill.ecse321.sportscenter.model.Course.CourseStatus;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sportscenter/modify")
public class ModifySportsCenterInformationRestController {
    @Autowired
    private ModifySportsCenterInformationService modifySportsCenterInformationService;

    // update a course
    @PutMapping("/courses/{courseId}")
    public CourseDto updateCourse(@PathVariable Integer courseId, @RequestBody CourseDto courseDto) {
 
        Course course = modifySportsCenterInformationService.updateCenterCourse(courseId, 
        courseDto.getName(), courseDto.getDescription(), courseDto.getCourseStatus(), courseDto.getCost(),
        courseDto.getDefaultDuration());
        return convertToDto(course);
    }

    // update a location
    @PutMapping("/locations/{locationId}")
    public LocationDto updateLocation(@PathVariable Integer locationId, @RequestBody LocationDto locationDto) {

        Location location = modifySportsCenterInformationService.updateCenterLocation(locationId, 
        locationDto.getName(), locationDto.getCapacity(), locationDto.getOpeningTime(), locationDto.getClosingTime());
        return convertToDto(location);
    }

    // update an instructor's details
    @PutMapping("/instructors/{instructorId}")
    public InstructorDto updateInstructor(@PathVariable Integer instructorId, @RequestBody InstructorDto instructorDto) {

            Instructor instructor = modifySportsCenterInformationService.updateCenterInstructor(instructorId, 
            instructorDto.getFirstName(), instructorDto.getLastName(), instructorDto.getEmail());
        return convertToDto(instructor);
    }

    private LocationDto convertToDto(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("There is no such Location!");
        }
        
        LocationDto dto = new LocationDto(location.getName(), location.getCapacity(), 
        location.getOpeningTime(), location.getClosingTime());
        return dto;
    }

    private InstructorDto convertToDto(Instructor in) {
        if (in == null) {
            throw new IllegalArgumentException("There is no such Instructor!");
        }

        Account acc = in.getAccount();
        
        InstructorDto dto = new InstructorDto(acc.getFirstName(), acc.getLastName(), 
        acc.getEmail());
        return dto;
    }

    private CourseDto convertToDto(Course c) {
        if (c == null) {
            throw new IllegalArgumentException("There is no such Course!");
        }
        
        CourseDto dto = new CourseDto(c.getName(), c.getDescription(), c.getCourseStatus(), 
        c.getRequiresInstructor(), c.getDefaultDuration(), c.getCost());
        return dto;
    }
    
}