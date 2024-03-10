package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.Course;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    List<Course> findCoursesById(Integer id);
    List<Course> findCoursesByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String name, String description);
    List<Course> findCoursesByRequiresInstructor(Boolean requiresInstructor);
    List<Course> findCoursesByDefaultDuration(Float defaultDuration);
    List<Course> findCoursesByCost(Float cost);

    Course findCourseByName(String name);
}
