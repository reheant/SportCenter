package ca.mcgill.ecse321.sportscenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.model.Course;

@Service
public class CourseViewService {
    @Autowired
    CourseRepository courseRepository;

    @Transactional
    public List<Course> viewAllCourses() {
        return toList(courseRepository.findAll());
    }

    @Transactional
    public List<Course> viewFilteredCourses(
            Integer id, String name, String description, Boolean requiresInstructor,
            Float defaultDuration, Float cost) throws Exception {
        List<Course> filteredCourses = new ArrayList<>();

        if (id != null) {
            List<Course> byId = courseRepository.findCoursesById(id);
            filteredCourses.addAll(byId);
            if (filteredCourses == null) {
                throw new Exception("No matches found.");
            }
            return filteredCourses;
        }

        if (name != null && description != null) {
            List<Course> byNameAndDescription = courseRepository.findCoursesByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(name, description);
            filteredCourses.addAll(byNameAndDescription);
        }

        if (requiresInstructor != null) {
            List<Course> byInstructor = courseRepository.findCoursesByRequiresInstructor(requiresInstructor);
            filteredCourses.addAll(byInstructor);
        }

        if (defaultDuration != null) {
            List<Course> byDuration = courseRepository.findCoursesByDefaultDuration(defaultDuration);
            filteredCourses.addAll(byDuration);
        }

        if (cost != null) {
            List<Course> byCost = courseRepository.findCoursesByCost(cost);
            filteredCourses.addAll(byCost);
        }
        if (filteredCourses == null) {
            throw new Exception("No matches found.");
        }
        return filteredCourses.stream().distinct().collect(Collectors.toList());
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
