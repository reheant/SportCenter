package ca.mcgill.ecse321.sportscenter.controller;
/* 
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

    @GetMapping(value = { "/courses", "/courses/" })
    public List<CourseDto> getAllCourses() {
        List<CourseDto> courseDtoList = new ArrayList<>();

        try {
            for (Course c: courseService.getAllCourses()){
                courseDtoList.add(convertToDto(c));
            }
        } catch (Exception e) {
            //TODO: 
            e.printStackTrace();
        }
        return courseDtoList;
    }

    private CourseDto convertToDto(Course c){
        if (c == null){
            throw new IllegalArgumentException("There is no such customer");
        }
        CourseDto courseDto = new CourseDto(c.getName(), c.getDescription(),c.getIsApproved(), c.getRequiresInstructor(),c.getDefaultDuration(), c.getCost());
        return courseDto;
    }    
}
*/