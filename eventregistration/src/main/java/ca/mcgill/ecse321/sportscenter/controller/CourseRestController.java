package ca.mcgill.ecse321.sportscenter.controller;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public CourseDto createCourse(@PathVariable("name") String name,
                                  @RequestParam(name = "description") String description,
                                  @RequestParam(name = "requiresInstructor") Boolean requiresInstructor,
                                  @RequestParam(name = "defaultDuration") float defaultDuration,
                                  @RequestParam(name = "cost") float cost) throws Exception {
        Course course = courseService.createCourse(name, description, requiresInstructor, defaultDuration, cost);
        return DtoConverter.convertToDto(course);
    }

    @GetMapping(value = { "/courses", "/courses/" })
    public List<CourseDto> getAllCourses() {
        List<CourseDto> courseDtoList = new ArrayList<>();

        try {
            for (Course c: courseService.getAllCourses()){
                courseDtoList.add(DtoConverter.convertToDto(c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courseDtoList;
    }

    @GetMapping(value = { "/courses/filter", "/courses/filter/"})
    public List<CourseDto> viewFilteredCourses(
            @RequestParam(value="ids", required=false) Collection<Integer> ids,
            @RequestParam(value="keyword", required=false) String keyword,
            @RequestParam(value="courseStatus", required=false) Course.CourseStatus status,
            @RequestParam(value="requiresInstructor", required=false) Boolean requiresInstructor,
            @RequestParam(value="defaultDuration", required=false) Float defaultDuration,
            @RequestParam(value="cost", required=false) Float cost) throws Exception {
        if (ids == null && keyword == null && status == null && requiresInstructor == null && defaultDuration == null && cost == null) {
            return getAllCourses();
        }
        return courseService.viewFilteredCourses(ids, keyword, status, requiresInstructor, defaultDuration, cost).stream().map(p -> DtoConverter.convertToDto(p)).collect(Collectors.toList());
    }

    @PostMapping(value = {"/approve/{name}", "/approve/{name}/" })
    public CourseDto approveCourse(@PathVariable String name) throws Exception {
        Course course = courseService.approveCourse(name);
        return DtoConverter.convertToDto(course);
    }

    @PostMapping(value = {"/disapprove/{name}", "/disapprove/{name}/" })
    public CourseDto disapproveCourse(@PathVariable String name) throws Exception {
        Course course = courseService.disapproveCourse(name);
        return DtoConverter.convertToDto(course);
    }

    @DeleteMapping(value = {"/courses/{id}", "/courses/{id}/"})
    public void deleteCourse(@PathVariable Integer id) throws Exception {
        courseService.deleteCourse(id);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String authorized(Exception e) {
        return e.getMessage();
    }
}