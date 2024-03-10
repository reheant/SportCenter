package ca.mcgill.ecse321.sportscenter.controller;

import ca.mcgill.ecse321.sportscenter.dto.CourseDto;
import ca.mcgill.ecse321.sportscenter.model.Course;
import ca.mcgill.ecse321.sportscenter.service.CourseViewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class CourseViewController {
    @Autowired
    private CourseViewService courseViewService;
    @GetMapping(value = { "/courses", "/courses/" })
    public List<CourseDto> viewAllCourses(){
        return courseViewService.viewAllCourses().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    @GetMapping(value = { "/courses/{name}", "/courses/{name}/" })
    public List<CourseDto> viewFilteredCourses(
            @PathVariable("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("requiresInstructor") Boolean requiresInstructor,
            @RequestParam("defaultDuration") Float defaultDuration,
            @RequestParam("cost") Float cost) throws Exception {
        return courseViewService.viewFilteredCourses(id, name, description, requiresInstructor, defaultDuration, cost).stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }


    private CourseDto convertToDto(Course e) {
        if (e == null) {
            throw new IllegalArgumentException("There is no such course.");
        }
        CourseDto eventDto = new CourseDto(e.getName(),e.getDescription(),e.getIsApproved(),e.getRequiresInstructor(), e.getDefaultDuration(), e.getCost());
        return eventDto;
    }
}
