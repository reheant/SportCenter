package ca.mcgill.ecse321.sportscenter.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportscenter.model.Course;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    List<Course> findCoursesByIdIn(Collection<Integer> ids);
    Course findCourseByName(String name);
    @Query("SELECT c FROM Course c WHERE lower(c.name) LIKE lower(concat('%', :keyword, '%')) OR lower(c.description) LIKE lower(concat('%', :keyword, '%'))")
    List<Course> findCoursesByKeywordInNameOrDescription(@Param("keyword") String keyword);
    List<Course> findCoursesByCourseStatus(Course.CourseStatus status);
    List<Course> findCoursesByRequiresInstructor(Boolean requiresInstructor);
    List<Course> findCoursesByDefaultDuration(Float defaultDuration);
    List<Course> findCoursesByCost(Float cost);
}
