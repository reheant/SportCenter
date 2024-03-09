
package ca.mcgill.ecse321.sportscenter.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportscenter.dao.CourseRepository;
import ca.mcgill.ecse321.sportscenter.model.Course;

@Service
public class CourseSportsCenterService {

    @Autowired 
    CourseRepository courseRepository;
    
    /**
    * Retrieves a course by its ID.
    * @param id The ID of the course to retrieve. (Integer)
    * @return The course with the specified ID.
    * @throws Exception If the course is not found.
    */
    @Transactional
    public Course getCourseById(Integer id) throws Exception {
       Course course = courseRepository.findById(id).orElse(null);

       if (course != null) return course;
       else throw new Exception("Could not find Couse with name " + id);
        
    }

    /**
    * Retrieves a course by its name.
    * @param name The name of the course to retrieve. (String)
    * @return The course with the given name.
    * @throws Exception If the course is not found.
    */
    @Transactional
    public Course getCourseByName(String name) throws Exception {
       Course course = courseRepository.findCourseByName(name);

       if (course != null) return course;
       else throw new Exception("Could not find Couse with name " + name);
        
    }

    /**
    * Retrieves a list of all courses.
    * @return List of all courses.
    * @throws Exception If an error occurs while retrieving the courses.
    */
    @Transactional
    public List<Course> getAllCourses() throws Exception {
        List<Course> courses = new ArrayList<>();

        courseRepository.findAll().forEach(courses::add);
        return courses;
    }

    /**
    * Creates a new course with the specified details.
    *
    * @param name The name of the course (String).
    * @param description The description of the course (String).
    * @param isApproved Whether the course is approved (boolean).
    * @param requiresInstructor Whether the course requires an Instructor (boolean).
    * @param duration The duration of the course (float).
    * @param cost The cost of the course (float).
    * @return The created course object.
    * @throws Exception If the name is invalid or already taken.
    */
    @Transactional
    public Course createCourse(String name, String description, boolean isApproved, boolean requiresInstructor,
      float duration, float cost) throws Exception {

        if (name == null || name.equals("")) {
            throw new Exception("The course requires a name");
        } else if ( courseRepository.findCourseByName(name) != null) {
            throw new Exception("A Course with name " + name + " already exists"); 
        } if (duration <= 0) {
            throw new Exception("The duration cannot be zero or negative");
        } if (cost <= 0) {
            throw new Exception("The price cannot be zero or negative");
        } 

        Course course = new Course();

        course.setName(name); 
        course.setDescription(description);
        course.setIsApproved(isApproved);
        course.setRequiresInstructor(requiresInstructor);
        course.setDefaultDuration(duration);
        course.setCost(cost);

        return course;
      }

}